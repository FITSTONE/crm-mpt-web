package com.project.crmpt.repository;

import com.project.crmpt.entity.Company;
import com.project.crmpt.entity.Status_client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CompanyRepo extends CrudRepository<Company, Long> {
    @Query(value = "SELECT * FROM company WHERE user_id = ?1", nativeQuery = true)
    public List<Company> findByCompanyAll(Long user_id);
    @Query(value = "SELECT * FROM company WHERE id = ?1 AND user_id = ?2", nativeQuery = true)
    public Company findByCompany(Long id, Long user_id);
    @Query(value = "SELECT * FROM company WHERE title = ?1 AND user_id = ?2", nativeQuery = true)
    public Company findByTitleCompany(String title, Long user_id);
    @Query(value = "SELECT * FROM company WHERE phone = ?1 AND user_id = ?2", nativeQuery = true)
    public Company findByPhoneCompany(String phone, Long user_id);
    @Query(value = "SELECT * FROM company WHERE email = ?1 AND user_id = ?2", nativeQuery = true)
    public Company findByEmailCompany(String email, Long user_id);
    @Query(value = "SELECT CURRENT_DATE();", nativeQuery = true)
    public String getByDate();
}
