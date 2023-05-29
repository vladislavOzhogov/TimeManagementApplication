package com.example.android.timemanagementapplication;

public class Reward {
    private String rewardName;
    private String spentPoints;

    public Reward(String rewardName, String spentPoints) {
        setRewardName(rewardName);
        setSpentPoints(spentPoints);
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getSpentPoints() {
        return spentPoints;
    }

    public void setSpentPoints(String spentPoints) {
        this.spentPoints = spentPoints;
    }
}
