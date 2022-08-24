package org.example.mvc;

public interface HandlerMapping {
    Object findHandler(HandlerKey handlerKey);
}