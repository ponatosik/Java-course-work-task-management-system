package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.GetTasksQuery;
import com.ponatosik.kanban.core.exceptions.UnknownGroupException;

import java.util.List;

@Handler(request = GetTasksQuery.class)
public class GetTasksQueryHandler implements RequestHandler<GetTasksQuery, List<Task>> {
    private final GroupsRepository groupsRepository;

    public GetTasksQueryHandler(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }


    @Override
    public List<Task> handle(GetTasksQuery command) {
       return groupsRepository.findById(command.groupId()).map(Group::getTasks).orElseThrow(() ->
               new UnknownGroupException(command.groupId()));
    }
}
