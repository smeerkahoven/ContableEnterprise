/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.remote;

import javax.ejb.Remote;
import javax.mail.MessagingException;

/**
 *
 * @author xeio
 */

@Remote
public interface SendMailRemote {
    
    public void sendMail(String from, String to, String subject, String content) throws MessagingException;
    
}
