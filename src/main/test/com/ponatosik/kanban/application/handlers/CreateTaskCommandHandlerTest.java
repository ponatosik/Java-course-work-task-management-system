package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.CreateTaskCommand;
import org.junit.jupiter.api.*;
import org.mockito.*;

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
}