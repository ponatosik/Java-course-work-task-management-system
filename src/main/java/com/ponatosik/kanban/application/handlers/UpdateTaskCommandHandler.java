package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.UpdateTaskCommand;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;

@Handler(request = UpdateTaskCommand.class)
public class UpdateTaskCommandHandler implements RequestHandler<UpdateTaskCommand, Task> {
    private final TaskRepository taskRepository;

    public UpdateTaskCommandHandler(TaskRepository TaskRepository) {
        this.taskRepository = TaskRepository;
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

        taskRepository.save(task);
        return task;
    }
}

