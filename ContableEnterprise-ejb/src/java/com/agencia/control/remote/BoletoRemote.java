/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.remote;

import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.Boleto;
import com.agencia.entities.BoletoPlanillaBsp;
import com.agencia.entities.Promotor;
import com.agencia.search.dto.BoletoSearchForm;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.response.json.boletaje.PlanillaSearchForm;
import com.response.json.boletaje.VentaBoletosSearchJson;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.control.remote.DaoRemoteFacade;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author xeio
 */
@Remote
public interface BoletoRemote extends DaoRemoteFacade {

    @Override
    public int insert(Entidad e) throws CRUDException;

    @Override
    public void executeNative(String query, HashMap<String, Object> parameters) throws CRUDException;

    @Override
    public List get(String namedQuery, Class<?> typeClass, HashMap parameters) throws CRUDException;

    @Override
    public Entidad get(Integer id, Class<?> typeClass) throws CRUDException;

    @Override
    public List get() throws CRUDException;

    @Override
    public List getList(String namedQuery, Class<?> typeClass) throws CRUDException;

    /**
     * Obtiene los tipos de Emision del Boleto que se almacenan en la
     * cnt_tipo_emision
     *
     * @return
     * @throws CRUDException
     */
    public List getTipoEmision() throws CRUDException;

    public boolean isBoletoRegistrado(Boleto b) throws CRUDException;

    public Boleto isBoletoRegistradoOrigen(Boleto b) throws CRUDException;

    public Boleto procesarBoleto(Boleto b) throws CRUDException;

    public List getPasajerosPorCliente(Integer idCliente) throws CRUDException;

    public Boleto saveBoletoMultiple(Boleto b) throws CRUDException;

    public Boleto saveBoletoVoid(Boleto b, NotaDebito n, String usuario) throws CRUDException;

    /**
     * Busqueda de Boletos por medio de los parametros de entrada
     *
     * @param idCliente
     * @param idAerolinea
     * @param idEmpresa
     * @param fechaI
     * @param fechaF
     * @return
     * @throws CRUDException
     */
    public List getBoletos(BoletoSearchForm search) throws CRUDException;

    /**
     * Retorna la lista de Boletos Multiples para visualizar en la pantalla como
     * una Tabla
     *
     * @param idBoleto
     * @param idBoletoPadre
     * @return
     * @throws CRUDException
     */
    public List getBoletosMultiples(Integer idBoleto, Integer idBoletoPadre) throws CRUDException;

    /**
     * Realiza la anulacion del Boleto. Debe Verificar que el Boleto no este
     * Anulado ya, ademas de verificar si
     *
     * Si esta en Pendiente, solo cambia el estado del Boleto Si esta en
     * Aprobado ya paso a Contabilidad, se deben anular : los asientos contables
     * del Boleto
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    public Boleto anular(Boleto boleto) throws CRUDException;
    
    public Boleto anularBoleto(Integer boleto) throws CRUDException;
    public CargoBoleto anularCargo(Integer cargo) throws CRUDException;

    /**
     * 
     * @param b
     * @throws CRUDException 
     */
    public void eliminar(Boleto b) throws CRUDException;
    /**
     * Busqueda en la cnt_boletos de los boletos AM=AMADEUS con estado
     * C=CARAGADO AUTOMATICO para poder anexarlo a una nota de debito
     *
     * @param idEmpresa
     * @return
     * @throws CRUDException
     */
    public List<Boleto> getBoletosAmadeusCargados(Integer idEmpresa) throws CRUDException;

    /**
     * Busqueda en la cnt_boletos de los Boletos SM, SV con estado C=CARGADO
     * Automatico para poder anexarlo a una nota de debito
     *
     * @param idEmpresa
     * @return
     * @throws CRUDException
     */
    public List<Boleto> getBoletosSabreCargados(Integer idEmpresa) throws CRUDException;

    /**
     * Registra un boleto desde la Nota de Debito. Asocia el Boleto Con la nota
     * de Debito y Actualiza las transacciones.
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    public int insertarBoleto(Boleto boleto) throws CRUDException;
    
    
    /**
     * 
     * @param boleto
     * @return
     * @throws CRUDException 
     */
    public int updateBoleto(Boleto boleto) throws CRUDException;

    /**
     * Procesa un boleto y lo pasa a Contabilidad de acuerdo a la nota de Debito
     *
     * @param boleto
     * @param nota
     * @return
     * @throws CRUDException
     */
    public boolean enviarAsientoDiario(final Boleto boleto, final NotaDebito nota,
            final ComprobanteContable comprobante, final ContabilidadBoletaje conf) throws CRUDException;

    public boolean enviarAsientoDiario(final CargoBoleto boleto, final NotaDebito nota,
            final ComprobanteContable comprobante, final ContabilidadBoletaje conf) throws CRUDException;

    /**
     * Crea el Ingreso de Caja
     *
     * @param boleto
     * @param nota
     * @param comprobante
     * @param caja
     * @param notaTran
     * @param conf
     * @return
     * @throws CRUDException
     */
    public boolean crearTransaccionIngresoCaja(final Boleto boleto, final NotaDebito nota,
            final ComprobanteContable comprobante, final IngresoCaja caja, final NotaDebitoTransaccion notaTran) throws CRUDException;

    public boolean validarConfiguracion(ContabilidadBoletaje conf) throws CRUDException;
    
    
    public AerolineaCuenta getAerolineCuenta(Boleto b, String tipo) throws CRUDException;

    /**
     * 
     * @param search
     * @return
     * @throws CRUDException 
     */
    public List<BoletoPlanillaBsp> getPlanillaBsp (PlanillaSearchForm search) throws CRUDException;
    
    public List<BoletoPlanillaBsp> getReporteVentas(VentaBoletosSearchJson search) throws CRUDException;
    
    public List<BoletoPlanillaBsp> getReporteComisionCliente(VentaBoletosSearchJson search) throws CRUDException;
    
    
    
}
