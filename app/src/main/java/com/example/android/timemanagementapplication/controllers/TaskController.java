package com.example.android.timemanagementapplication.controllers;

import com.example.android.timemanagementapplication.Task;
import com.google.firebase.auth.FirebaseUser;

public class TaskController {
    String userEmail;
    Task task;
    String date;

    public TaskController(){}

    public TaskController(String userEmail, Task task, String date){
        setUserEmail(userEmail);
        setTask(task);
        setDate(date);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
