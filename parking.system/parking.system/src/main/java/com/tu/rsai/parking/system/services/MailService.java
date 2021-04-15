package com.tu.rsai.parking.system.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class MailService {

	public void sendMail(String email, double taxes) {
	    Properties props = new Properties();

	    String sub = "Payment for parking service";
	    String msg = "Your parking bill is " + taxes;
	    final String username = "parking.servicersai";
	    final String pass = "rdyzkgnnmymxhllv";
	    String fromEmail = "parking.servicersai@yahoo.com";

	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.mail.yahoo.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,new javax.mail.Authenticator()
	    {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication()
	        {
	            return new PasswordAuthentication(username, pass);
	        }
	    });

	    try
	    {
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(fromEmail));
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        message.setSubject(sub);
	        message.setText(msg);

	        Transport.send(message);
	    } catch (MessagingException e)
	    {
	        e.printStackTrace();
	    }
	}

}
