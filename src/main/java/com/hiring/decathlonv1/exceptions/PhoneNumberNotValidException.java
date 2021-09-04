package com.hiring.decathlonv1.exceptions;

import java.util.List;

public class PhoneNumberNotValidException extends RuntimeException {

    public PhoneNumberNotValidException(List<String> phoneNumber) {
        super("Phone number not valid :"+ String.join(" ", phoneNumber));
    }
}
