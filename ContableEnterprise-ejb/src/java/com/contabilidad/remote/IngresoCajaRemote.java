/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.agencia.entities.Boleto;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface IngresoCajaRemote extends DaoRemoteFacade {

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void update(Entidad e) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public List getNative(String nativeQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public void remove(String nativeQuery, HashMap<String, Object> parameters) throws CRUDException;

    public IngresoCaja createIngresoCaja(final LinkedList<Boleto> boleto, final NotaDebito nota,
            final LinkedList<NotaDebitoTransaccion> transacciones) throws CRUDException;

    public IngresoCaja createIngresoCaja(final Boleto boleto, final NotaDebito nota) throws CRUDException;

    public IngresoTransaccion createIngresoCajaTransaccion(final Boleto boleto, final NotaDebito nota,
            final NotaDebitoTransaccion transacciones, IngresoCaja ingreso) throws CRUDException;

    public void anularTransaccion(Boleto boleto);
}
