package com.project.crmpt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Type_task")
public class Type_task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(length = 50)
    private String Title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type_task")
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Task> getTask() {
        return task;
    }

    public Type_task(){

    }

    public Type_task(String title){
        Title = title;
    }

    public Type_task(Long id, String title){
        ID = id;
        Title = title;
    }
}