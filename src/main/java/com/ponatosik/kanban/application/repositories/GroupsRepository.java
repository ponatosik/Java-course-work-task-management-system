package com.ponatosik.kanban.application.repositories;

import com.ponatosik.kanban.core.entities.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends CrudRepository<Group, Integer> {

}
