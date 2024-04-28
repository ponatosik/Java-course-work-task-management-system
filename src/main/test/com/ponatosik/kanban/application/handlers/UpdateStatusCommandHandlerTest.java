package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.UpdateStatusCommand;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class UpdateStatusCommandHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private UpdateStatusCommandHandler handler;

    @Test
    void givenValidCommand_shouldUpdateStatusWithCaption() {
        var expectedCaption = "test status";
        var command = new UpdateStatusCommand(0, 0, expectedCaption);

        var actual = handler.handle(command);

        assertEquals(expectedCaption, actual.getCaption());
    }

    @Test
    void givenValidCommand_shouldSaveStatusInRepository() {
        var expectedCaption = "test status";
        var command = new UpdateStatusCommand(0, 0, expectedCaption);

        var actual = handler.handle(command);

        verify(statusRepository).save(actual);
    }
}