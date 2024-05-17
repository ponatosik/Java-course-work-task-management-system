package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.CreateStatusCommand;
import com.ponatosik.kanban.core.entities.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateStatusCommandHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private CreateStatusCommandHandler handler;

    @BeforeEach
    void setUp () {
        when(groupsRepository.findById(any(Integer.class)))
                .then(x -> Optional.of(Group.createGroup(x.getArgument(0), "test group")));
    }

    @Test
    void givenValidCommand_shouldCreateStatusWithCaption() {
        var expectedCaption = "test status";
        var command = new CreateStatusCommand(0, expectedCaption);

        var actual = handler.handle(command);

        assertEquals(expectedCaption, actual.getCaption());
    }

    @Test
    void givenValidCommand_shouldSaveStatusInRepository() {
        var expectedCaption = "test status";
        var command = new CreateStatusCommand(0, expectedCaption);

        var actual = handler.handle(command);

        verify(statusRepository).save(actual);
    }
}