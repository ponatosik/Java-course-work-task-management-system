package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.GetStatusesQuery;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;

import java.util.List;

@Handler(request = GetStatusesQuery.class)
public class GetStatusesQueryHandler implements RequestHandler<GetStatusesQuery, List<Status>> {
    private final StatusRepository statusRepository;
    private final GroupsRepository groupsRepository;

    public GetStatusesQueryHandler(StatusRepository statusRepository, GroupsRepository groupsRepository) {
        this.statusRepository = statusRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public List<Status> handle(GetStatusesQuery command) {
        if (!groupsRepository.existsById(command.groupId())) {
            throw new UnknownGroupException(command.groupId());
        }

        return statusRepository.findByGroupId(command.groupId());
    }
}

