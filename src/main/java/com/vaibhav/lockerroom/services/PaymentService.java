package com.vaibhav.lockerroom.services;

import com.vaibhav.lockerroom.models.Locker;
import com.vaibhav.lockerroom.models.Payment;
import com.vaibhav.lockerroom.models.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private static final float BASE_PRICE = 20.0f;
    private final LockerService lockerService;

    private final List<Payment> payments;

    public PaymentService(LockerService lockerService) {
        this.lockerService = lockerService;
        payments = new ArrayList<>();
    }

    public Boolean bookLocker(final String passCode, User user) {
        Boolean isLockerFree = lockerService.isLockerFree(Optional.empty(), Optional.of(passCode));
        if (isLockerFree != null && isLockerFree) {
            Locker locker = lockerService.getLockerByPassCode(passCode);
            Payment payment = new Payment();
            payment.setUser(user);
            payment.setCreatedAt(LocalDateTime.now());
            payment.setLockerId(locker.getId());
            payments.add(payment);
            lockerService.updateLockerStatus(true, locker.getId());
            return true;
        }
        return null;
    }

    public Float checkOut(final String passCode, final Long userId) throws IllegalAccessException {
        if (passCode != null && passCode.trim() != "") {
            Locker locker = lockerService.getLockerByPassCode(passCode);
            List<Payment> paymentList = payments.stream().filter(payment -> payment.getLockerId() == locker.getId() && payment.getIsActive()).collect(Collectors.toList());
            if (!paymentList.isEmpty()) {
                Payment payment = paymentList.get(0);
                if (payment.getUser().getId() != userId) {
                    throw new IllegalAccessException();
                }
                long duration = payment.getCreatedAt().until(LocalDateTime.now(), ChronoUnit.DAYS);
                paymentList.get(0).setIsActive(false);
                lockerService.updateLockerStatus(false, locker.getId());
                return duration * BASE_PRICE;
            }
        }
        return null;
    }
}
