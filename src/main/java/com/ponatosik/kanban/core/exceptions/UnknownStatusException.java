package com.ponatosik.kanban.core.exceptions;

public class UnknownStatusException extends RuntimeException {
    int requestedStatusId;
    int groupId;

    public UnknownStatusException(int requestedStatusId, int groupId) {
        super(generateErrorMessage(requestedStatusId, groupId));
        this.groupId = groupId;
        this.requestedStatusId = requestedStatusId;
    }

    public  UnknownStatusException(int requestedStatusId, int groupId, Throwable err) {
        super(generateErrorMessage(requestedStatusId, groupId), err);
        this.groupId = groupId;
        this.requestedStatusId = requestedStatusId;
    }


    private static String generateErrorMessage(int requestedStatusId, int groupId) {
        return "Status with id " + requestedStatusId + " was not found in group with id " + groupId;
    }
}
