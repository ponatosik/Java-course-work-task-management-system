package com.ponatosik.kanban.core.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    Group group = Group.createGroup(0, "test group");

    @BeforeEach
    void setUp() {
        Status status = group.createStatus(0, "test status");
        group.createTask(0, "task 1", status);
        group.createTask(1, "task 2", status);
        group.createTask(2, "task 3", status);
        group.createTask(3, "task 4", status);
    }

    @Test
    void moveTaskOrder_givenTasks_shouldHaveCorrectOrdering() {
        var task = group.getTasks().get(1);
        int newOrder = group.getTasks().get(3).getOrder();

        group.moveTaskOrder(task, newOrder);

        var tasksByOrder = group.getTasks().stream().sorted(Comparator.comparing(Task::getOrder)).toList();
        assertTrue(tasksByOrder.get(0).getOrder() == tasksByOrder.get(1).getOrder() - 1);
        assertTrue(tasksByOrder.get(1).getOrder() == tasksByOrder.get(2).getOrder() - 1);
        assertTrue(tasksByOrder.get(2).getOrder() == tasksByOrder.get(3).getOrder() - 1);
    }

    @Test
    void moveTaskOrder_givenTasks_shouldReturnShiftedTasksShifted() {
        var task = group.getTasks().get(1);
        int newOrder = group.getTasks().get(3).getOrder();
        var expected = List.of(group.getTasks().get(2), group.getTasks().get(3));

        var actual = group.moveTaskOrder(task, newOrder);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }
}