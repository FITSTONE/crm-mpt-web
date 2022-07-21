package com.project.crmpt.models;

public class DealModel {
    private Long ID;
    private String Title;
    private String Description;
    private Double Total;
    private String Date_creates;
    private String IsClosed;
    private String Type_deal;
    private String Status_deal;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
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

    public String getIsClosed() {
        return IsClosed;
    }

    public void setIsClosed(String isClosed) {
        IsClosed = isClosed;
    }

    public String getType_deal() {
        return Type_deal;
    }

    public void setType_deal(String type_deal) {
        this.Type_deal = type_deal;
    }

    public String getStatus_deal() {
        return Status_deal;
    }

    public void setStatus_deal(String status_deal) {
        this.Status_deal = status_deal;
    }

    public String getClient() {
        return Client;
    }

    public void setClient(String client) {
        this.Client = client;
    }

    public Long getClient_id() {
        return Client_id;
    }

    public void setClient_id(Long client_id) {
        this.Client_id = client_id;
    }

    public DealModel(){

    }

    public DealModel(Long id, String title, String description, Double total, String date_creates, String closed, String type_deal,
                     String status_deal, String client, Long client_id){
        ID = id;
        Title = title;
        Description = description;
        Total = total;
        Date_creates = date_creates;
        IsClosed = closed;
        Type_deal = type_deal;
        Status_deal = status_deal;
        Client = client;
        Client_id = client_id;

    }

    public DealModel(String title, String description, Double total, String type_deal,
                     String status_deal, Long client_id){
        Title = title;
        Description = description;
        Total = total;
        Type_deal = type_deal;
        Status_deal = status_deal;
        Client_id = client_id;
    }

    public DealModel(Long id, String title, String description, Double total, String type_deal,
                     String status_deal, Long client_id){
        ID = id;
        Title = title;
        Description = description;
        Total = total;
        Type_deal = type_deal;
        Status_deal = status_deal;
        Client_id = client_id;

    }
}