package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;
import com.ponatosik.kanban.core.entities.Task;

import java.time.LocalDateTime;

public record UpdateTaskCommand(
        int groupId,
        int taskId,
        Integer statusId,
        String title,
        String description,
        LocalDateTime deadline
) implements Request<Task> {
    public UpdateTaskCommand(int groupId, int taskId, Integer statusId, String title, String description) {
        this(groupId, taskId, statusId, title, description, null);
    }

    public UpdateTaskCommand(int groupId, int taskId, Integer statusId, String title) {
        this(groupId, taskId, statusId, title, null, null);
    }

    public UpdateTaskCommand(int groupId, int taskId, Integer statusId) {
        this(groupId, taskId, statusId, null, null, null);
    }
}

