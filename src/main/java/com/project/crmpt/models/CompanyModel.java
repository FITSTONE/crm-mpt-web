package com.project.crmpt.models;

public class CompanyModel {
    private Long ID;
    private String Title;
    private String City;
    private String Phone;
    private String Email;
    private String Date_creates;

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

    public CompanyModel(){

    }

    public CompanyModel(Long id, String title, String city, String phone, String email, String date_creates){
        ID = id;
        Title = title;
        City = city;
        Phone = phone;
        Email = email;
        Date_creates = date_creates;
    }

    public CompanyModel(String title, String city, String phone, String email){
        Title = title;
        City = city;
        Phone = phone;
        Email = email;
    }

    public CompanyModel(Long id, String title, String city, String phone, String email){
        ID = id;
        Title = title;
        City = city;
        Phone = phone;
        Email = email;
    }
}
