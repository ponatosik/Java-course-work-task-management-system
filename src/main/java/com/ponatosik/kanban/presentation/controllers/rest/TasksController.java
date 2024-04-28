package com.ponatosik.kanban.presentation.controllers.rest;

import com.ponatosik.kanban.application.interfaces.Mediator;
import com.ponatosik.kanban.application.requests.*;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.presentation.requests.CreateTaskRequest;
import com.ponatosik.kanban.presentation.requests.UpdateTaskRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks/{groupId}")
public class TasksController {
    private final Mediator mediator;

    public TasksController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping()
    public Task create(@PathVariable Integer groupId, @RequestBody CreateTaskRequest request) {
        return mediator.send(new CreateTaskCommand(groupId, request.statusId(), request.title()));
    }

    @PatchMapping("{id}")
    public Task update(@PathVariable int groupId, @PathVariable int id, @RequestBody UpdateTaskRequest request) {
        return mediator.send(new UpdateTaskCommand(
                groupId,
                id,
                request.statusId(),
                request.title(),
                request.description(),
                request.deadline() ));
    }

    @GetMapping()
    public List<Task> get(@PathVariable Integer groupId) {
        List<Task> tasks = mediator.send(new GetTasksQuery(groupId));
        return tasks;
    }
}
