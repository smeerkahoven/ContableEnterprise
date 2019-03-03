/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.ufv;

import javax.ejb.Local;

/**
 *
 * @author xeio
 */
@Local
public interface UFVSchedulerLocal {
    
    public void checkUFVFactor();
}
