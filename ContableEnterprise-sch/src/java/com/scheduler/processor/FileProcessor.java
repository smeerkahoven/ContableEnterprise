/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scheduler.processor;

import com.configuracion.entities.ArchivoBoleto;
import com.configuracion.remote.AmadeusFileRemote;
import com.configuracion.remote.SabreFileRemote;
import com.seguridad.control.exception.CRUDException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author xeio
 */
@Stateless
@LocalBean
public class FileProcessor {

    @EJB
    private SabreFileRemote ejbSabre ;
    @EJB
    private AmadeusFileRemote ejbAmadeus ;
    //EN PRODUCCION DEBE FUNCIONAR CADA 10 MINUTOS
    //@Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "9-17", dayOfMonth = "*", year = "*", minute = "*/10", second = "0")
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*/1")
    public void processArchivosBoletos() {
        
        try {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("estado", ArchivoBoleto.Estado.CREADO);
            
            List<ArchivoBoleto> l = ejbAmadeus.get("ArchivoBoleto.findByEstadoCreado", ArchivoBoleto.class, parameters);
            
            for(ArchivoBoleto b : l){
                
                if (b.getTipoArchivo().equals(ArchivoBoleto.TipoArchivo.AMADEUS)){
                    if (ejbAmadeus.procesarArchivo(b)){
                        b.setEstado(ArchivoBoleto.Estado.PROCESADO);
                    }else {
                        b.setEstado(ArchivoBoleto.Estado.ERROR);
                    }
                }else if (b.getTipoArchivo().equals(ArchivoBoleto.TipoArchivo.SABRE)){
                    if (ejbSabre.procesarArchivo(b)){
                        b.setEstado(ArchivoBoleto.Estado.PROCESADO);
                    }else {
                        b.setEstado(ArchivoBoleto.Estado.ERROR);
                    }
                }else {
                    //archivo desconocido
                }
                ejbAmadeus.update(b);
            }
            
            System.out.println("Timer event: " + new Date());
        } catch (CRUDException ex) {
            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
