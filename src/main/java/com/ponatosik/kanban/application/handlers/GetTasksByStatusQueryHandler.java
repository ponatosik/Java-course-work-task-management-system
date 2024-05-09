package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.GetTasksByStatusQuery;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

import java.util.List;

@Handler(request = GetTasksByStatusQuery.class)
public class GetTasksByStatusQueryHandler implements RequestHandler<GetTasksByStatusQuery, List<Task>> {
    private final TaskRepository taskRepository;
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public GetTasksByStatusQueryHandler(TaskRepository taskRepository, GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public List<Task> handle(GetTasksByStatusQuery command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));
        if (!groupsRepository.existsById(command.groupId())) {
            throw new UnknownGroupException(command.groupId());
        }

        return taskRepository.findByGroupIdAndStatusId(group.getId(), command.statusId());
    }
}
