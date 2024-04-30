package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.GetTasksByStatusQuery;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;

import java.util.List;

@Handler(request = GetTasksByStatusQuery.class)
public class GetTasksByStatusQueryHandler implements RequestHandler<GetTasksByStatusQuery, List<Task>> {
    private final TaskRepository taskRepository;
    private final GroupsRepository groupsRepository;

    public GetTasksByStatusQueryHandler(TaskRepository taskRepository, GroupsRepository groupsRepository) {
        this.taskRepository = taskRepository;
        this.groupsRepository = groupsRepository;
    }


    @Override
    public List<Task> handle(GetTasksByStatusQuery command) {
        if (!groupsRepository.existsById(command.groupId())) {
            throw new UnknownGroupException(command.groupId());
        }

        return taskRepository.findByGroupIdAndStatusId(command.groupId(), command.statusId());
    }
}
