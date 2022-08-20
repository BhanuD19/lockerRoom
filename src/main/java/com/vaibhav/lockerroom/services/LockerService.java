package com.vaibhav.lockerroom.services;

import com.vaibhav.lockerroom.models.Locker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LockerService {
    final List<Locker> lockers;

    public LockerService() {
        lockers = new ArrayList<>();
    }

    public List<Locker> getFreeLockers() {
        return lockers.stream().filter(locker -> !locker.getIsOccupied()).collect(Collectors.toList());
    }

    public Boolean isLockerFree(Optional<Long> id, Optional<String> passCode) {
        if (id.isPresent()) {
            List<Locker> lockers1 = lockers.stream().filter(locker -> locker.getId() == id.get()).collect(Collectors.toList());
            for (Locker locker : lockers1) {
                return !locker.getIsOccupied();
            }
        }
        if (passCode.isPresent()) {
            List<Locker> lockers1 = lockers.stream().filter(locker -> locker.getPassword() == passCode.get()).collect(Collectors.toList());
            for (Locker locker : lockers1) {
                return !locker.getIsOccupied();
            }
        }
        return null;
    }

    public Locker getLockerByPassCode(final String passcode) {
        if (passcode != null && passcode.trim() != "") {
            List<Locker> collect = lockers.stream().filter(locker -> locker.getPassword() == passcode).collect(Collectors.toList());
            for (Locker locker : collect) {
                return locker;
            }
        }
        return null;
    }

    public Boolean updateLockerStatus(Boolean isOccupied, final Long id) {
        List<Locker> collect = lockers.stream().filter(locker -> locker.getId() == id).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            collect.get(0).setIsOccupied(isOccupied);
            return true;
        }
        return null;
    }
}
