package net.bsmch.findby;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.support.CustomFindByBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactoryFinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(Find.FindByBuilder.class)
public @interface Find {
    /**
     * Finds elements by 'id' attribute
     */
    String id() default "";

    /**
     * Finds elements by 'name' attribute
     */
    String name() default "";

    /**
     * Finds elements bu one 'class' attribute value
     */
    String className() default "";

    /**
     * Finds elements matching the given CSS Selector
     */
    String css() default "";

    /**
     * Finds elements with the given tagname
     */
    String tagName() default "";

    /**
     * Finds links with matching text values
     */
    String linkText() default "";

    /**
     * Finds links containing the given text values
     */
    String partialLinkText() default "";

    /**
     * Finds elements whose 'attribute' matches the given 'value'
     */
    String attribute() default "";

    /**
     * Finds elements by 'value' attribute
     */
    String value() default "";

    /**
     * Finds elements matching the given XPath
     */
    String xpath() default "";

    /**
     * Wrapper method to find elements with the given text, the search will
     * be performed using {@link Find#xpath()}.
     * <br>Can be chained with {@link Find#checkContains()} to check if the text value
     * <u>contains</u> the given text.
     * <br>Can be chained with {@link Find#isDescendant()} when the {@link Find} annotation
     * is in {@link PageComponent} class, to only look for descendants.
     * <br>Can be chained with {@link Find#tagName()} to look for specific tags.
     */
    String xpathText() default "";

    /**
     * Wrapper method to find elements with the given string, the search will
     * be performed using {@link Find#xpath()}.
     * <br>Can be chained with {@link Find#checkContains()} to check if the string value
     * <u>contains</u> the given string.
     * <br>Can be chained with {@link Find#isDescendant()} when the {@link Find} annotation
     * is in {@link PageComponent} class, to only look for descendants.
     * <br>Can be chained with {@link Find#tagName()} to look for specific tags.
     */
    String xpathString() default "";

    /**
     * Paired with {@link Find#xpathText()} and {@link Find#xpathString()}
     * to check if the attributes are containing the given values.
     */
    boolean checkContains() default false;

    /**
     * Paired with {@link Find#xpathText()} and {@link Find#xpathString()}
     * to query as a descendant instead of the root document.
     */
    boolean isDescendant() default false;

    class FindByBuilder extends CustomFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            Find findBy = (Find) annotation;
            assertValidFindBy(findBy);

            return buildByFromShortFindBy(findBy);
        }
    }
}
