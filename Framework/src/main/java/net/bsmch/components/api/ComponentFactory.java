package net.bsmch.components.api;

import net.bsmch.components.PageComponent;
import net.bsmch.components.internal.ComponentDecorator;
import net.bsmch.findby.Find;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;

/**
 * Factory class to make using Page Components simpler and easier.
 */
public class ComponentFactory {
     /**
     * Instantiate an instance of the given class, and set a lazy proxy to the context element of each
     * {@link PageComponent} and {@link java.util.List<PageComponent>} fields that have
     * been declared.
     * It also sets a lazy proxy for each WebElement and List<WebElement> fields.
     * It also auto-initializes each sub-component (Nested PageComponent) field.
     * So you should not call this method from a sub-component, it can result in unexpected behaviour.
     *
     * To instantiate the Component you must declare a {@link FindBy} or {@link Find}
     * annotation for the field.
     *
     * This method will attempt to instantiate the class given to it, using the default constructor.
     * An exception will be thrown if the class cannot be instantiated.
     *
     * @param searchContext The SearchContext will be used to look up the components and elements.
     * @param page          The Page Object or Page Component to initialize.
     * @param <T>           Class of the PageObject
     * @return An instantiated instance of the class with all WebElements and all sub-components proxied.
     * @see FindBy
     * @see Find
     */
    public static <T> T initComponents(SearchContext searchContext, T page) {
        initComponents(new ComponentDecorator(searchContext), page);
        return page;
    }

    /**
     * See {@link ComponentFactory#initComponents(SearchContext, Object)} this method
     * uses a pre-defined {@link FieldDecorator} to decorate the fields.
     * @param decorator The field decorator used to decorate fields.
     * @param page      The Page Object or Page Component to initialize.
     */
    public static void initComponents(FieldDecorator decorator, Object page) {
        Class<?> proxyIn = page.getClass();
        while (proxyIn != Object.class) {
            proxyFields(decorator, page, proxyIn);
            proxyIn = proxyIn.getSuperclass();
        }
    }

    private static void proxyFields(FieldDecorator decorator, Object page, Class<?> proxyIn) {
        Field[] fields = proxyIn.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(page.getClass().getClassLoader(), field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(page, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
