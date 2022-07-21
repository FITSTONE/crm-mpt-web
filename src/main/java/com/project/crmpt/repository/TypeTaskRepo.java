package com.project.crmpt.repository;

import com.project.crmpt.entity.Type_deal;
import com.project.crmpt.entity.Type_task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeTaskRepo extends CrudRepository<Type_task, Long> {
    @Query(value = "SELECT * FROM type_task WHERE user_id = ?1", nativeQuery = true)
    public List<Type_task> findByTypeTaskAll(Long user_id);
    @Query(value = "SELECT * FROM type_task WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Type_task findByTypeTask(Long id, Long user_id);
    @Query(value = "SELECT * FROM type_task WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Type_task findByTitleType(String title, Long user_id);
}