package com.project.crmpt.repository;

import com.project.crmpt.entity.Type_deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TypeDealRepo extends CrudRepository<Type_deal, Long> {
    @Query(value = "SELECT * FROM type_deal WHERE user_id = ?1", nativeQuery = true)
    public List<Type_deal> findByTypeDealAll(Long user_id);
    @Query(value = "SELECT * FROM type_deal WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Type_deal findByTypeDeal(Long id, Long user_id);
    @Query(value = "SELECT * FROM type_deal WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Type_deal findByTitleType(String title, Long user_id);
}