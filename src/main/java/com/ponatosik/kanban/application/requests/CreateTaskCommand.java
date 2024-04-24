package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.application.interfaces.Request;

import java.time.LocalDateTime;

public record CreateTaskCommand(
        int groupId,
        int statusId,
        String title,
        String description,
        LocalDateTime deadline
) implements Request<Task> {
    public CreateTaskCommand(int groupId, int statusId, String title, String description) {
        this(groupId, statusId, title, description, null);
    }

    public CreateTaskCommand(int groupId, int statusId, String title) {
        this(groupId, statusId, title, null, null);
    }
}

