package com.bhargav.themeleafbhargav.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.pinpointemail.PinpointEmailClient;
import software.amazon.awssdk.services.pinpointemail.model.*;

@Service
public class EmailService {

    private final String pinpointAppId = "62ebebcc24034286859e6e35b3916a38";

    private final PinpointEmailClient pinpointEmailClient;

    public EmailService() {
        this.pinpointEmailClient = PinpointEmailClient.builder()
                .region(Region.AP_SOUTH_1)
                .build();
    }

    public void sendEmail(String subject, String toAddress, String body) {
        try {
            Content content = Content.builder()
                    .data(body)
                    .build();

            Body messageBody = Body.builder()
                    .text(content)
                    .build();

            Message message = Message.builder()
                    .body(messageBody)
                    .subject(Content.builder().data(subject).build())
                    .build();

            Destination destination = Destination.builder()
                    .toAddresses(toAddress)
                    .build();

            EmailContent emailContent = EmailContent.builder()
                    .simple(message)
                    .build();

            String senderAddress = "info@securetag.in";
            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .fromEmailAddress(senderAddress)
                    .destination(destination)
                    .content(emailContent)
                    .build();

            pinpointEmailClient.sendEmail(sendEmailRequest);
            System.out.println("Email sent successfully!");

        } catch (PinpointEmailException e) {
            System.err.println("Failed to send email: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}

