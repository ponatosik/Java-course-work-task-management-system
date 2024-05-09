package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.interfaces.UserService;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.requests.CreateGroupCommand;
import com.ponatosik.kanban.core.entities.User;
import com.ponatosik.kanban.application.repositories.UserRepository;

@Handler(request = CreateGroupCommand.class)
public class CreateGroupCommandHandler implements RequestHandler<CreateGroupCommand, Group> {
    private final GroupsRepository groupsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public CreateGroupCommandHandler(GroupsRepository groupsRepository, UserRepository userRepository, UserService userService) {
        this.groupsRepository = groupsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public Group handle(CreateGroupCommand command) {
        User user = userService.getAuthenticatedUser().orElseThrow();
        if(!userRepository.existsById(user.getId())) {
           user = userRepository.save(user);
        }

        Group group = Group.createGroup(null, command.groupName());
        group = groupsRepository.save(group);

        user.addGroup(group);
        userRepository.save(user);
        return group;
    }
}

