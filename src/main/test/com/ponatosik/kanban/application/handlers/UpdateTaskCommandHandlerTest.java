package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.UpdateTaskCommand;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class UpdateTaskCommandHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private UpdateTaskCommandHandler handler;

    @Test
    void givenValidCommand_shouldUpdateTaskWithTitle() {
        var expectedTitle = "test task";
        var command = new UpdateTaskCommand(0, 0, 0, expectedTitle);

        var actual = handler.handle(command);

        assertEquals(expectedTitle, actual.getTitle());
    }

    @Test
    void givenValidCommand_shouldSaveTaskInRepository() {
        var expectedTitle = "test task";
        var command = new UpdateTaskCommand(0, 0, 0, expectedTitle);

        var actual = handler.handle(command);

        verify(taskRepository).save(actual);
    }
}