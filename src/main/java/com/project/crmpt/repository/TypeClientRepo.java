package com.project.crmpt.repository;

import com.project.crmpt.entity.Type_client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeClientRepo extends CrudRepository<Type_client, Long> {
    @Query(value = "SELECT * FROM type_client WHERE user_id = ?1", nativeQuery = true)
    public List<Type_client> findByTypeClientAll(Long user_id);
    @Query(value = "SELECT * FROM type_client WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Type_client findByTypeClient(Long id, Long user_id);
    @Query(value = "SELECT * FROM type_client WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Type_client findByTitleType(String title, Long user_id);
}