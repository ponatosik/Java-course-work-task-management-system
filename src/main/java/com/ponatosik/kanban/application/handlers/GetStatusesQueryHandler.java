package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.requests.GetStatusesQuery;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

import java.util.List;

@Handler(request = GetStatusesQuery.class)
public class GetStatusesQueryHandler implements RequestHandler<GetStatusesQuery, List<Status>> {
    private final StatusRepository statusRepository;
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public GetStatusesQueryHandler(StatusRepository statusRepository, GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.statusRepository = statusRepository;
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<Status> handle(GetStatusesQuery command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));

        return statusRepository.findByGroupId(group.getId());
    }
}

