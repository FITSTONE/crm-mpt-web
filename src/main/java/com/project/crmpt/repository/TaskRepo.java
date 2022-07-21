package com.project.crmpt.repository;

import com.project.crmpt.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepo extends CrudRepository<Task, Long> {
    @Query(value = "SELECT * FROM task WHERE user_id = ?1", nativeQuery = true)
    public List<Task> findStatusTasksAll(Long user_id);
    @Query(value = "SELECT * FROM task WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Task findByTask(Long id, Long user_id);
    @Query(value = "SELECT * FROM task WHERE deal_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Task> findTaskByDealId(Long id, Long user_id);
    @Query(value = "SELECT * FROM task WHERE status = ?1 OR deal_id = ?2 AND user_id = ?3", nativeQuery = true)
    public List<Task> searchByTask(String status, Long deal_id, Long user_id);
    @Query(value = "SELECT * FROM task WHERE status_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Task> findByStatusTask(Long id, Long user_id);
    @Query(value = "SELECT * FROM task WHERE type_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Task> findByTypeTask(Long id, Long user_id);
    @Query(value = "SELECT * FROM task WHERE deal_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Task> findByDealId(Long id, Long user_id);
    @Query(value = "SELECT * FROM task WHERE client_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Task> findByClientId(Long id, Long user_id);
    @Query(value = "SELECT DATEDIFF(?1, (SELECT CURRENT_DATE()))", nativeQuery = true)
    public Long getDifferenceDate(String date_end);
    @Query(value = "SELECT CURRENT_DATE();", nativeQuery = true)
    public String getByDate();
}