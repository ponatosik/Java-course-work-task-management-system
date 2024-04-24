package com.ponatosik.kanban.presentation.requests;

import java.time.LocalDateTime;

public record UpdateTaskRequest(String title, String description, Integer statusId, LocalDateTime deadline) { }
