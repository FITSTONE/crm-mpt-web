package com.project.crmpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 30)
    private String Name;

    @Column(length = 40)
    private String Surname;

    @Column(nullable = true, length = 30)
    private String Middle_name;

    @Column(length = 20)
    private String Phone;

    @Column(nullable = true, length = 50)
    private String Email;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type_client type;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status_client status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Deal> deal;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private List<Task> task;

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

    public Type_client getType() {
        return type;
    }

    public void setType(Type_client type) {
        this.type = type;
    }

    public Status_client getStatus() {
        return status;
    }

    public void setStatus(Status_client status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Deal> getDeal() {
        return deal;
    }

    public List<Task> getTask() {
        return task;
    }

    public Client(){

    }
}