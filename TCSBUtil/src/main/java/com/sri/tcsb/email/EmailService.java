package com.sri.tcsb.email;

import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sri.tcsb.env.TCSBEnvProp;
import com.sri.tcsb.env.TCSBPassword;
import com.sri.tcsb.env.TCSBReadEnv;
import com.sri.tcsb.logger.TCSBLogger;
import com.sri.tcsb.security.CipherTextInfo;

public class EmailService {
	  TCSBLogger log = TCSBLogger.getLogger(EmailService.class.getName());
	  public boolean sendEmail(Email email) throws Exception
	  {
		  
		  boolean emailStatus = false;
		
           Properties props = new Properties();
           
           props.put("mail.smtp.auth",TCSBReadEnv.getEnvValue(TCSBEnvProp.EmailAuth).trim());  
           props.put("mail.smtp.host", TCSBReadEnv.getEnvValue(TCSBEnvProp.EmailHost).trim());  
           props.put("mail.smtp.port", TCSBReadEnv.getEnvValue(TCSBEnvProp.EmailPort).trim() );
           props.put("mail.smtp.starttls.enable", TCSBReadEnv.getEnvValue(TCSBEnvProp.EmailStarttls).trim() );
           
           //props.put("mail.smtp.socketFactory.port", "465");  
           //props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
           
             
           Session session = Session.getInstance(props,
             new javax.mail.Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                	 String pwd = ""; 
                	 try{
                	 	pwd = CipherTextInfo.decrypt(TCSBPassword.getPassword(TCSBEnvProp.EmailPwd));
                	 	//pwd = POReaderReadEnv.getEnvValue(POReaderEnvProp.EmailPwd);
                	 }catch(Exception er){
                		 log.error("Unable to get Email Password from passwd file :" + er.getMessage());
                	 }
                     return new PasswordAuthentication(TCSBReadEnv.getEnvValue(TCSBEnvProp.EmailUserName), pwd);
                 }
             });
 
           try {

	        	Multipart multipart = new MimeMultipart(); //1

        	   List<EmailAttachment> attachments = email.getAttachments();
        	   
        	   if (attachments!=null) {
	        	   
	        	   for (int i=0;i<attachments.size();i++){
	        		    EmailAttachment attach = attachments.get(i);
	        		    BodyPart attachmentBodyPart = new MimeBodyPart();
			        	attachmentBodyPart.setDataHandler(attach.getDataHandler()); //2
			        	attachmentBodyPart.setFileName(attach.getFileName()); // 2
			        	multipart.addBodyPart(attachmentBodyPart,i);
	        	   }
	           }
        	
        	// Create the HTML Part
        	BodyPart htmlBodyPart = new MimeBodyPart(); //4
        	htmlBodyPart.setContent(email.getMsg_body() , "text/html"); //5
        	multipart.addBodyPart(htmlBodyPart); // 6
        	// Set the Multipart's to be the email's content
        	
        
             //Message message = new MimeMessage(session);
             MimeMessage message = new MimeMessage(session);
             message.setFrom(new InternetAddress(email.getFrom()));
             message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
             if (email.getCc() != null){
            	 message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(email.getCc()));
             }
             if (email.getBcc()!= null){
            	 message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(email.getBcc()));
             }
             message.setSubject(email.getSubject());
             //message.setText(email.getMsg_body());
             //message.setFileName(x);   // Attachments
             //message.setContent(email.getMsg_body(), "text/html; charset=utf-8");
             
             message.setContent(multipart); //7
             Transport.send(message);
             emailStatus = true;
 
           } catch (MessagingException e) {
        	   log.error("Unable to send email :"+ e.getMessage());
               throw new RuntimeException(e);
           }
           return emailStatus;
	} // End of method
	  
} // End of class
