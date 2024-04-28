package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.GetGroupsQuery;
import com.ponatosik.kanban.core.entities.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GetGroupsQueryHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private GetGroupsQueryHandler handler;

    private final List<Group> savedGroups = List.of(
            Group.createGroup(0, "test 1"),
            Group.createGroup(1, "test 2"),
            Group.createGroup(2, "test 3"));

    @BeforeEach
    void setUp() {
        when(groupsRepository.findAll()).then(x -> savedGroups);
    }

    @Test
    void givenValidCommand_shouldReturnSavedGroups() {
        var command = new GetGroupsQuery();

        var actual = handler.handle(command);

        assertEquals(savedGroups, actual);
    }
}