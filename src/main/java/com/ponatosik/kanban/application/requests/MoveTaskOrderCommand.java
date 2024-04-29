package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;

public record MoveTaskOrderCommand(int groupId, int taskId, int newOrder) implements Request<Boolean> {
}
