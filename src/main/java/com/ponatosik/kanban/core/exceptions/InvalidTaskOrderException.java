package com.ponatosik.kanban.core.exceptions;

import lombok.Value;

@Value
public class InvalidTaskOrderException extends IndexOutOfBoundsException {
    int requestedTaskId;
    int requestedTaskOrder;
    int maxAvailableOrder;
    int groupId;

    public InvalidTaskOrderException(int requestedTaskId, int requestedTaskOrder, int maxAvailableOrder, int groupId) {
        super(generateErrorMessage(requestedTaskId,requestedTaskOrder,maxAvailableOrder,groupId));
        this.requestedTaskId = requestedTaskId;
        this.requestedTaskOrder = requestedTaskOrder;
        this.maxAvailableOrder = maxAvailableOrder;
        this.groupId = groupId;
    }

    private static String generateErrorMessage(int requestedTaskId, int requestedTaskOrder, int maxAvailableOrder, int groupId) {
        return "Cannot move task with id " + requestedTaskId + " to order " + requestedTaskOrder + " in group with id " + groupId;
    }
}
