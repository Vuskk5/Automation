package net.bsmch.components.internal;

import net.bsmch.components.PageComponent;
import net.bsmch.components.api.ComponentFactory;
import net.bsmch.findby.FindAll;
import net.bsmch.findby.Find;
import net.bsmch.findby.Finds;
import net.bsmch.findby.NoFind;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.*;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;
import org.selophane.elements.factory.api.ElementFactory;
import org.selophane.elements.factory.internal.ElementDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ComponentDecorator implements FieldDecorator {
    private static final String CONTEXT_FIELD_NAME = "context";
    private SearchContext searchContext;

    public ComponentDecorator(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        // If field is annotated with NoFindBy
        if (field.getAnnotation(NoFind.class) != null) {
            return null;
        }

        // If field type is not subclass of PageComponent and field is not decoratable
        if (!PageComponent.class.isAssignableFrom(field.getType()) && !isDecoratableList(field)) {
            return null;
        }

        Class<?> fieldType = field.getType();

        Field contextField;
        try {
            contextField = PageComponent.class.getDeclaredField(CONTEXT_FIELD_NAME);
        }
        catch (NoSuchFieldException ex) {
            System.out.println("Field " + CONTEXT_FIELD_NAME + " does not exists in " + PageComponent.class.getName());
            ex.printStackTrace();
            return null;
        }

        // Locator with searchContext of the class calling initComponents
        ElementLocator locator = new DefaultElementLocator(this.searchContext, new Annotations(field));

        // If the fieldType is subclass of PageComponent
        if (PageComponent.class.isAssignableFrom(fieldType)) {
            ElementDecorator decorator = new ElementDecorator(new DefaultElementLocatorFactory(this.searchContext));
            Object element = decorator.decorate(PageComponent.class.getClassLoader(), contextField, locator);

            PageComponent pageComponent = getNewProxiedPageComponent(new ElementImpl((WebElement) element), fieldType);

            if (hasSubComponents(pageComponent)) {
                ComponentFactory.initComponents(pageComponent.getContext(), pageComponent);
            }

            return pageComponent;
        }
        // If the field is subclass of List
        else if (List.class.isAssignableFrom(fieldType)) {
            Class<?> erasureClass = getErasureClass(field); // Get the List's inner type
            List<WebElement> contextElementsList = locator.findElements();
            List<Object> componentsList = new ArrayList<>();

            for (WebElement contextElement : contextElementsList) {
                Element castedElement = new ElementImpl(contextElement);
                PageComponent pageComponent = getNewProxiedPageComponent(castedElement, erasureClass);

                if (hasSubComponents(pageComponent)) {
                    ComponentFactory.initComponents(castedElement, pageComponent);
                }
                componentsList.add(pageComponent);
            }

            return componentsList;
        }
        else {
            return null;
        }
    }

    private boolean hasSubComponents(PageComponent pageComponent) {
        Field[] fields = pageComponent.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (PageComponent.class.isAssignableFrom(field.getType())) {
                return true;
            } else if (List.class.isAssignableFrom(field.getType())) {
                Class<?> erasureClass = getErasureClass(field); // List type
                if (PageComponent.class.isAssignableFrom(erasureClass)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get a new {@link PageComponent} of type {@code componentClass}.
     * With the top-context set as {@code elementContext}.
     * Sets a lazy proxy for all {@link WebElement} and {@link List<WebElement>} fields that
     * have been declared in the class.
     * @param elementContext    The top context of the PageComponent.
     * @param componentClass    The class type of the component. Subclass of {@link PageComponent}.
     * @return  The instantiated {@link PageComponent} or null if an exception was thrown.
     */
    private PageComponent getNewProxiedPageComponent(Element elementContext, Class<?> componentClass) {
        try {
            // Get both fields of PageComponent and allow access to them
            Field contextField = PageComponent.class.getDeclaredField("context");
            contextField.setAccessible(true);

            // Instantiate a SubClass of PageComponent
            // Checks where made prior to this cast to ensure it is valid
            PageComponent pageComponent = (PageComponent) componentClass.newInstance();
            contextField.set(pageComponent, elementContext);
            ElementFactory.initElements(elementContext, pageComponent);

            contextField.set(pageComponent, elementContext);

            contextField.setAccessible(false);

            return pageComponent;
        }
        catch (IllegalAccessException | InstantiationException | NoSuchFieldException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Class<?> getErasureClass(Field field) {
        // Type erasure in Java isn't complete. Attempt to discover the generic
        // interfaceType of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        Class<?> erasureClass = getErasureClass(field);
        if (erasureClass == null) {
            return false;
        }

        if (!PageComponent.class.isAssignableFrom(erasureClass)) {
            return false;
        }

        return  field.getAnnotation(FindBy.class) != null ||
                field.getAnnotation(FindBys.class) != null ||
                field.getAnnotation(org.openqa.selenium.support.FindAll.class) != null ||
                field.getAnnotation(Find.class) != null ||
                field.getAnnotation(Finds.class) != null ||
                field.getAnnotation(FindAll.class) != null;
    }
}
