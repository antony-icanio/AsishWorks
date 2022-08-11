package com.example.AsishWorks.controller;

import com.example.AsishWorks.model.EmailDetails;
import com.example.AsishWorks.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//Links

//https://www.geeksforgeeks.org/spring-boot-sending-email-via-smtp/


@RestController

public class EmailController {
    @Autowired
    EmailServiceImpl EmailServiceImpl;

    //Send simple Mail
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details){return EmailServiceImpl.sendSimpleMail(details);}

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details){return EmailServiceImpl.sendMailWithAttachment(details);}
}
