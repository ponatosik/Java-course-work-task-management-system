package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.requests.UpdateGroupCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = UpdateGroupCommand.class)
public class UpdateGroupCommandHandler implements RequestHandler<UpdateGroupCommand, Group> {
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public UpdateGroupCommandHandler(GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Group handle(UpdateGroupCommand command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));

        if(command.groupName() != null) {
           group.setTitle(command.groupName());
        }

        groupsRepository.save(group);
        return group;
    }
}

