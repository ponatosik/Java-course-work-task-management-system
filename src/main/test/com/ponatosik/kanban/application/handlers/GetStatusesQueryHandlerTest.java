package com.ponatosik.kanban.application.handlers;

import com.ponatosik.kanban.application.ApplicationUnitTest;
import com.ponatosik.kanban.application.requests.GetStatusesQuery;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetStatusesQueryHandlerTest extends ApplicationUnitTest {

    @InjectMocks
    private GetStatusesQueryHandler handler;

    private Group savedGroup = Group.createGroup(0, "test group");
    private List<Status> savedStatuses = List.of(
            savedGroup.createStatus(0, "status 1"),
            savedGroup.createStatus(1, "status 2"),
            savedGroup.createStatus(2, "status 3"));


    @BeforeEach
    void setUp() {
        when(groupsRepository.findById(any(Integer.class))).then(x -> Optional.of(savedGroup));
        when(statusRepository.findByGroupId(any(Integer.class))).then(x -> savedStatuses);
    }

    @Test
    void givenValidCommand_shouldReturnSavedGroups() {
        var command = new GetStatusesQuery(0);

        var actual = handler.handle(command);

        assertEquals(savedStatuses, actual);
    }
}