package com.example.android.timemanagementapplication.controllers;

import com.example.android.timemanagementapplication.Reward;
import com.example.android.timemanagementapplication.Task;

public class RewardController {
    String userEmail;
    Reward reward;
    String date;

    public RewardController(){}

    public RewardController(String userEmail, Reward reward, String date){
        setUserEmail(userEmail);
        setReward(reward);
        setDate(date);
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
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
