/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.remote;

import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.entities.PagoAnticipadoTransaccion;
import com.response.json.boletaje.PagoAnticipadoSearchJson;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface PagoAnticipadoRemote extends DaoRemoteFacade {

    @Override
    public Entidad get(Integer id, Class<?> typeClass) throws CRUDException;

    
    /**
     *
     * @param idUsuario
     * @param idEmpresa
     * @return
     * @throws CRUDException
     */
    public PagoAnticipado createNewPagoAnticipado(String idUsuario, Integer idEmpresa) throws CRUDException;

    /**
     *
     * @param search
     * @return
     * @throws CRUDException
     */
    public List<PagoAnticipado> findAllPagoAnticipado(PagoAnticipadoSearchJson search) throws CRUDException;

    public List<PagoAnticipadoTransaccion> findAllPagoAnticipadoTransacciones(Integer idPagoAnticipado) throws CRUDException;

    public void pendiente(PagoAnticipado pago) throws CRUDException;

    public void anularPagoAnticipado(Integer idPagoAnticipado, String usuario) throws CRUDException;

    public void anularTransaccion(PagoAnticipadoTransaccion trx, String usuario) throws CRUDException;
    public void anularTransaccion(NotaDebitoTransaccion trx, String usuario) throws CRUDException;

    public void guardar(PagoAnticipado idPagoAnticipado) throws CRUDException;

    public PagoAnticipadoTransaccion getPagoAnticipadoTransaccion(Integer idPagoAnticipado) throws CRUDException;

    public PagoAnticipadoTransaccion saveTransaccion(PagoAnticipadoTransaccion trx, String usuario) throws CRUDException;
    public PagoAnticipadoTransaccion saveTransaccionDevolucion(PagoAnticipadoTransaccion trx, String usuario) throws CRUDException;

    public PagoAnticipadoTransaccion updateTransaccion(PagoAnticipadoTransaccion trx) throws CRUDException;

    public void updatePago(PagoAnticipado data) throws CRUDException;

    public Devolucion devolver(Devolucion d, String usuario) throws CRUDException ;

    public List<PagoAnticipadoTransaccion> getPagoAnticipadoTransaccionByNotaDebito(NotaDebito idNotaDebito) throws CRUDException;
}
