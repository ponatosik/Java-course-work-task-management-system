package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.GetTasksByStatusQuery;
import com.ponatosik.kanban.core.entities.Task;

import java.util.List;

@Handler(request = GetTasksByStatusQuery.class)
public class GetTasksByStatusQueryHandler implements RequestHandler<GetTasksByStatusQuery, List<Task>> {
    private final TaskRepository taskRepository;

    public GetTasksByStatusQueryHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public List<Task> handle(GetTasksByStatusQuery command) {
       return taskRepository.findByGroupIdAndStatusId(command.groupId(), command.statusId());
    }
}
