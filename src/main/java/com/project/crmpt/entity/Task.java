package com.project.crmpt.entity;

import javax.persistence.*;

@Entity
@Table(name = "Task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 100)
    private String Title;

    @Column(length = 20)
    private String Date_creates;

    @Column(length = 20)
    private String Date_end;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status_task status_task;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type_task type_task;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public String getDate_creates() {
        return Date_creates;
    }

    public void setDate_creates(String date_creates) {
        Date_creates = date_creates;
    }

    public String getDate_end() {
        return Date_end;
    }

    public void setDate_end(String date_end) {
        Date_end = date_end;
    }

    public Status_task getStatus_task() {
        return status_task;
    }

    public void setStatus_task(Status_task status_task) {
        this.status_task = status_task;
    }

    public Type_task getType_task() {
        return type_task;
    }

    public void setType_task(Type_task type_task) {
        this.type_task = type_task;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task(){

    }
}