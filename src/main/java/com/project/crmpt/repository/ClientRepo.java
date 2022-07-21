package com.project.crmpt.repository;

import com.project.crmpt.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ClientRepo extends CrudRepository<Client, Long> {
    @Query(value = "SELECT * FROM client WHERE user_id = ?1", nativeQuery = true)
    public List<Client> findByUserId(Long user_id);
    @Query(value = "SELECT * FROM client WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Client findByClient(Long id, Long user_id);
    @Query(value = "SELECT * FROM client WHERE name = ?1 OR middle_name = ?1 OR phone = ?1 OR surname = ?1 " +
            "OR email = ?1 OR inn = ?1 OR type_client = ?1 AND user_id = ?2", nativeQuery = true)
    public Iterable<Client> searchByUser(String title, Long user_id);
    @Query(value = "SELECT * FROM client WHERE phone = ?1 AND user_id = ?2", nativeQuery = true)
    public Client findClientByPhone(String phone, Long user_id);
    @Query(value = "SELECT * FROM client WHERE email = ?1 AND user_id = ?2", nativeQuery = true)
    public Client findClientByEmail(String email, Long user_id);
    @Query(value = "SELECT * FROM client WHERE type_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Client> findByTypeClient(Long id, Long user_id);
    @Query(value = "SELECT * FROM client WHERE status_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Client> findByStatusClient(Long id, Long user_id);
    @Query(value = "SELECT * FROM client WHERE company_id = ?1 AND user_id = ?2", nativeQuery = true)
    public List<Client> findByCompany(Long id, Long user_id);
}