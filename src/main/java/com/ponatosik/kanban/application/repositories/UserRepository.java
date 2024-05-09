package com.ponatosik.kanban.application.repositories;

import com.ponatosik.kanban.core.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
