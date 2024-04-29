package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.CreateTaskCommand;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTaskCommandHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private CreateTaskCommandHandler handler;

    @Test
    void givenValidCommand_shouldCreateTaskWithTitle() {
        var expectedTitle = "test task";
        var command = new CreateTaskCommand(0, 0, expectedTitle);

        var actual = handler.handle(command);

        assertEquals(expectedTitle, actual.getTitle());
    }

    @Test
    void givenValidCommand_shouldSaveTaskInRepository() {
        var expectedTitle = "test task";
        var command = new CreateTaskCommand(0, 0, expectedTitle);

        var actual = handler.handle(command);

        verify(taskRepository).save(actual);
    }

    @Test
    void givenMultipleCommands_shouldCreateTasksWithOrder() {
        var expectedTasks = List.of("task 1", "task 2", "task 3");
        var commands = List.of(
                new CreateTaskCommand(0, 0, expectedTasks.get(0)),
                new CreateTaskCommand(0, 0, expectedTasks.get(1)),
                new CreateTaskCommand(0, 0, expectedTasks.get(2))
        );

        var actual = List.of(
                handler.handle(commands.get(0)),
                handler.handle(commands.get(1)),
                handler.handle(commands.get(2)));

        assertEquals(expectedTasks.get(0), actual.get(0).getTitle());
        assertEquals(expectedTasks.get(1), actual.get(1).getTitle());
        assertEquals(expectedTasks.get(2), actual.get(2).getTitle());
        assertTrue(actual.get(0).getOrder() == actual.get(1).getOrder() - 1);
        assertTrue(actual.get(1).getOrder() == actual.get(2).getOrder() - 1);
    }
}