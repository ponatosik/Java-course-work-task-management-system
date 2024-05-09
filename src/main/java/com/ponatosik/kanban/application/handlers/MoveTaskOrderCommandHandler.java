package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.MoveTaskOrderCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = MoveTaskOrderCommand.class)
public class MoveTaskOrderCommandHandler implements RequestHandler<MoveTaskOrderCommand, Boolean> {

    private final GroupsRepository groupsRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public MoveTaskOrderCommandHandler(GroupsRepository groupsRepository, TaskRepository taskRepository, UserRepository userRepository, UserService userService) {
        this.groupsRepository = groupsRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Boolean handle(MoveTaskOrderCommand command) {
        User user = userService.getAuthenticatedUser()
                .map(usr -> userRepository.findById(usr.getId()))
                .orElseThrow().orElseThrow();

        Group group = user.getGroups().stream()
                .filter(grp -> grp.getId().equals(command.groupId()))
                .findAny()
                .orElseThrow(() ->
                        new UnknownGroupException(command.groupId()));

        Task task = group.getTasks().stream().filter(t -> t.getId() == command.taskId()).findAny().orElseThrow(() ->
                new UnknownTaskException(command.taskId(), command.groupId()));

        var shiftedTasks = group.moveTaskOrder(task, command.newOrder());

        taskRepository.saveAll(shiftedTasks);
        taskRepository.save(task);
        return true;
    }
}
