package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;

public record  SwapTasksOrderCommand(int groupId, int taskId1, int taskId2) implements Request<Boolean> {
}
