package com.project.crmpt.repository;

import com.project.crmpt.entity.Status_deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusDealRepo extends CrudRepository<Status_deal, Long> {
    @Query(value = "SELECT * FROM status_deal WHERE user_id = ?1", nativeQuery = true)
    public List<Status_deal> findStatusDealsAll(Long user_id);
    @Query(value = "SELECT * FROM status_deal WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Status_deal findStatusDeal(Long id, Long user_id);
    @Query(value = "SELECT * FROM status_deal WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Status_deal findByTitleStatus(String title, Long user_id);
}