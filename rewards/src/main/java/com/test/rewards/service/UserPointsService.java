package com.test.rewards.service;

import com.test.rewards.exceptions.BusinessException;
import com.test.rewards.model.UserPayment;
import com.test.rewards.model.UserPoints;

import java.util.List;

public interface UserPointsService {

    public UserPoints calculateReward(String userId) throws BusinessException;

    public List<UserPoints> calculateReward(String userId, int months) throws BusinessException;

    public List<UserPoints> calculateReward(int months) throws BusinessException;

    public UserPoints calculateReward(String userId, String startDate, String endDate) throws BusinessException;

    public List<UserPoints> calculateReward(String startDate, String endDate) throws BusinessException;

    public UserPayment addUserTransaction(UserPayment userPayment);
}
