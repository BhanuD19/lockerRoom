package com.vaibhav.lockerroom.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {
    Long id;
    User user;
    Long lockerId;
    LocalDateTime createdAt;
    Float total;
    Boolean isActive;
}
