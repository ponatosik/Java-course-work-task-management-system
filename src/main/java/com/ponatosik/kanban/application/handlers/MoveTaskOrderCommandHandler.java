package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.annotations.Handler;
import com.ponatosik.kanban.application.interfaces.RequestHandler;
import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.application.requests.MoveTaskOrderCommand;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Task;

@Handler(request = MoveTaskOrderCommand.class)
public class MoveTaskOrderCommandHandler implements RequestHandler<MoveTaskOrderCommand, Boolean> {

    private final GroupsRepository groupsRepository;
    private final TaskRepository taskRepository;

    public MoveTaskOrderCommandHandler(GroupsRepository groupsRepository, TaskRepository taskRepository) {
        this.groupsRepository = groupsRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public Boolean handle(MoveTaskOrderCommand command) {
        Group group = groupsRepository.findById(command.groupId()).orElseThrow();
        Task task = group.getTasks().stream().filter(t -> t.getId() == command.taskId()).findAny().orElseThrow();

        var shiftedTasks = group.moveTaskOrder(task, command.newOrder());

        taskRepository.saveAll(shiftedTasks);
        taskRepository.save(task);
        return true;
    }
}
