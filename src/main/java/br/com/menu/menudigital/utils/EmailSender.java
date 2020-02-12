package br.com.menu.menudigital.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	@Autowired
    private JavaMailSender javaMailSender;
	
	public void sendEmail(String to, String subject, String htmlBody) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setTo(to);

		helper.setSubject(subject);
		helper.setText(htmlBody, true);

		javaMailSender.send(mimeMessage);

    }
	
	public void sendEmail(String[] to, String subject, String htmlBody) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		helper.setTo(to);

		helper.setSubject(subject);
		helper.setText(htmlBody, true);

		javaMailSender.send(mimeMessage);

    }
}
