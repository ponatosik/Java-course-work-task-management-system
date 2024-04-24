package com.ponatosik.kanban.application.interfaces;

public interface Mediator {
     <V> V send(Request<V> request);
}
