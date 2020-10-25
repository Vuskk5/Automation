package net.bsmch.support;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

import java.lang.reflect.*;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ElementFinder {
    public static Element proxy(SearchContext context, By locator) {
        InvocationHandler handler = new ElementProxy(context, locator);

        return (Element) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{Element.class, WebElement.class, WrapsElement.class, Locatable.class},
                handler
        );
    }

    @SuppressWarnings("unchecked")
    public static List<Element> proxyAll(SearchContext context, By locator) {
        InvocationHandler handler = new ElementListProxy(context, locator);

        return (List<Element>) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{List.class},
                handler
        );
    }

    static class ElementProxy implements InvocationHandler {
        private SearchContext context;
        private By locator;

        public ElementProxy(SearchContext context, By locator) {
            this.context = context;
            this.locator = locator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
            final WebElement element;

            try {
                element = context.findElement(locator);
            } catch (NoSuchElementException ex) {
                if ("toString".equals(method.getName())) {
                    return "Proxy element for: " + locator.toString();
                }

                throw ex;
            }

            if ("getWrappedElement".equals(method.getName())) {
                return element;
            }

            try {
                return method.invoke(proxy, objects);
            } catch (InvocationTargetException ex) {
                // Unwrap the underlying exception
                throw ex.getCause();
            }
        }

    }

    static class ElementListProxy implements InvocationHandler {
        private SearchContext context;
        private By locator;

        public ElementListProxy(SearchContext context, By locator) {
            this.context = context;
            this.locator = locator;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
            List<Element> wrappedList = new ArrayList<>();
            Constructor<ElementImpl> cons = ElementImpl.class.getConstructor(WebElement.class);
            for (WebElement element : context.findElements(locator)) {
                Element thing = cons.newInstance(element);
                wrappedList.add(thing);
            }

            try {
                return method.invoke(wrappedList, objects);
            }
            catch (InvocationTargetException ex) {
                // Unwrap the underlying exception
                throw ex.getCause();
            }
        }
    }
}
