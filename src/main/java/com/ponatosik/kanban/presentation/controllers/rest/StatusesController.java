package com.ponatosik.kanban.presentation.controllers.rest;

import com.ponatosik.kanban.application.interfaces.Mediator;
import com.ponatosik.kanban.application.requests.*;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.presentation.requests.CreateStatusRequest;
import com.ponatosik.kanban.presentation.requests.UpdateStatusRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/statuses/{groupId}")
public class StatusesController {
    private final Mediator mediator;

    public StatusesController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping()
    public Status create(@PathVariable Integer groupId, @RequestBody CreateStatusRequest request) {
        return mediator.send(new CreateStatusCommand(groupId, request.caption()));
    }

    @PatchMapping("{id}")
    public Status update(@PathVariable int groupId, @PathVariable int id, @RequestBody UpdateStatusRequest request) {
        return mediator.send(new UpdateStatusCommand(groupId, id, request.caption()));
    }

    @GetMapping()
    public List<Status> get(@PathVariable Integer groupId) {
        return mediator.send(new GetStatusesQuery(groupId));
    }
}
