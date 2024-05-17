package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.UpdateGroupCommand;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class UpdateGroupCommandHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private UpdateGroupCommandHandler handler;

    @Test
    void givenValidCommand_shouldReturnGroupWithUpdatedTitle() {
        var expectedTitle = "test group";
        var command = new UpdateGroupCommand(0, expectedTitle);

        var actual = handler.handle(command);

        assertEquals(expectedTitle, actual.getTitle());
    }

    @Test
    void givenValidCommand_shouldSaveGroupInRepository() {
        var expectedTitle = "test group";
        var command = new UpdateGroupCommand(0, expectedTitle);

        var actual = handler.handle(command);

        verify(groupsRepository).save(actual);
    }
}