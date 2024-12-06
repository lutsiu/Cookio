package com.example.Cookio.services.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String recipientEmail, String verificationToken) {

        String subject = "Email Verification";

        String verificationLink = "http://localhost:8080/auth/verify?token="  + verificationToken;

        String content = "<p>Dear User,</p>"
                + "<p>Please click the link below to verify your email address:</p>"
                + "<p><a href=\"" + verificationLink + "\">Verify Email</a></p>"
                + "<p>Thank you,<br>The Team</p>";

        sendEmail(recipientEmail, subject, content);
    }

    public void sendEmail(String recipientEmail, String subject, String content) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException exc) {
            throw new RuntimeException("Failed to send email", exc);
        }
    }
}
