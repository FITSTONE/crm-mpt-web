package com.project.crmpt.repository;

import com.project.crmpt.entity.Status_task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusTaskRepo extends CrudRepository<Status_task, Long> {
    @Query(value = "SELECT * FROM status_task WHERE user_id = ?1", nativeQuery = true)
    public List<Status_task> findStatusTasksAll(Long user_id);
    @Query(value = "SELECT * FROM status_task WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Status_task findStatusTask(Long id, Long user_id);
    @Query(value = "SELECT * FROM status_task WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Status_task findStatusTaskByTitleStatus(String title, Long user_id);

}