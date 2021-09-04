package com.hiring.decathlonv1.errors;

import lombok.Data;

@Data
public class CustomError {

    private int httpStatus;
    private String errorMessage;

}
