package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.requests.GetGroupsQuery;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

import java.util.List;

@Handler(request = GetGroupsQuery.class)
public class GetGroupsQueryHandler implements RequestHandler<GetGroupsQuery, List<Group>> {
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public GetGroupsQueryHandler(GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<Group> handle(GetGroupsQuery command) {
        User user = userService.getAuthenticatedUser().orElseThrow();
        if (!userRepository.existsById(user.getId())) {
            user = userRepository.save(user);
        }
        user = userRepository.findById(user.getId()).orElseThrow();

        var a =user.getGroups();
        return a;
    }
}

