package com.example.magasin_cms.Model;

public class TaskModel {
    private String item_id;
    private String title;
    private String description;
    private String date;
    private String time;
    private String receiver;
    private String status;
    private String Assigned_by;
    private String csID;

    public TaskModel(){

    }

    public TaskModel(String title, String description, String date, String time, String receiver,String item_id,String status,String Assigned_by,String csID) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.receiver = receiver;
        this.item_id=item_id;
        this.status=status;
        this.Assigned_by=Assigned_by;
        this.csID=csID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getAssigned_by() {
        return Assigned_by;
    }

    public void setAssigned_by(String Assigned_by) {
        this.Assigned_by = Assigned_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCsID() {
        return csID;
    }

    public void setCsID(String csID) {
        this.csID = csID;
    }
}
