/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.Devolucion;
import com.contabilidad.entities.PagoAnticipado;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.DevolucionRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author xeio
 */
@Stateless
public class DevolucionEJB extends FacadeEJB implements DevolucionRemote {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @EJB
    private ComprobanteRemote ejbComprobante;

    @Override
    public Devolucion saveDevolucionFromPagoAnticipado(Devolucion devolucion, PagoAnticipado pFromDb) throws CRUDException {

        Double montoDevolucion = devolucion.getMonto().doubleValue();
        Double montoMaxDevolucion = pFromDb.getMontoAnticipado().subtract(pFromDb.getMontoTotalAcreditado() != null ? pFromDb.getMontoTotalAcreditado() : BigDecimal.ZERO).doubleValue();

        if (montoDevolucion > montoMaxDevolucion) {
            String m = "El monto maximo permitido para la devolucion del Pago Anticipado Nro:<nro> es <monto>. Si el pago tiene Depositos acreditados, anule los necesarios para poder llegar al monto requerido.".replace("nro", pFromDb.getIdPagoAnticipado().toString());
            m = m.replace("monto", montoMaxDevolucion.toString());
            throw new CRUDException(m);
        }
        // Registramos la Devolucion
        Cliente cFromDb = em.find(Cliente.class, pFromDb.getIdCliente().getIdCliente());
        devolucion.setIdCliente(cFromDb);
        devolucion.setIdPagoAnticipado(pFromDb);

        devolucion.setEstado(Estado.EMITIDO);
        devolucion.setFechaInsert(DateContable.getCurrentDate());

        devolucion.setIdDevolucion(insert(devolucion));

        // Creamos los asientos Contables 
        ContabilidadBoletaje conf = ejbNotaDebito.getConfiguracion(devolucion.getIdEmpresa());

        ComprobanteContable comprobanteEgreso = ejbComprobante.createComprobante(ComprobanteContable.Tipo.COMPROBANTE_EGRESO,
                devolucion.getConcepto(), devolucion);
        comprobanteEgreso.setIdLibro(insert(comprobanteEgreso));

        AsientoContable debe = ejbComprobante.createTotalDebe(comprobanteEgreso, conf, devolucion);
        insert(debe);

        AsientoContable haber = ejbComprobante.createTotalHaber(comprobanteEgreso, conf, devolucion);
        insert(haber);

        ejbComprobante.actualizarMontosFinalizar(comprobanteEgreso);
        
        return devolucion;
    }

}
