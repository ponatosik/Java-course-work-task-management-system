package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.UpdateTaskCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;
import com.ponatosik.kanban.core.exceptions.UnknownStatusException;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;

@Handler(request = UpdateTaskCommand.class)
public class UpdateTaskCommandHandler implements RequestHandler<UpdateTaskCommand, Task> {
    private final TaskRepository taskRepository;
    private final GroupsRepository groupsRepository;

    public UpdateTaskCommandHandler(TaskRepository TaskRepository, GroupsRepository groupsRepository) {
        this.taskRepository = TaskRepository;
        this.groupsRepository = groupsRepository;
    }

    @Override
    public Task handle(UpdateTaskCommand command) {
        Task task = taskRepository.findById(command.taskId()).orElseThrow(() ->
                new UnknownTaskException(command.taskId(), command.groupId()));

        if(command.title() != null) {
            task.setTitle(command.title());
        }
        if(command.description() != null) {
            task.setDescription(command.description());
        }
        if(command.title() != null) {
            task.setDeadline(command.deadline());
        }
        if(command.statusId() != null) {
            Group group = groupsRepository.findById(command.groupId()).orElseThrow(() ->
                    new UnknownGroupException(command.groupId()));

            Status status = group.getStatuses().stream()
                    .filter(stat -> stat.getGroup().getId().equals(command.statusId()))
                    .findAny()
                    .orElseThrow(() -> new UnknownStatusException(command.statusId(), command.groupId()));

            task.setStatus(status);
        }

        taskRepository.save(task);
        return task;
    }
}

