package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.CreateGroupCommand;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class CreateGroupCommandHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private CreateGroupCommandHandler handler;

    @Test
    void givenValidCommand_shouldCreateGroupWithTitle() {
        var expectedTitle = "test group";
        var command = new CreateGroupCommand(expectedTitle);

        var actual = handler.handle(command);

        assertEquals(expectedTitle, actual.getTitle());
    }

    @Test
    void givenValidCommand_shouldSaveGroupInRepository() {
        var expectedTitle = "test group";
        var command = new CreateGroupCommand(expectedTitle);

        var actual = handler.handle(command);

        verify(groupsRepository).save(actual);
    }
}