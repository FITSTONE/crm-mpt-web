package com.project.crmpt.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 50)
    private String Title;

    @Column(length = 255)
    private String description;

    private Double Total;

    @Column(length = 10)
    private String Date_creates;

    private Boolean IsClosed;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type_deal type_deal;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status_deal status_deal;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deal")
    private List<Task> task;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public String getDate_creates() {
        return Date_creates;
    }

    public void setDate_creates(String date_creates) {
        Date_creates = date_creates;
    }

    public Type_deal getType_deal() {
        return type_deal;
    }

    public void setType_deal(Type_deal type_deal) {
        this.type_deal = type_deal;
    }

    public Status_deal getStatus_deal() {
        return status_deal;
    }

    public void setStatus_deal(Status_deal status_deal) {
        this.status_deal = status_deal;
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

    public Boolean getClosed() {
        return IsClosed;
    }

    public void setClosed(Boolean closed) {
        IsClosed = closed;
    }

    public List<Task> getTask() {
        return task;
    }

    public Deal(){

    }

}