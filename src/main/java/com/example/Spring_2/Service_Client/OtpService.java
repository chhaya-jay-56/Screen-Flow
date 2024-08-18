package com.example.Spring_2.Service_Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender emailSender;

    private final String fromEmail = "screenflow5678@gmail.com"; // email address

    // Generate a 6-digit OTP
    public String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
    }

    // Send OTP via email
    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome To Screen Flow");
        message.setText("Thank you for signing up with Screen Flow! To complete your registration, please verify your email address by entering the One-Time Password (OTP) provided below.\n\n" +
        "Your OTP for Screen Flow: " + otp + "\n\n" + "This OTP is valid for the next 30 minutes. Please enter it on the registration page to verify your email address and activate your account.\n\n" + "If you did not request this OTP, please ignore this email. For your security, do not share your OTP with anyone.");
        emailSender.send(message);
    }

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(to);
        msg.setTo(fromEmail);
        msg.setSubject(subject);
        msg.setText(message);

        emailSender.send(msg);
    }
}
