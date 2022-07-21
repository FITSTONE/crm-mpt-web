package com.project.crmpt.repository;

import com.project.crmpt.entity.Status_client;
import com.project.crmpt.entity.Status_deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusClientRepo extends CrudRepository<Status_client, Long> {
    @Query(value = "SELECT * FROM status_client WHERE user_id = ?1", nativeQuery = true)
    public List<Status_client> findByStatusClientAll(Long user_id);
    @Query(value = "SELECT * FROM status_client WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Status_client findByStatusClient(Long id, Long user_id);
    @Query(value = "SELECT * FROM status_client WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Status_client findByTitleStatus(String title, Long user_id);
}