package com.email.controller;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
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
	public String sendEmail(final HttpServletRequest request) {
		// takes input from e-mail form
        final String recipientAddress = request.getParameter("recipient");
        final String subject = request.getParameter("subject");
        final String mailTemplate = "mail.vm";
        //String message = request.getParameter("message");
        
        // prints debug info
        //System.out.println("To: " + recipientAddress);
        //System.out.println("Subject: " + subject);
        //System.out.println("Message: " + message);
        
        // creates a simple e-mail object
//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(mail.getMailTo());
//        email.setSubject(mail.getMailSubject());
        //email.setText(message);
         
        //config velocity template
//        Template template = velocityEngine.getTemplate("./mail-template/" + mail.getTemplateName());

//        VelocityContext velocityContext = new VelocityContext();
//        velocityContext.put("firstName", "Proton");
//        velocityContext.put("lastName", "Chalis");
//        velocityContext.put("location", "Augmentis");
        
//        StringWriter stringWriter = new StringWriter();
//        template.merge(velocityContext, stringWriter);
//        email.setText(stringWriter.toString());
        
//        System.out.println("Message: " + stringWriter.toString());
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
	        @SuppressWarnings({"unchecked","rawtypes"})
			public void prepare(MimeMessage mimeMessage) throws Exception {
	            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	            message.setTo(recipientAddress);
	            message.setSubject(subject);
				
	            Map model = new HashMap();
	            model.put("firstName", "Proton");
	            model.put("lastName", "Chalis");
	            model.put("location", "Augmentis");
	            model.put("image", request.getSession().getServletContext().getRealPath("/") + "images/image001.png");
	            
				String text = VelocityEngineUtils.mergeTemplateIntoString(
	            		velocityEngine, "./mail-template/" + mailTemplate, "UTF-8", model);
	            message.setText(text, true);
	        }
        };
        
        //send email
        mailSender.send(preparator);
         
        // forwards to the view named "Result"
        return "result";
        
	}
}
