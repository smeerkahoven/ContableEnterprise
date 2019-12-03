/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xeio
 */
public class BalanceDto implements Serializable {
    
    private List<BalanceGeneralDto> activos = new ArrayList<>() ;
    
    private List<BalanceGeneralDto> pasivos = new ArrayList<>();

    public List<BalanceGeneralDto> getActivos() {
        return activos;
    }

    public List<BalanceGeneralDto> getPasivos() {
        return pasivos;
    }

    public void setActivos(List<BalanceGeneralDto> activos) {
        this.activos = activos;
    }

    public void setPasivos(List<BalanceGeneralDto> pasivos) {
        this.pasivos = pasivos;
    }
    
    
    
    
    
}
