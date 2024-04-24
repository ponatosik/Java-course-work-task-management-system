package com.ponatosik.kanban.application.annotations;

import com.ponatosik.kanban.application.interfaces.Request;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Handler {
    Class<? extends Request<?>> request ();
}
