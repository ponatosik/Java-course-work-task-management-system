package com.ponatosik.kanban.application.interfaces;

import com.ponatosik.kanban.core.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getAuthenticatedUser();
}
