package com.email.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	public String sendEmail(final HttpServletRequest request) throws IOException {
		
		// takes input from e-mail form
        final String recipientAddress = request.getParameter("recipient");
        final String subject = request.getParameter("subject");
        final String mailTemplate = "mail.vm";
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
       
        File file = convertFile(multipartFile);
        
        final String filePart = file.getAbsolutePath();
        final String fileName = new String(multipartFile.getOriginalFilename().getBytes("iso-8859-1"),"UTF-8");
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Path : " + files.getAbsolutePath());
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
	        @SuppressWarnings({"unchecked","rawtypes"})
			public void prepare(MimeMessage mimeMessage) throws Exception {
	            
	        	MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	            message.setTo(recipientAddress);
	            message.setSubject(subject);
//	            message.setText("my text <img src='cid:logo'>", true);
//	            message.addInline("logo", new ClassPathResource("images/logo.png"));
	            //FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/") + "/images/logo.png");
	         
	            FileSystemResource file = new FileSystemResource(filePart);
	            message.addAttachment(fileName, file);
	           
	    		//message.addAttachment(file.getFilename(), (DataSource) files);
	            //message.addAttachment(file.getFilename(), file);
	            //message.addAttachment(file1.getFilename(), file1);
	            Map model = new HashMap();
	            model.put("firstName", "Proton");
	            model.put("lastName", "Chalis");
	            model.put("location", "Augmentis");
	            model.put("augmentisURL","www.augmentis.biz");
	            model.put("image", request.getSession().getServletContext().getContextPath() + "/static/images/logo.png");
	            
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
	
	public File convertFile(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
}
