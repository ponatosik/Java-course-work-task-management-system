package com.ponatosik.kanban.application;

import com.ponatosik.kanban.application.repositories.GroupsRepository;
import com.ponatosik.kanban.application.repositories.StatusRepository;
import com.ponatosik.kanban.application.repositories.TaskRepository;
import com.ponatosik.kanban.core.entities.Group;
import com.ponatosik.kanban.core.entities.Status;
import com.ponatosik.kanban.core.entities.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public abstract class ApplicationUnitTest {
    @Mock
    protected GroupsRepository groupsRepository;

    @Mock
    protected StatusRepository statusRepository;

    @Mock
    protected TaskRepository taskRepository;

    @BeforeEach
    void mocksSetUp() {
        var group = Group.createGroup(0, "test group");
        var status = group.createStatus(0, "test status");
        var task = group.createTask(0, "test task", status);

        when(groupsRepository.findById(any(Integer.class)))
                .then(x -> Optional.of(group));

        when(statusRepository.findById(any(Integer.class)))
                .then(x -> Optional.of(status));

        when(taskRepository.findById(any(Integer.class)))
                .then(x -> Optional.of(task));

        when(groupsRepository.save(any(Group.class))).then(x -> x.getArgument(0));
        when(statusRepository.save(any(Status.class))).then(x -> x.getArgument(0));
        when(taskRepository.save(any(Task.class))).then(x -> x.getArgument(0));
    }
}
