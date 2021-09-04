package com.hiring.decathlonv1.dto;

import lombok.Data;

@Data
public class AlertSentResponse {
    private boolean isAlertSent = false;
    private Developer alertSentTo;
}
