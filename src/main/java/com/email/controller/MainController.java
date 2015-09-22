package com.email.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

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
	public String sendEmail(final MultipartHttpServletRequest request) throws IOException {
		
		// takes input from e-mail form
        final String recipientAddress = request.getParameter("recipient");
        final String subject = request.getParameter("subject");
        final String mailTemplate = "mail.vm";
        
        List<MultipartFile> files = request.getFiles("file");
        
        final List<String> filePart = new ArrayList<String>();
        final List<String> fileName = new ArrayList<String>();
        for(MultipartFile file : files){
        	File f = convertFile(file);
        	fileName.add(new String(f.getName().getBytes("iso-8859-1"),"UTF-8"));
        	filePart.add(f.getAbsolutePath());
        }
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
	        @SuppressWarnings({"unchecked","rawtypes"})
			public void prepare(MimeMessage mimeMessage) throws Exception {
	            
	        	MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	            message.setTo(recipientAddress);
	            message.setSubject(subject);
//	            message.setText("my text <img src='cid:logo'>", true);
//	            message.addInline("logo", new ClassPathResource("images/logo.png"));
	            //FileSystemResource file = new FileSystemResource(request.getSession().getServletContext().getRealPath("/") + "/images/logo.png");
	            int i = 0;
	            for(String fPart : filePart){
	            	FileSystemResource file = new FileSystemResource(fPart);
		            message.addAttachment(fileName.get(i), file);
		            i++;
	            }
	            
	            Map model = new HashMap();
	            model.put("firstName", "Proton");
	            model.put("lastName", "Chalis");
	            model.put("location", "Augmentis");
	            model.put("augmentisURL","www.augmentis.biz");
	            model.put("image", request.getSession().getServletContext().getContextPath() + "/static/images/logo.png");
	            
				String text = VelocityEngineUtils.mergeTemplateIntoString(
	            		velocityEngine, mailTemplate, "UTF-8", model);
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
