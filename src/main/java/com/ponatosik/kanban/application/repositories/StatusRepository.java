package com.ponatosik.kanban.application.repositories;

import com.ponatosik.kanban.core.entities.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends CrudRepository<Status, Integer> {
    List<Status> findByGroupId(Integer groupId);
}
