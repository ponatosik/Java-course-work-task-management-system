package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.GetTasksQuery;

import java.util.List;

@Handler(request = GetTasksQuery.class)
public class GetTasksQueryHandler implements RequestHandler<GetTasksQuery, List<Task>> {
    private final TaskRepository taskRepository;
    private final GroupsRepository groupsRepository;

    public GetTasksQueryHandler(TaskRepository taskRepository, GroupsRepository groupsRepository) {
        this.taskRepository = taskRepository;
        this.groupsRepository = groupsRepository;
    }


    @Override
    public List<Task> handle(GetTasksQuery command) {
       return groupsRepository.findById(command.groupId()).map(Group::getTasks).orElseThrow();
    }
}
