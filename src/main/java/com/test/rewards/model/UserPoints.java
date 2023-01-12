package com.test.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPoints {

    private String userId;
    private Points points;
    private Points totalPoints;
    private int monthCount;

    public UserPoints(String userId, Points points, Points totalPoints) {
        this.userId = userId;
        this.points = points;
        this.totalPoints = totalPoints;
    }
}
