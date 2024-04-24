package com.ponatosik.kanban.application.interfaces;

public interface RequestHandler<T extends Request<V>, V>  {
    V handle(T command);
}
