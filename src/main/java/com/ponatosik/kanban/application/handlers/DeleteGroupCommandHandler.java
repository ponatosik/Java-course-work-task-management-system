package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.requests.DeleteGroupCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;

@Handler(request = DeleteGroupCommand.class)
public class DeleteGroupCommandHandler implements RequestHandler<DeleteGroupCommand, Boolean> {
    private final GroupsRepository groupsRepository;

    public DeleteGroupCommandHandler(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Boolean handle(DeleteGroupCommand command) {
        Group group = groupsRepository.findById(command.groupId()).orElseThrow(() ->
                new UnknownGroupException(command.groupId()));

        groupsRepository.delete(group);
        return true;
    }
}

