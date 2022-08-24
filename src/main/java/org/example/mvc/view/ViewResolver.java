package org.example.mvc.view;

public interface ViewResolver {
    View resolveViewName(String viewName);
}