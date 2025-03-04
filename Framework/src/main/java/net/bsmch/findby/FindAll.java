package net.bsmch.findby;

import net.bsmch.findby.support.CustomFindByBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.ByAll;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(FindAll.FindByBuilder.class)
public @interface FindAll {
    Find[] value();

    class FindByBuilder extends CustomFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            FindAll findBys = (FindAll) annotation;
            assertValidFindAll(findBys);

            Find[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];
            for (int i = 0; i < findByArray.length; i++) {
                byArray[i] = buildByFromFindBy(findByArray[i]);
            }

            return new ByAll(byArray);
        }
    }
}
