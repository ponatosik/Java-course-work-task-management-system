package com.ponatosik.kanban.presentation.controllers.rest;

import com.ponatosik.kanban.application.interfaces.Mediator;
import com.ponatosik.kanban.application.requests.*;
import com.ponatosik.kanban.core.entities.Task;
import com.ponatosik.kanban.presentation.requests.CreateTaskRequest;
import com.ponatosik.kanban.presentation.requests.MoveTaskOrderRequest;
import com.ponatosik.kanban.presentation.requests.SwapTasksOrderRequest;
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

    @PostMapping("swap")
    public void swapOrder(@PathVariable Integer groupId, @RequestBody SwapTasksOrderRequest request) {
        mediator.send(new SwapTasksOrderCommand(groupId, request.taskId1(), request.taskId2()));
    }

    @PostMapping("move")
    public void moveOrder(@PathVariable Integer groupId, @RequestBody MoveTaskOrderRequest request) {
        mediator.send(new MoveTaskOrderCommand(groupId, request.taskId(), request.newOrder()));
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
        return mediator.send(new GetTasksQuery(groupId));
    }
}
