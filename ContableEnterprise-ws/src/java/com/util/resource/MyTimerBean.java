/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util.resource;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author xeio
 */
@Stateless
@LocalBean
public class MyTimerBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //@Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void scheduler() {
        // your code here
        System.out.println("Hola que tal");
    }
}
