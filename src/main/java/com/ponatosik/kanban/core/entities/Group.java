package com.ponatosik.kanban.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;
import com.ponatosik.kanban.core.exceptions.UnknownTaskStatusException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "myGroup")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Setter
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "taskOrder")
    @JoinColumn
    private List<Task> tasks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Status> statuses;

    public Task createTask(Integer taskId, String taskTitle, String description, LocalDateTime deadline, Status taskStatus) {
        if(!statuses.contains(taskStatus)){
            throw new UnknownTaskStatusException();
        }

        int taskOrder = tasks.size() + 1;
        Task task = new Task(taskId, taskTitle, description, deadline, taskStatus, this, taskOrder);
        tasks.add(task);
        return task;
    }

    public Task createTask(Integer taskId, String taskTitle, String description,  Status taskStatus) {
        return createTask(taskId, taskTitle, description, null, taskStatus);
    }

    public Task createTask(Integer taskId, String taskTitle, Status taskStatus) {
        return createTask(taskId, taskTitle, null, null, taskStatus);
    }

    public Status createStatus(Integer statusId, String statusCaption) {
        Status status = new Status(statusId, statusCaption, this);
        statuses.add(status);
        return status;
    }

    public void swapTasksOrder(Task task1, Task task2) {
        if (!tasks.contains(task1)) {
           throw new UnknownTaskException();
        }
        if (!tasks.contains(task2)) {
            throw new UnknownTaskException();
        }

        int order1 = task1.getOrder();
        task1.setOrder(task2.getOrder());
        task2.setOrder(order1);
    }

    public static Group createGroup(Integer id, String title) {
       Group group = new Group();
       group.id = id;
       group.title = title;
       group.statuses = new ArrayList<>();
       group.tasks = new ArrayList<>();
       return group;
    }
}
