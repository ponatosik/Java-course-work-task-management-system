package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;
import com.ponatosik.kanban.core.entities.Task;

import java.util.List;

public record GetTasksByStatusQuery(Integer groupId, Integer statusId) implements Request<List<Task>> {
}
