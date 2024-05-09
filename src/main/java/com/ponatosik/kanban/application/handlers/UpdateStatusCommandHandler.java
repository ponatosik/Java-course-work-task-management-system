package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.UpdateStatusCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.exceptions.UnknownStatusException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = UpdateStatusCommand.class)
public class UpdateStatusCommandHandler implements RequestHandler<UpdateStatusCommand, Status> {
    private final StatusRepository StatusRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public UpdateStatusCommandHandler(StatusRepository StatusRepository, UserRepository userRepository, UserService userService) {
        this.StatusRepository = StatusRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Status handle(UpdateStatusCommand command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));
        Status status = StatusRepository.findById(command.statusId()).orElseThrow(() ->
                new UnknownStatusException(command.statusId(), group.getId()
                    ));

        if(command.title() != null) {
            status.setCaption(command.title());
        }

        StatusRepository.save(status);
        return status;
    }
}

