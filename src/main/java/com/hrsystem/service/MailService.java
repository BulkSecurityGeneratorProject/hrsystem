package com.hrsystem.service;

import com.hrsystem.domain.Department;
import com.hrsystem.domain.Request;
import com.hrsystem.domain.Ticket;
import com.hrsystem.domain.User;

import io.github.jhipster.config.JHipsterProperties;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String TICKET = "ticket";

    private static final String BASE_URL = "baseUrl";

    private String current_IP ;

    private static final String PORT ="8080";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final String HR_EMAIL = "hr-cairo@qcentris.com";

    private final String IT_EMAIL = "intranet.qcentris@qcentris.com";

    private String [] RequestTypes ={"HR" , "IT"};

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.current_IP ="http://"+ getCurrentIP()+ ":" + PORT;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey ) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        //context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable(BASE_URL, current_IP);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        log.debug("jhipstergetmail : " + getCurrentIP());
        sendEmail(user.getEmail(), subject, content, false, true);

    }

    @Async
    public void sendEmailFromTemplateToDepartmentRequest(User user, String departmentEmail, Ticket ticket, String templateName, String titleKey ) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(TICKET , ticket);
        context.setVariable(BASE_URL, current_IP);
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);

        log.debug("User email "+user.getEmail());
        log.debug("department email "+ departmentEmail);
        sendEmail(departmentEmail, subject, content, false, true);

    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendRequestMailFromDepartment(User user , Ticket ticket , String requestType) {
        //HR
        if(requestType.equals(RequestTypes[0])){
            log.debug("Sending Request reset email to '{}'", HR_EMAIL);
            sendEmailFromTemplateToDepartmentRequest(user,HR_EMAIL, ticket ,  "requestEmail", "email.request.title");
            sendEmailFromTemplateToDepartmentRequest(user,user.getEmail(), ticket ,  "requestEmail", "email.request.title");

        }
        //IT
        else if(requestType.equals(RequestTypes[1]) ){
            log.debug("Sending Request reset email to '{}'", IT_EMAIL);
            sendEmailFromTemplateToDepartmentRequest(user,IT_EMAIL,ticket ,"requestEmail", "email.request.title");
            sendEmailFromTemplateToDepartmentRequest(user,user.getEmail(),ticket ,"requestEmail", "email.request.title");
        }
    }

    public String getCurrentIP(){
        String ip ="";
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
