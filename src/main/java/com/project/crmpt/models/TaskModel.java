package com.project.crmpt.models;

public class TaskModel {
    private Long ID;
    private String Title;
    private String Date_creates;
    private String Date_end;
    private String Type;
    private String Status;
    private String Deal;
    private Long Deal_id;
    private String Client;
    private Long Client_id;

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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getDeal() {
        return Deal;
    }

    public void setDeal(String deal) {
        this.Deal = deal;
    }

    public Long getDeal_id() {
        return Deal_id;
    }

    public void setDeal_id(Long deal_id) {
        this.Deal_id = deal_id;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        Client = client;
    }

    public Long getClient_id() {
        return Client_id;
    }

    public void setClient_id(Long client_id) {
        Client_id = client_id;
    }

    public TaskModel(){

    }

    public TaskModel(Long id, String title, String date_creates, String date_end, String type, String status,
                     String deal, Long deal_id, String client, Long client_id){
        ID = id;
        Title = title;
        Date_creates = date_creates;
        Date_end = date_end;
        Type = type;
        Status = status;
        Deal = deal;
        Deal_id = deal_id;
        Client = client;
        Client_id = client_id;
    }

    public TaskModel(String title, String date_creates, String date_end, String type, String status,
                     String deal, Long deal_id, String client, Long client_id){
        Title = title;
        Date_creates = date_creates;
        Date_end = date_end;
        Type = type;
        Status = status;
        Deal = deal;
        Deal_id = deal_id;
        Client = client;
        Client_id = client_id;
    }

    public TaskModel(String title, String date_end, String type, String status,
                     Long deal_id, Long client_id){
        Title = title;
        Date_end = date_end;
        Type = type;
        Status = status;
        Deal_id = deal_id;
        Client_id = client_id;
    }

    public TaskModel(Long id, String title, String date_end, String type, String status,
                     Long deal_id, Long client_id){
        ID = id;
        Title = title;
        Date_end = date_end;
        Type = type;
        Status = status;
        Deal_id = deal_id;
        Client_id = client_id;
    }
}