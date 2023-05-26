package com.example.android.timemanagementapplication.controllers;

public class TaskController {
    String taskName;
    String pointsAwarded;
    String id;

    public TaskController(){}

    public TaskController(String taskName, String pointsAwarded, String id){
        setTaskName(taskName);
        setPointsAwarded(pointsAwarded);
        setId(id);
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(String pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
