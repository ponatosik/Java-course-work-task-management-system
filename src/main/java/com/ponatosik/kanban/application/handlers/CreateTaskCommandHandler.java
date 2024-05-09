package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.CreateTaskCommand;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.exceptions.UnknownStatusException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = CreateTaskCommand.class)
public class CreateTaskCommandHandler implements RequestHandler<CreateTaskCommand, Task> {
    private final TaskRepository taskRepository;
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CreateTaskCommandHandler(TaskRepository taskRepository, GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Task handle(CreateTaskCommand command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));

        Status status = group.getStatuses().stream()
                .filter(stat -> stat.getGroup().getId().equals(command.statusId()))
                .findAny()
                .orElseThrow(() -> new UnknownStatusException(command.statusId(), command.groupId()));

        Task task = group.createTask(null, command.title(), status);
        taskRepository.save(task);
        return task;
    }
}

