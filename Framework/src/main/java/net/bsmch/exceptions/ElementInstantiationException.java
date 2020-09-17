package net.bsmch.exceptions;

import net.bsmch.PageObject;
import org.selophane.elements.base.Element;

public class ElementInstantiationException extends RuntimeException {
    private Throwable cause;
    private Class<? extends Element> element;

    public ElementInstantiationException() {
        super();
    }

    public ElementInstantiationException(Class<? extends Element> element) {
        super();
        this.element = element;
    }

    public ElementInstantiationException(Class<? extends Element> element, Throwable exception) {
        super();
        this.element = element;
        this.cause = exception;
    }

    @Override
    public String toString() {
        String message = "Could not instantiate element";
        message += (element != null) ? " of type " + element.getName() : "";
        return message + ".\nCaused by " + cause.toString();
    }
}
