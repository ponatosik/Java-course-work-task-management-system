package com.ponatosik.kanban.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ponatosik.kanban.core.exceptions.InvalidTaskOrderException;
import com.ponatosik.kanban.core.exceptions.UnknownTaskException;
import com.ponatosik.kanban.core.exceptions.UnknownStatusException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "myGroup")
public class Group {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "group_sequence"
    )
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
            throw new UnknownStatusException(taskId, this.id);
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
            throw new UnknownTaskException(task1.getId(), this.id);
        }
        if (!tasks.contains(task2)) {
            throw new UnknownTaskException(task2.getId(), this.id);
        }

        int order1 = task1.getOrder();
        task1.setOrder(task2.getOrder());
        task2.setOrder(order1);
    }

    public List<Task> moveTaskOrder(Task task, int newOrder) {
       if (!tasks.contains(task)) {
           throw new UnknownTaskException(task.getId(), this.id);
       }
       if (tasks.size() < newOrder) {
           throw new InvalidTaskOrderException(task.getId(), newOrder, tasks.size(), this.id);
       }

        int taskOrder = task.getOrder();
        if(taskOrder == newOrder) {
            return Collections.emptyList();
        }

        List<Task> shiftedTasks;
        if(taskOrder > newOrder) {
            shiftedTasks = tasks.stream()
                    .filter(t -> t.getOrder() < taskOrder)
                    .filter(t -> t.getOrder() >= newOrder)
                    .toList();
        } else {
            shiftedTasks = tasks.stream()
                    .filter(t -> t.getOrder() > taskOrder)
                    .filter(t -> t.getOrder() <= newOrder)
                    .toList();
        }

        int shiftDirection = (int) Math.signum(taskOrder - newOrder);
        shiftedTasks.forEach(shiftedTask ->
                shiftedTask.setOrder(shiftedTask.getOrder() + shiftDirection));

        task.setOrder(newOrder);
        return shiftedTasks;
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
