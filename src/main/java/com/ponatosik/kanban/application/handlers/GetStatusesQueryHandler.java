package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.GetStatusesQuery;

import java.util.List;

@Handler(request = GetStatusesQuery.class)
public class GetStatusesQueryHandler implements RequestHandler<GetStatusesQuery, List<Status>> {
    private final StatusRepository statusRepository;

    public GetStatusesQueryHandler(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> handle(GetStatusesQuery command) {
        return statusRepository.findByGroupId(command.groupId());
    }
}

