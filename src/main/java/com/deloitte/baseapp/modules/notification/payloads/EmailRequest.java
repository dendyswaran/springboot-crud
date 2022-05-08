package com.deloitte.baseapp.modules.notification.payloads;

import lombok.Data;

@Data
public class EmailRequest {

    private String subject;
    private String body;
    private String recipient;

}
