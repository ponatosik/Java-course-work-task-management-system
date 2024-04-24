package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.application.interfaces.Request;

public record CreateGroupCommand(String groupName) implements Request<Group> {
}

