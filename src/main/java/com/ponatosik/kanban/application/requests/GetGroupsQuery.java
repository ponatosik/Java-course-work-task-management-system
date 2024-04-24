package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.application.interfaces.Request;

import java.util.List;

public record GetGroupsQuery() implements Request<List<Group>> {
}

