package com.project.crmpt.repository;

import com.project.crmpt.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE login = ?1 AND password = ?2", nativeQuery = true)
    public User findByUser(String login, String password);
    @Query(value = "SELECT * FROM user WHERE login = ?1", nativeQuery = true)
    User findByUsername(String username);
    @Query(value = "SELECT * FROM user WHERE phone = ?1", nativeQuery = true)
    public User findUserByPhone(String phone);
    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    public User findUserByEmail(String email);
    @Query(value = "SELECT * FROM user WHERE activation_code = ?1", nativeQuery = true)
    public User findByActivationCode(String code);
}