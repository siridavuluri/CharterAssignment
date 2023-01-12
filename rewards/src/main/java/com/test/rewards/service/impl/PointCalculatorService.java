package com.test.rewards.service.impl;

import org.springframework.stereotype.Service;

@Service
public class PointCalculatorService {

    public int calculate(Double amount) {
        int points = 0;
        if (amount >= 50) {
            if (amount < 100) {
                points += (amount - 50) * 1;
            } else {
                points += 50;
            }
        }
        if (amount > 100) {
            points += (amount - 100) * 2;
        }
        return points;
    }
}
