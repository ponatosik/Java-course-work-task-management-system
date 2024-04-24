package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.application.interfaces.Request;

import java.util.List;

public record GetTasksQuery(Integer groupId) implements Request<List<Task>> {
}
