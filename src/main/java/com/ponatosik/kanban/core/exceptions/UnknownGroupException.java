package com.ponatosik.kanban.core.exceptions;

public class UnknownGroupException extends RuntimeException {
    int requestedGroupId;

    public UnknownGroupException(int requestedGroupId) {
        super(generateErrorMessage(requestedGroupId));
        this.requestedGroupId = requestedGroupId;
    }

    public UnknownGroupException(int requestedGroupId, Throwable err) {
        super(generateErrorMessage(requestedGroupId), err);
        this.requestedGroupId = requestedGroupId;
    }


    private static String generateErrorMessage(int requestedGroupId) {
        return "Group with id " + requestedGroupId + " was not found";
    }
}
