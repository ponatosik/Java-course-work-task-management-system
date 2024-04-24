package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.interfaces.Request;

public record CreateStatusCommand(int groupId, String title) implements Request<Status> {
}

