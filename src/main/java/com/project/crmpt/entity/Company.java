package com.project.crmpt.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 70)
    private String Title;

    @Column(length = 40)
    private String City;

    @Column(length = 20)
    private String Phone;

    @Column(length = 50)
    private String Email;

    @Column(length = 10)
    private String Date_creates;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Client> clients;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
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

    public String getDate_creates() {
        return Date_creates;
    }

    public void setDate_creates(String date_creates) {
        Date_creates = date_creates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Company(){

    }

    public Company(String title, String city, String phone, String email){
        Title = title;
        City = city;
        Phone = phone;
        Email = email;
    }

    public Company(Long id, String title, String city, String phone, String email){
        ID = id;
        Title = title;
        City = city;
        Phone = phone;
        Email = email;
    }
}