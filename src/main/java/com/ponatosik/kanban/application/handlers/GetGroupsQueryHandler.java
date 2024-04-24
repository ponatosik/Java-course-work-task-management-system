package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.requests.GetGroupsQuery;
import com.ponatosik.kanban.application.annotations.Handler;

import java.util.ArrayList;
import java.util.List;

@Handler(request = GetGroupsQuery.class)
public class GetGroupsQueryHandler implements RequestHandler<GetGroupsQuery, List<Group>> {
    private final GroupsRepository groupsRepository;

    public GetGroupsQueryHandler(GroupsRepository groupsRepository) {
        this.groupsRepository = groupsRepository;
    }

    @Override
    public List<Group> handle(GetGroupsQuery command) {
        ArrayList<Group> list = new ArrayList<>();
        groupsRepository.findAll().forEach(list::add);
        return list;
    }
}

