package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;
import com.ponatosik.kanban.core.entities.Status;

public record UpdateStatusCommand(int groupId, int statusId, String title) implements Request<Status> {
}

