package com.ponatosik.kanban.presentation.controllers.rest;

import com.ponatosik.kanban.application.interfaces.Mediator;
import com.ponatosik.kanban.application.requests.CreateGroupCommand;
import com.ponatosik.kanban.application.requests.DeleteGroupCommand;
import com.ponatosik.kanban.application.requests.GetGroupsQuery;
import com.ponatosik.kanban.application.requests.UpdateGroupCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.presentation.requests.CreateGroupRequest;
import com.ponatosik.kanban.presentation.requests.UpdateGroupRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
@SecurityRequirement(name = "bearer")
public class GroupsController {
    private final Mediator mediator;

    public GroupsController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping()
    public Group createGroup(@RequestBody CreateGroupRequest request) {
        return mediator.send(new CreateGroupCommand(request.groupName()));
    }

    @PatchMapping("{id}")
    public Group update(@PathVariable int id, @RequestBody UpdateGroupRequest request) {
       return mediator.send(new UpdateGroupCommand(id, request.groupName()));
    }

    @GetMapping()
    public List<Group> getGroups() {
        return mediator.send(new GetGroupsQuery());
    }

    @DeleteMapping("{id}")
    public void deleteGroup (@PathVariable int id) {
        mediator.send(new DeleteGroupCommand(id));
    }
}
