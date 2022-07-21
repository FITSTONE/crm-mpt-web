package com.project.crmpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Status_deal")
public class Status_deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 50)
    private String Title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status_deal")
    private List<Deal> deals;

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

    public Status_deal(){

    }

    public Status_deal(String title){
        Title = title;
    }

    public Status_deal(Long id, String title){
        ID = id;
        Title = title;
    }

}