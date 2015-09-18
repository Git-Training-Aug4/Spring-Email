package com.email.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@RequestMapping(value="/", method={ RequestMethod.GET })
	public String emailPage() {
		return "index";
	}
	
	@RequestMapping(value="/sendEmail", method={ RequestMethod.POST })
	public String sendEmail(HttpServletRequest request) {
		
		// takes input from e-mail form
        final String recipientAddress = request.getParameter("recipient");
        final String subject = request.getParameter("subject");
        final String messages = request.getParameter("message");
        
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				 MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				 message.setTo(recipientAddress);
				 message.setSubject(subject);
				 
				 Map model = new HashMap();
                 model.put("address",recipientAddress);
                 model.put("message", messages);
                 model.put("subject", subject);
                 
                 String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "velocity/new_contact_message.vm", "UTF-8", model);
                 message.setText(text, true);
			}
	       };
	       
	    // sends the e-mail  
	    mailSender.send(preparator);
        
        // prints debug info
        System.out.println("To: " + recipientAddress);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + messages);

        // forwards to the view named "Result"
        return "result";
	}
}
