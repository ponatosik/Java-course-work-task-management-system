package com.ponatosik.kanban.presentation.requests;

import java.time.LocalDateTime;

public record CreateTaskRequest(String title, String description, Integer statusId, LocalDateTime deadline) { }
