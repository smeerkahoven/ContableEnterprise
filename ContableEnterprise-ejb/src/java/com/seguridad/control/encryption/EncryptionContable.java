/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cheyo
 */
public class EncryptionContable {

    private static MessageDigest md;

    public static String encrypt(String text) {
        StringBuilder textEncrypt = new StringBuilder();
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] password = text.getBytes();
            md.reset(); 
            byte[] digested = md.digest(password);
                        for(int i = 0 ; i<digested.length ; i++){
                textEncrypt.append(Integer.toHexString(0xff & digested[i])) ;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionContable.class.getName()).log(Level.SEVERE, null, ex);
        }

        return textEncrypt.toString() ;
    }
}
