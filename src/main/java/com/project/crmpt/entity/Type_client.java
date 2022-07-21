package com.project.crmpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Type_client")
public class Type_client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 50)
    private String Title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Client> getClients() {
        return clients;
    }

    public Type_client(){

    }

    public Type_client(String title){
        Title = title;
    }

    public Type_client(Long id,String title){
        ID = id;
        Title = title;
    }
}