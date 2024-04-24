package com.ponatosik.kanban.application.implementation;

import com.ponatosik.kanban.application.annotations.Handler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.ponatosik.kanban.application.interfaces.Mediator;
import com.ponatosik.kanban.application.interfaces.Request;
import com.ponatosik.kanban.application.interfaces.RequestHandler;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Component
public class SpringContextMediator implements Mediator, ApplicationContextAware {
    private Map<Class<Request<?>>, RequestHandler<? extends Request<?>, ?>> handlers;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        handlers = new HashMap<>();
        String[] handlerNames = applicationContext.getBeanNamesForAnnotation(Handler.class);
        for (String handlerName: handlerNames) {
            RequestHandler<? extends Request<?>, ?> handler = (RequestHandler<? extends Request<?>, ?>) applicationContext.getBean(handlerName);
            Handler info = handler.getClass().getAnnotation(Handler.class);
            Class<? extends Request<?>> classInfo = info.request();
            handlers.put((Class<Request<?>>) classInfo, handler);
        }
    }

    @Override
    public <V> V send(Request<V> request) {
        Class<Request<V>> classInfo = (Class<Request<V>>) request.getClass();
        RequestHandler<Request<V>, V> handler = (RequestHandler<Request<V>, V>) handlers.get(classInfo);
        return handler.handle(request);
    }
}
