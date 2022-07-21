package com.project.crmpt.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(nullable = true, length = 30)
    private String Name;

    @Column(nullable = true, length = 40)
    private String Surname;

    @Column(nullable = true, length = 30)
    private String Middle_name;

    @Column(nullable = true, length = 20)
    private String Phone;

    @Column(nullable = true, length = 50)
    private String Email;

    @Column(nullable = true, length = 50)
    private String Login;

    @Column(nullable = true, length = 50)
    private String Password;

    private Boolean IsActive;

    private String activationCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Client> client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Deal> deal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Task> task;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Company> companies;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Type_client> type_clients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Status_client> status_clients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Type_deal> type_deals;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Status_deal> status_deals;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Status_task> status_tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Type_task> type_tasks;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getMiddle_name() {
        return Middle_name;
    }

    public void setMiddle_name(String middle_name) {
        Middle_name = middle_name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return Password;
    }

    @Override
    public String getUsername() {
        return Login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return IsActive;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public User(){

    }

    public User(String name, String surname, String middle, String phone, String email, String pass) {
        Name = name;
        Surname = surname;
        Middle_name = middle;
        Phone = phone;
        Email = email;
        Login = email;
        Password = pass;
        IsActive = false;
    }
}