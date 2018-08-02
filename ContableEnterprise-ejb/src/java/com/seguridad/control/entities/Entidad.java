/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.control.entities;

import com.seguridad.control.exception.CRUDException;
import java.io.Serializable;

/**
 *
 * @author Cheyo
 */
public abstract class Entidad implements Serializable{
    
    public abstract int getId()  throws CRUDException  ;
   
}
