package com.example.android.timemanagementapplication;

public class Task {
    private String taskName;
    private String pointsAwarded;

    public Task(String taskName, String pointsAwarded) {
        setTaskName(taskName);
        setPointsAwarded(pointsAwarded);
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
}
