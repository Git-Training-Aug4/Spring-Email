package com.email.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@RequestMapping(value="/create", method={ RequestMethod.GET })
	public String createTemplate() {
		return "create_template";
	}
	
	@RequestMapping(value="/setTemplate", method={RequestMethod.POST})
	public @ResponseBody String setTemplate(final HttpServletRequest request, 
			@RequestParam(value="template") String template, @RequestParam(value="name") String name) throws UnsupportedEncodingException{
		
		String firstName = "Anat";
		String date = "15 June 2015";
		String time = "9.30 AM.";
		String recruitName = "Achiraya Janjiratavorn";
		String recruitPosition = "Recruitment Professional";
		String recruitPhone = "66 8 4751 6665";
		
		final String recipientAddress = "anat.pantera@gmail.com";
        final String subject = "test ckeditor mail template";
        //final String path = request.getSession().getServletContext().getRealPath("/") + "/mail-attachment/";
        
        //create mail
        velocityEngine.init();
        StringWriter writer = new StringWriter();
        
        //define variable in mail template
        Context context = new VelocityContext();
        context.put("firstName", firstName);
        context.put("date", date);
        context.put("time", time);
        context.put("recruitName", recruitName);
        context.put("recruitPosition", recruitPosition);
        context.put("recruitPhone", recruitPhone);
        
        //merge context and writer to String 
        velocityEngine.evaluate(context, writer, "SimpleVelocity", template); 
        
        
        String mailHeader = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head>";
         
        //define finalTemplate
        String finalTemplate = mailHeader + "<body>" + writer.toString() + "</body></html>";
        
        final String encode = new String(finalTemplate.getBytes("iso-8859-1"),"UTF-8");
        //create mime message
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
	            
	        	MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	            message.setTo(recipientAddress);
	            message.setSubject(subject);
	            
//	            FileSystemResource logo = new FileSystemResource(path + "logo.png");
//	            message.addAttachment(logo.getFilename(), logo);
//	            
//	            FileSystemResource map = new FileSystemResource(path + "map.jpg");
//	            message.addAttachment(map.getFilename(), map);
	            
	            message.setText(encode, true);
	        }
        };
        
        //send email
        mailSender.send(preparator);
		
        //console input
		System.out.println("templateName : " + name);
		System.out.println("finalTemplate : " + encode);
		
		return template;
	}
	
	@RequestMapping(value="sendmail/interview", method={ RequestMethod.POST })
	public String sendInterview(final MultipartHttpServletRequest request) throws Exception {
		
		final String recipientAddress = request.getParameter("recipient");
        final String subject = request.getParameter("subject");
        
        List<MultipartFile> files = request.getFiles("file");
        
        final List<String> filePart = new ArrayList<String>();
        final List<String> fileName = new ArrayList<String>();
        for(MultipartFile file : files){
        	if(!file.getOriginalFilename().equals("")){
        		File f = convertFile(file);
            	fileName.add(new String(f.getName().getBytes("iso-8859-1"),"UTF-8"));
            	filePart.add(f.getAbsolutePath());
        	}
        }
        
		StringWriter writer = new StringWriter();
		velocityEngine.init();
		Context context = new VelocityContext();
		//Put something in the context 
		context.put("shape", "circle");  
		//Define our template 
		String template = "The shape is $shape";
		
		velocityEngine.evaluate(context, writer, "SimpleVelocity", template); 
		System.out.println("success: " + writer.toString());
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
	            
	        	MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	            message.setTo(recipientAddress);
	            message.setSubject(subject);

	            int i = 0;
	            for(String fPart : filePart){
	            	FileSystemResource file = new FileSystemResource(fPart);
		            message.addAttachment(fileName.get(i), file);
		            i++;
	            }
	            
	            
	            
	            velocityEngine.init();
	            
	            StringWriter writer = new StringWriter();
	    		
	    		Context context = new VelocityContext();
	    		context.put("shape", "circle");  
	    		
	    		String template = "<h1>The shape is $shape<h1>";
	    		
	    		velocityEngine.evaluate(context, writer, "SimpleVelocity", template); 
	    		//System.out.println("success: " + writer.toString());
	    		
	    		
	            String text =  writer.toString();
	            message.setText(text, true);
	        }
        };
        
        //send email
        mailSender.send(preparator);
         
        // forwards to the view named "Result"
        return "result";
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
