package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.CreateStatusCommand;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;

@Handler(request = CreateStatusCommand.class)
public class CreateStatusCommandHandler implements RequestHandler<CreateStatusCommand, Status> {
    private final StatusRepository statusRepository;
    private final GroupsRepository groupsRepository;

    public CreateStatusCommandHandler(StatusRepository statusRepository, GroupsRepository groupsRepository) {
        this.statusRepository = statusRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Status handle(CreateStatusCommand command) {
        Group group = groupsRepository.findById(command.groupId()).orElseThrow(() ->
                new UnknownGroupException(command.groupId()));

        Status status = group.createStatus(null, command.title());
        status = statusRepository.save(status);
        return status;
    }
}

