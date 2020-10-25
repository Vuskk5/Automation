package net.bsmch.components.internal;

import net.bsmch.components.PageComponent;
import net.bsmch.components.api.ComponentFactory;
import net.bsmch.findby.NoFind;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
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


        Field contextField;
        try {
            contextField = PageComponent.class.getDeclaredField(CONTEXT_FIELD_NAME);
        }
        catch (NoSuchFieldException ex) {
            throw new RuntimeException("Field " + CONTEXT_FIELD_NAME + " does not exists in " + PageComponent.class.getName(), ex);
        }

        // Locator with searchContext of the class calling initComponents
        ElementLocator locator = new DefaultElementLocator(this.searchContext, new Annotations(field));

        // If the fieldType is subclass of PageComponent
        Class<?> fieldType = field.getType();
        if (PageComponent.class.isAssignableFrom(fieldType)) {
            ElementDecorator decorator = new ElementDecorator(new DefaultElementLocatorFactory(this.searchContext));
            Object element = decorator.decorate(PageComponent.class.getClassLoader(), contextField, locator);

            PageComponent pageComponent = getNewPageComponent(new ElementImpl((WebElement) element), fieldType);

            if (hasSubComponents(pageComponent)) {
                ComponentFactory.initComponents(pageComponent.getContext(), pageComponent);
            }

            return pageComponent;
        }
        else if (List.class.isAssignableFrom(fieldType)) {
            Class<?> erasureClass = getErasureClass(field);

            if (erasureClass == null) {
                return null;
            }

            List<WebElement> contextElementsList = locator.findElements();
            List<Object> componentsList = new ArrayList<>();

            for (WebElement contextElement : contextElementsList) {
                Element castedElement = new ElementImpl(contextElement);
                PageComponent pageComponent = getNewPageComponent(castedElement, erasureClass);

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
            }
            else if (List.class.isAssignableFrom(field.getType())) {
                Class<?> erasureClass = getErasureClass(field);

                if (erasureClass != null &&
                    PageComponent.class.isAssignableFrom(erasureClass)) {
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
    private PageComponent getNewPageComponent(Element elementContext, Class<?> componentClass) {
        try {
            PageComponent pageComponent = (PageComponent) componentClass.newInstance();

            ElementFactory.initElements(elementContext, pageComponent);

            pageComponent.setContext(elementContext);

            return pageComponent;
        }
        catch (IllegalAccessException | InstantiationException ex) {
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

        return  field.getAnnotation(org.openqa.selenium.support.FindBy.class) != null ||
                field.getAnnotation(org.openqa.selenium.support.FindBys.class) != null ||
                field.getAnnotation(org.openqa.selenium.support.FindAll.class) != null ||
                field.getAnnotation(net.bsmch.findby.Find.class) != null ||
                field.getAnnotation(net.bsmch.findby.Finds.class) != null ||
                field.getAnnotation(net.bsmch.findby.FindAll.class) != null;
    }
}
