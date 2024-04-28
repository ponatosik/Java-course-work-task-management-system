package com.ponatosik.kanban.application.repositories;

import com.ponatosik.kanban.core.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByGroupId(Integer groupId);
    List<Task> findByGroupIdAndStatusId(Integer groupId, Integer statusId);
}
