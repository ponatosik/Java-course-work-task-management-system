package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.requests.GetTasksQuery;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

import java.util.List;

@Handler(request = GetTasksQuery.class)
public class GetTasksQueryHandler implements RequestHandler<GetTasksQuery, List<Task>> {
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public GetTasksQueryHandler(GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public List<Task> handle(GetTasksQuery command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));

       return groupsRepository.findById(group.getId()).map(Group::getTasks).orElseThrow(() ->
               new UnknownGroupException(command.groupId()));
    }
}
