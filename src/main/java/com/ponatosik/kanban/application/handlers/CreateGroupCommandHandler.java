package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.requests.CreateGroupCommand;

@Handler(request = CreateGroupCommand.class)
public class CreateGroupCommandHandler implements RequestHandler<CreateGroupCommand, Group> {
    private final GroupsRepository groupsRepository;

    public CreateGroupCommandHandler(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Group handle(CreateGroupCommand command) {
        Group group = Group.createGroup(null, command.groupName());
        group = groupsRepository.save(group);
        return group;
    }
}

