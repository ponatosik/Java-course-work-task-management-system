package com.ponatosik.kanban.presentation.requests;

public record MoveTaskOrderRequest(Integer taskId, int newOrder) { }
