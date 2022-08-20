package com.vaibhav.lockerroom.models;

import lombok.Data;

@Data
public class Locker {
    Long id;
    String lockerCode;
    String password;
    Boolean isOccupied;
}
