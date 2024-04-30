package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.requests.UpdateGroupCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;

@Handler(request = UpdateGroupCommand.class)
public class UpdateGroupCommandHandler implements RequestHandler<UpdateGroupCommand, Group> {
    private final GroupsRepository groupsRepository;

    public UpdateGroupCommandHandler(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Group handle(UpdateGroupCommand command) {
        Group group = groupsRepository.findById(command.groupId()).orElseThrow(() ->
                new UnknownGroupException(command.groupId()));

        if(command.groupName() != null) {
           group.setTitle(command.groupName());
        }

        groupsRepository.save(group);
        return group;
    }
}

