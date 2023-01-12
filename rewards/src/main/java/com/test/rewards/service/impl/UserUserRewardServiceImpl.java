package com.test.rewards.service.impl;

import com.test.rewards.exceptions.BusinessException;
import com.test.rewards.model.Points;
import com.test.rewards.model.UserPayment;
import com.test.rewards.model.UserPoints;
import com.test.rewards.repository.PointRepository;
import com.test.rewards.repository.UserRepository;
import com.test.rewards.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserUserRewardServiceImpl implements UserPointsService {

    @Autowired
    private PointRepository rewardRepository;

    @Autowired
    private PointCalculatorService pointCalculatorService;

    @Override
    public UserPoints calculateReward(String userId) throws BusinessException {
        int points = 0;
        try {
            Collection<Double> amountCollection = rewardRepository.getByUser(userId);
            Double totalAmount = amountCollection.stream().reduce(0.0, (a, b) -> a + b);
            points = pointCalculatorService.calculate(totalAmount);

        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        return new UserPoints(userId, null, new Points(points));
    }

    @Override
    public List<UserPoints> calculateReward(String userId, int months) throws BusinessException {
        List<UserPoints> result = new ArrayList<>();
        try {
            for (int i=1; i<=months; i++) {
                int points = 0;
                int totalPoints = 0;
                LocalDateTime startDate = LocalDateTime.now().minusMonths(i);
                LocalDateTime endDate = LocalDateTime.now().minusMonths(i-1);
                Collection<Double> amountCollection = rewardRepository.getByUserAndRange(userId, startDate, endDate);
                if (CollectionUtils.isEmpty(amountCollection)) {
                    result.add(new UserPoints(userId, new Points(points), new Points(totalPoints), i));
                    continue;
                }
                Double totalAmount = amountCollection.stream().reduce(0.0, (a, b) -> a + b);
                points = pointCalculatorService.calculate(totalAmount);

                Collection<Double> totalAmountCollection = rewardRepository.getByUser(userId);
                totalAmount = totalAmountCollection.stream().reduce(0.0, (a, b) -> a + b);
                totalPoints = pointCalculatorService.calculate(totalAmount);
                result.add(new UserPoints(userId, new Points(points), new Points(totalPoints), i));
            }

        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        return result;
    }

    @Override
    public List<UserPoints> calculateReward(int months) throws BusinessException {
        List<UserPoints> result = new ArrayList<>();
        try {
            Set<String> userList = rewardRepository.getAllUsers();
            for (String userId: userList) {
                result.addAll(calculateReward(userId, months));
            }
        } catch (Exception e) {
            throw new BusinessException(e.toString());
        }
        return result;
    }


    @Override
    public UserPoints calculateReward(String userId, String startDate, String endDate) throws BusinessException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localStartDate = LocalDateTime.parse(startDate, formatter);
        LocalDateTime localEndDate = LocalDateTime.parse(endDate, formatter);
        Collection<Double> amountCollection = rewardRepository.getByUserAndRange(userId, localStartDate, localEndDate);
        if (CollectionUtils.isEmpty(amountCollection)) {
            return new UserPoints(userId, new Points(0), new Points(0));
        }
        Double totalAmount = amountCollection.stream().reduce(0.0, (a, b) -> a + b);
        int points = pointCalculatorService.calculate(totalAmount);

        Collection<Double> totalAmountCollection = rewardRepository.getByUser(userId);
        totalAmount = totalAmountCollection.stream().reduce(0.0, (a, b) -> a + b);
        int totalPoints = pointCalculatorService.calculate(totalAmount);
        return new UserPoints(userId, new Points(points), new Points(totalPoints));
    }

    @Override
    public List<UserPoints> calculateReward(String startDate, String endDate) throws BusinessException {
        Set<String> userList = rewardRepository.getAllUsers();
        List<UserPoints> result = new ArrayList<>();
        for (String userId: userList) {
            UserPoints userPoints = calculateReward(userId, startDate, endDate);
            result.add(userPoints);
        }
        return result;
    }


    @Override
    public UserPayment addUserTransaction(UserPayment userPayment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(userPayment.getDateTime(), formatter);
        rewardRepository.add(userPayment.getUserId(), userPayment.getPayment().getAmount(), localDate);
        return userPayment;
    }
}
