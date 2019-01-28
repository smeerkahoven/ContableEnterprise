/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.response.json.boletaje;

import com.contabilidad.entities.MayoresAcumulados;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author xeio
 */
public class GridMayores implements Serializable {
    
    private List<MayoresJson> mayores;
    private MayoresAcumulados acumulados ;
    private MayoresAcumulados totales ;
    private MayoresAcumulados totalesAcumulado ;

    public MayoresAcumulados getTotalesAcumulado() {
        return totalesAcumulado;
    }

    public void setTotalesAcumulado(MayoresAcumulados totalesAcumulado) {
        this.totalesAcumulado = totalesAcumulado;
    }

    
    public MayoresAcumulados getTotales() {
        return totales;
    }

    public void setTotales(MayoresAcumulados totales) {
        this.totales = totales;
    }
    
    

    public List<MayoresJson> getMayores() {
        return mayores;
    }

    public void setMayores(List<MayoresJson> mayores) {
        this.mayores = mayores;
    }

    public MayoresAcumulados getAcumulados() {
        return acumulados;
    }

    public void setAcumulados(MayoresAcumulados acumulados) {
        this.acumulados = acumulados;
    }
    
    
    
}
