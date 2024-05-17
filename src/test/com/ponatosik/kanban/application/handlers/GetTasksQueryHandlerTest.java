package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.GetTasksQuery;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.core.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetTasksQueryHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private GetTasksQueryHandler handler;

    private Group savedGroup = Group.createGroup(0, "test group");
    private Status savedStatus = savedGroup.createStatus(0, "test status");

    private List<Task> savedTasks = List.of(
            savedGroup.createTask(0,  "task 1", savedStatus),
            savedGroup.createTask(0,  "task 1", savedStatus),
            savedGroup.createTask(0,  "task 1", savedStatus));


    @BeforeEach
    void setUp() {
        when(groupsRepository.findById(any(Integer.class))).then(x -> Optional.of(savedGroup));
        when(taskRepository.findByGroupId(any(Integer.class))).then(x -> savedTasks);
    }

    @Test
    void givenValidCommand_shouldReturnSavedGroups() {
        var command = new GetTasksQuery(0);

        var actual = handler.handle(command);

        assertEquals(savedTasks, actual);
    }
}