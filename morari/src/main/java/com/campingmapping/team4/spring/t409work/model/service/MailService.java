package com.campingmapping.team4.spring.t409work.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
 
  @Autowired
  private JavaMailSender javaMailSender;
  
  public void sendEmail(String toMail, String subject, String message) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(toMail);
    email.setSubject(subject);
    email.setText(message);
    javaMailSender.send(email);
    
  }
}

