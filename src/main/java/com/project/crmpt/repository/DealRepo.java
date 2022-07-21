package com.project.crmpt.repository;

import com.project.crmpt.entity.Client;
import com.project.crmpt.entity.Deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DealRepo extends CrudRepository<Deal, Long> {
    @Query(value = "SELECT * FROM deal WHERE user_id = ?1", nativeQuery = true)
    public List<Deal> findByDealAll(Long user_id);
    @Query(value = "SELECT CURRENT_DATE();", nativeQuery = true)
    public String getByDate();
    @Query(value = "SELECT * FROM deal WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Deal findByDeal(Long id, Long user_id);
    @Query(value = "SELECT * FROM deal WHERE client_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Deal> findDealByClientId(Long id, Long user_id);
    @Query(value = "SELECT * FROM deal WHERE type_deal = ?1 OR status = ?2 OR client_id = ?3 AND user_id = ?4", nativeQuery = true)
    public List<Deal> searchByDeal(String type, String status, Long id, Long user_id);
    @Query(value = "SELECT * FROM deal WHERE type_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Deal> findByTypeDeal(Long id, Long user_id);
    @Query(value = "SELECT * FROM deal WHERE status_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Deal> findByStatusDeal(Long id, Long user_id);
    @Query(value = "SELECT * FROM deal WHERE title = ?1 AND total = ?2 AND type_id = ?3 AND status_id = ?4 AND user_id = ?5", nativeQuery = true)
    public Deal addClientInTask(String title, Double total, Long type, Long status, Long user_id);
}
