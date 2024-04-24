package com.ponatosik.kanban.application.requests;

import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.interfaces.Request;

import java.util.List;

public record GetStatusesQuery(Integer groupId) implements Request<List<Status>> {
}

