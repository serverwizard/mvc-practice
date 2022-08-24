package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestMappingHandlerMapping handlerMapping;

    private List<HandlerAdapter> handlerAdapters;

    private List<ViewResolver> viewResolvers;

    @Override
    public void init() {
        handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.init();

        handlerAdapters = Collections.singletonList(new SimpleControllerHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Controller controller = handlerMapping.findHandler(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));

        try {
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                    .filter(ha -> ha.supports(controller))
                    .findFirst()
                    .orElseThrow(() -> new ServletException("No adapter for handler [" + controller + "]"));

            ModelAndView modelAndView = handlerAdapter.handle(request, response, controller);

            for (ViewResolver viewResolver : this.viewResolvers) {
                View view = viewResolver.resolveViewName(modelAndView.getViewName());
                view.render(modelAndView.getModel(), request, response);
            }
        } catch (Throwable e) {
            logger.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
