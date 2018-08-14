/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.ejb;

import com.seguridad.control.remote.SendMailRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author xeio
 */
@Stateless
public class SendMailEJB implements SendMailRemote {

    @Resource(name = "mail/web.setricon@gmail.com")
    private Session mailSession;

    @Override
    public void sendMail(String from, String to, String subject, String content)
            throws MessagingException {

        Message msg = new MimeMessage(mailSession);
        msg.setSubject(subject);
        msg.setRecipient(Message.RecipientType.TO, 
                new InternetAddress(to));
        msg.setContent(content, "text/html");

        
        Transport.send(msg);
    }

}
