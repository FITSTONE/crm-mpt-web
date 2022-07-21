package com.project.crmpt.models;

public class ClientModel {
    private Long ID;
    private String Name;
    private String Surname;
    private String Middle_name;
    private String Phone;
    private String Email;
    private String Type_client;
    private String Status_client;
    private String Company;

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

    public String getType_client() {
        return Type_client;
    }

    public void setType_client(String type_client) {
        this.Type_client = type_client;
    }

    public String getStatus_client() {
        return Status_client;
    }

    public void setStatus_client(String status_client) {
        this.Status_client = status_client;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public ClientModel(){

    }

    public ClientModel(Long id, String name, String surname, String middle_name, String phone, String email,
                       String type_client, String status_client, String company){
        ID = id;
        Name = name;
        Surname = surname;
        Middle_name = middle_name;
        Phone = phone;
        Email = email;
        Type_client = type_client;
        Status_client = status_client;
        Company = company;
    }

    public ClientModel(String name, String surname, String middle_name, String phone, String email,
                       String type_client, String status_client, String company){
        Name = name;
        Surname = surname;
        Middle_name = middle_name;
        Phone = phone;
        Email = email;
        Type_client = type_client;
        Status_client = status_client;
        Company = company;
    }
}
