package com.ponatosik.kanban.application.handlers;

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
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;

@Handler(request = CreateTaskCommand.class)
public class CreateTaskCommandHandler implements RequestHandler<CreateTaskCommand, Task> {
    private final TaskRepository taskRepository;
    private final GroupsRepository groupsRepository;

    public CreateTaskCommandHandler(TaskRepository taskRepository, GroupsRepository groupsRepository) {
        this.taskRepository = taskRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Task handle(CreateTaskCommand command) {
        Group group = groupsRepository.findById(command.groupId()).orElseThrow(() ->
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

