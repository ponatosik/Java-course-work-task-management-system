package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.CreateStatusCommand;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = CreateStatusCommand.class)
public class CreateStatusCommandHandler implements RequestHandler<CreateStatusCommand, Status> {
    private final StatusRepository statusRepository;
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CreateStatusCommandHandler(StatusRepository statusRepository, GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.statusRepository = statusRepository;
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Status handle(CreateStatusCommand command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                new UnknownGroupException(command.groupId()));

        Status status = group.createStatus(null, command.title());
        status = statusRepository.save(status);
        return status;
    }
}

