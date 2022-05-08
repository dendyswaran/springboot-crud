package com.deloitte.baseapp.modules.notification.services;

import com.deloitte.baseapp.commons.LogInfo;
import com.deloitte.baseapp.modules.notification.payloads.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class NotificationEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public CompletableFuture<Boolean> sendEmail(final EmailRequest emailRequest) {
        log.info(LogInfo.print("NotificationEmailService", "sendEmail", "start sending an email.."));

        return CompletableFuture.supplyAsync(() -> {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(emailRequest.getRecipient());
            mail.setSubject(emailRequest.getSubject());
            mail.setText(emailRequest.getBody());
            mail.setFrom("no-reply@deloitte.com");


            javaMailSender.send(mail);
            log.info(LogInfo.print("NotificationEmailService", "sendEmail", "finished sending an email.."));

            return Boolean.TRUE;
        });
    }

}
