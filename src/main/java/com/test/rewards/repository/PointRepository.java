package com.test.rewards.repository;

import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PointRepository {

    private Map<String, TreeMap<Long, Double>> storage = new HashMap<>();

    public int add(String userId, Double amount, LocalDateTime dateOfTransaction) {
        TreeMap<Long, Double> existing = null;
        if (storage.containsKey(userId)) {
            existing = storage.get(userId);
        }
        if (existing == null) {
            existing = new TreeMap<>();
        }
        existing.put(Timestamp.valueOf(dateOfTransaction).getTime(), amount);
        storage.put(userId, existing);
        return existing.size();
    }

    public Collection<Double> getByUserAndRange(String userId, LocalDateTime startTime, LocalDateTime endTime) {
        if (storage.containsKey(userId)) {
            TreeMap<Long, Double> existing = storage.get(userId);
            Long inputStartTime = Timestamp.valueOf(startTime).getTime();
            Long startTimeKey = existing.higherKey(inputStartTime);
            Long inputEndTime = Timestamp.valueOf(endTime).getTime();
            Long endTimeKey = existing.lowerKey(inputEndTime);
            if (startTimeKey == null || endTimeKey == null) {
                return null;
            }
            if (startTimeKey > endTimeKey) {
                return null;
            }
            SortedMap<Long, Double> sortedMap = existing.subMap(startTimeKey,true, endTimeKey, true);
            return sortedMap.values();
        }
        return null;
    }

    public Collection<Double> getByUser(String userId) {
        if (storage.containsKey(userId)) {
            return storage.get(userId).values();
        }
        return null;
    }

    public Set<String> getAllUsers() {
        return storage.keySet();
    }
}
