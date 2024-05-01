package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;
import com.ponatosik.kanban.core.entities.Group;

public record DeleteGroupCommand(int groupId) implements Request<Boolean> {
}

