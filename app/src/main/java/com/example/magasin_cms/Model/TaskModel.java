package com.example.magasin_cms.Model;

public class TaskModel {
    private String title;
    private String description;
    private String date;
    private String time;
    private String receiver;

    public TaskModel(){

    }

    public TaskModel(String title, String description, String date, String time, String receiver) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.receiver = receiver;
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
}
