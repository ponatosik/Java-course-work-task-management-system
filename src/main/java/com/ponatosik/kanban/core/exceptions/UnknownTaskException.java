package com.ponatosik.kanban.core.exceptions;

import lombok.Value;

@Value
public class UnknownTaskException extends RuntimeException {
    int requestedTaskId;
    int groupId;

    public UnknownTaskException(int requestedTaskId, int groupId) {
        super(generateErrorMessage(requestedTaskId, groupId));
        this.groupId = groupId;
        this.requestedTaskId = requestedTaskId;
    }

    public  UnknownTaskException(int requestedTaskId, int groupId, Throwable err) {
        super(generateErrorMessage(requestedTaskId, groupId), err);
        this.groupId = groupId;
        this.requestedTaskId = requestedTaskId;
    }


    private static String generateErrorMessage(int requestedTaskId, int groupId) {
        return "Task with id " + requestedTaskId + " was not found in group with id " + groupId;
    }
}
