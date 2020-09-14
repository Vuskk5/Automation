package net.bsmch.exceptions;

import net.bsmch.PageObject;

public class PageInstantiationException extends RuntimeException {
    private Throwable cause;
    private Class<? extends PageObject> page;

    public PageInstantiationException() {
        super();
    }

    public PageInstantiationException(Class<? extends PageObject> page) {
        super();
        this.page = page;
    }

    public PageInstantiationException(Class<? extends PageObject> page, Throwable exception) {
        super();
        this.page = page;
        this.cause = exception;
    }

    @Override
    public String toString() {
        String message = "Could not instantiate page";
        message += (page != null) ? " of type " + page.getName() : "";
        return message + ".\nCaused by " + cause.toString();
    }
}
