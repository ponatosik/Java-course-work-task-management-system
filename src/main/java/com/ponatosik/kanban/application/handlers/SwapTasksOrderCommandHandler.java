package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.SwapTasksOrderCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;

@Handler(request = SwapTasksOrderCommand.class)
public class SwapTasksOrderCommandHandler implements RequestHandler<SwapTasksOrderCommand, Boolean> {

    private final GroupsRepository groupsRepository;
    private final TaskRepository taskRepository;

    public SwapTasksOrderCommandHandler(GroupsRepository groupsRepository, TaskRepository taskRepository) {
        this.groupsRepository = groupsRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Boolean handle(SwapTasksOrderCommand command) {
        Group group = groupsRepository.findById(command.groupId()).orElseThrow(() ->
                new UnknownGroupException(command.groupId()));
        Task task1 = group.getTasks().stream().filter(task -> task.getId() == command.taskId1()).findAny().orElseThrow(() ->
                new UnknownTaskException(command.taskId1(), command.groupId()));
        Task task2 = group.getTasks().stream().filter(task -> task.getId() == command.taskId2()).findAny().orElseThrow(() ->
                new UnknownTaskException(command.taskId2(), command.groupId()));

        group.swapTasksOrder(task1, task2);

        taskRepository.save(task1);
        taskRepository.save(task2);
        return true;
    }
}
