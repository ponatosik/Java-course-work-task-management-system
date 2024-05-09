package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.requests.DeleteGroupCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = DeleteGroupCommand.class)
public class DeleteGroupCommandHandler implements RequestHandler<DeleteGroupCommand, Boolean> {
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public DeleteGroupCommandHandler(GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Boolean handle(DeleteGroupCommand command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));

        groupsRepository.delete(group);
        return true;
    }
}

