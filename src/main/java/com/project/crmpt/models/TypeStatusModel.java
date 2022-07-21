package com.project.crmpt.models;

public class TypeStatusModel {
    private Long ID;
    private String Title;

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

    public TypeStatusModel(){

    }

    public TypeStatusModel(Long id, String title){
        ID = id;
        Title = title;
    }
}