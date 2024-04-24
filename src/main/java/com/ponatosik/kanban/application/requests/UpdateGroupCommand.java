package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.application.interfaces.Request;
import com.ponatosik.kanban.core.entities.Group;

public record UpdateGroupCommand(int groupId, String groupName) implements Request<Group> {
}

