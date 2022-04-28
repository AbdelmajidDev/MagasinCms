package com.example.magasin_cms.Model_Task;

public class TaskManagementModel {

    private String taskId;
    private String taskTitle;
    private String taskDescription;
    private String date;
    private String time;
    private String Receiver;

    public TaskManagementModel(){

    }

    public TaskManagementModel(String taskId, String taskTitle, String taskDescription, String date, String time, String receiver) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.date = date;
        this.time = time;
        Receiver = receiver;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
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
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }
}
