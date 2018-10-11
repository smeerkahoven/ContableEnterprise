/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.ejb;

import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.agencia.entities.FormasPago;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.AsientoContablePK;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.ComprobanteContablePK;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.remote.ComprobanteRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.queries.Queries;
import com.seguridad.utils.DateContable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ComprobanteEJB extends FacadeEJB implements ComprobanteRemote {
    
    @Override
    public ComprobanteContablePK getNextComprobantePK(Date fecha, String tipo) throws CRUDException {
        
        Integer gestion = Integer.parseInt(DateContable.getPartitionDate(DateContable.getDateFormat(fecha, DateContable.LATIN_AMERICA_FORMAT)));
        Number next = 0;
        String query = queries.getPropertie(Queries.GET_NEXT_ID_LIBRO);
        query = query.replace("[partition]", gestion.toString());
        
        Query q = em.createNativeQuery(query);
        q.setParameter("1", tipo);
        
        List l = q.getResultList();
        
        if (!l.isEmpty()) {
            next = (Number) l.get(0);
        }
        
        ComprobanteContablePK pk = new ComprobanteContablePK(next.intValue(), gestion);
        
        return pk;
    }
    
    @Override
    public List getComprobantes(String tipo, String estado, String fechaI, String fechaF,
            Integer idEmpresa) throws CRUDException {
        
        String q = "SELECT c FROM ComprobanteContable c ";
        if (tipo.trim().length() > 0 || estado.trim().length() > 0
                || fechaI.trim().length() > 0 || fechaF.trim().length() > 0) {
            q += "WHERE ";
            
            if (tipo.trim().length() > 0) {
                q += " c.tipo=:tipo AND";
            }
            
            if (estado.trim().length() > 0) {
                q += " c.estado=:estado AND";
            }
            if (fechaI.trim().length() > 0) {
                q += " c.fecha>=:fechaI AND";
            }
            
            if (fechaF.trim().length() > 0) {
                q += " c.fecha<=:fechaF AND";
            }
            
            q += " c.idEmpresa=:idEmpresa ";
        }
        
        Query query = em.createQuery(q, ComprobanteContable.class);
        if (tipo.trim().length() > 0) {
            query.setParameter("tipo", tipo);
        }
        
        if (estado.trim().length() > 0) {
            query.setParameter("estado", estado);
        }
        if (fechaI.trim().length() > 0) {
            query.setParameter("fechaI", DateContable.toLatinAmericaDateFormat(fechaI));
        }
        
        if (fechaF.trim().length() > 0) {
            query.setParameter("fechaF", DateContable.toLatinAmericaDateFormat(fechaF));
        }
        
        query.setParameter("idEmpresa", idEmpresa);
        /*System.out.println(q);
        System.out.println(fechaI);
        System.out.println(fechaF);
        System.out.println(DateContable.toLatinAmericaDateFormat(fechaI));
        System.out.println(DateContable.toLatinAmericaDateFormat(fechaF));*/
        
        return query.getResultList();
        
    }

    /**
     * Se realiza el proceso de pasar un boleto a un comprobante contable 1. Se
     * obtiene la configuracion del b
     *
     * @param boleto
     * @throws CRUDException
     */
    @Override
    public void procesarBoleto(Boleto boleto) throws CRUDException {

        //1 Obtenemos la configuracion de la contabilidad del boleto de acuerdo a la empresa
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("idEmpresa", boleto.getIdEmpresa());
        
        ContabilidadBoletaje contabilidad = (ContabilidadBoletaje) get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);
        
        Optional op = Optional.ofNullable(contabilidad);
        if (!op.isPresent()) {
            throw new CRUDException("No existe configuracion de Boletaje para la Entidad " + boleto.getIdEmpresa());
        }
        
    }
    
    @Override
    public ComprobanteContable createComprobante(Aerolinea a, Boleto boleto, Cliente cliente, String tipo, NotaDebito nota) throws CRUDException {
        
        ComprobanteContable comprobante = new ComprobanteContable();
        ComprobanteContablePK pk = new ComprobanteContablePK();
        pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));

        //creamos el concepto
        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        buff.append(a.getNumero());
        
        buff.append("/");

        //numero boleto
        buff.append("#");
        buff.append(boleto.getNumero());
        buff.append("/");

        //Pasajero
        buff.append(boleto.getNombrePasajero().toUpperCase());

        // Rutas
        buff.append("/");
        buff.append(boleto.getIdRuta1() != null ? boleto.getIdRuta1() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta2() != null ? boleto.getIdRuta2() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta3() != null ? boleto.getIdRuta3() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta4() != null ? boleto.getIdRuta4() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta5() != null ? boleto.getIdRuta5() : "");

        //jala el nombre cliente
        comprobante.setNombre(cliente.getNombre());
        comprobante.setConcepto(buff.toString());
        comprobante.setFactorCambiario(boleto.getFactorCambiario());
        comprobante.setFecha(boleto.getFechaEmision());
        comprobante.setFechaInsert(boleto.getFechaInsert());
        comprobante.setIdEmpresa(boleto.getIdEmpresa());
        comprobante.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        comprobante.setTipo(tipo);
        comprobante.setEstado(ComprobanteContable.APROBADO);
        comprobante.setComprobanteContablePK(pk);
        
        ComprobanteContablePK numero = getNextComprobantePK(boleto.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero.getIdLibro());
        
        return comprobante;
    }
    
    @Override
    public ComprobanteContable createComprobante(
            Aerolinea a, Boleto boleto, Cliente cliente, String tipo) throws CRUDException {
        Optional op;
        ComprobanteContable comprobante = new ComprobanteContable();
        ComprobanteContablePK pk = new ComprobanteContablePK();
        pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setEstado(ComprobanteContable.APROBADO);
        comprobante.setComprobanteContablePK(pk);
        //creamos el concepto
        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        buff.append(a.getNumero());
        
        buff.append("/");

        //numero boleto
        buff.append("#");
        buff.append(boleto.getNumero());
        buff.append("/");

        //Pasajero
        buff.append(boleto.getNombrePasajero().toUpperCase());

        // Rutas
        buff.append("/");
        buff.append(boleto.getIdRuta1() != null ? boleto.getIdRuta1() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta2() != null ? boleto.getIdRuta2() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta3() != null ? boleto.getIdRuta3() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta4() != null ? boleto.getIdRuta4() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta5() != null ? boleto.getIdRuta5() : "");

        //jala el nombre cliente
        comprobante.setNombre(cliente.getNombre());
        comprobante.setConcepto(buff.toString());
        comprobante.setFactorCambiario(boleto.getFactorCambiario());
        comprobante.setFecha(boleto.getFechaEmision());
        comprobante.setFechaInsert(boleto.getFechaInsert());
        comprobante.setIdEmpresa(boleto.getIdEmpresa());
        comprobante.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        comprobante.setTipo(tipo);
        
        ComprobanteContablePK numero = getNextComprobantePK(boleto.getFechaEmision(), tipo);
        comprobante.setIdNumeroGestion(numero.getIdLibro());
        
        return comprobante;
    }
    
    @Override
    public ComprobanteContable createAsientoDiarioBoleto(Boleto boleto) throws CRUDException {
        
        Optional op;
        ComprobanteContable comprobante = new ComprobanteContable();
        ComprobanteContablePK pk = new ComprobanteContablePK();
        pk.setGestion(DateContable.getPartitionDateInt(DateContable.getDateFormat(boleto.getFechaEmision(), DateContable.LATIN_AMERICA_FORMAT)));
        comprobante.setEstado(ComprobanteContable.APROBADO);
        comprobante.setComprobanteContablePK(pk);

        //creamos el concepto
        StringBuilder buff = new StringBuilder();

        // DESCRIPCION 
        // AEROLINEA/ # Boleto / Pasajero / Rutas
        Aerolinea a = em.find(Aerolinea.class, boleto.getIdAerolinea().getIdAerolinea());
        if (a != null) {
            buff.append(a.getNumero());
        }
        
        buff.append("/");

        //numero boleto
        buff.append("#");
        buff.append(boleto.getNumero());
        buff.append("/");

        //Pasajero
        buff.append(boleto.getNombrePasajero().toUpperCase());

        // Rutas
        buff.append("/");
        buff.append(boleto.getIdRuta1() != null ? boleto.getIdRuta1() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta2() != null ? boleto.getIdRuta2() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta3() != null ? boleto.getIdRuta3() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta4() != null ? boleto.getIdRuta4() : "");
        buff.append("/");
        buff.append(boleto.getIdRuta5() != null ? boleto.getIdRuta5() : "");

        //jala el nombre cliente
        Cliente c = em.find(Cliente.class, boleto.getIdCliente().getIdCliente());
        op = Optional.ofNullable(c);
        
        if (!op.isPresent()) {
            throw new CRUDException("No se encontro un Cliente para el boleto");
        }
        
        comprobante.setNombre(c.getNombre());
        comprobante.setConcepto(buff.toString());
        comprobante.setFactorCambiario(boleto.getFactorCambiario());
        comprobante.setFecha(boleto.getFechaEmision());
        comprobante.setFechaInsert(boleto.getFechaInsert());
        comprobante.setIdEmpresa(boleto.getIdEmpresa());
        comprobante.setIdUsuarioCreador(boleto.getIdUsuarioCreador());
        comprobante.setTipo(ComprobanteContable.Tipo.ASIENTO_DIARIO);
        
        ComprobanteContablePK numero = getNextComprobantePK(boleto.getFechaEmision(), ComprobanteContable.Tipo.ASIENTO_DIARIO);
        comprobante.setIdNumeroGestion(numero.getIdLibro());
        
        return comprobante;
    }
    
    @Override
    public synchronized ComprobanteContable procesarComprobante(ComprobanteContable comprobante) throws CRUDException {
        
        ComprobanteContablePK numero = getNextComprobantePK(comprobante.getFecha(), comprobante.getTipo());
        
        comprobante.setIdNumeroGestion(numero.getIdLibro());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setComprobanteContablePK(new ComprobanteContablePK(0, numero.getGestion()));
        
        Integer idLibro = insert(comprobante);
        
        comprobante.getComprobanteContablePK().setIdLibro(idLibro);
        
        return comprobante;
        
    }
    
    public ComprobanteContable procesarComprobanteBoleto(ComprobanteContable comprobante) throws CRUDException {
        ComprobanteContablePK numero = getNextComprobantePK(comprobante.getFecha(), comprobante.getTipo());
        
        comprobante.setIdNumeroGestion(numero.getIdLibro());
        comprobante.setFechaInsert(DateContable.getCurrentDate());
        comprobante.setComprobanteContablePK(new ComprobanteContablePK(0, numero.getGestion()));
        
        return comprobante;
        
    }
    
    @Override
    public AsientoContable procesarAsientoContable(AsientoContable a, ComprobanteContable c) throws CRUDException {
        
        a.setFechaMovimiento(DateContable.getCurrentDate());
        a.setIdLibro(c.getComprobanteContablePK().getIdLibro());
        a.setAsientoContablePK(new AsientoContablePK(0, c.getComprobanteContablePK().getGestion()));
        
        a.getAsientoContablePK().setIdAsiento(insert(a));
        
        return a;
        
    }

    /**
     * Crea un asiento Contable para el total a cancelar
     *
     * @param b Boleto
     * @param cc Comprobante Contable
     * @param conf Configuracion de la Contabilidad del Boleto
     * @param a Aerolinea
     * @return
     */
    @Override
    public AsientoContable crearTotalCancelar(Boleto b, ComprobanteContable cc,
            ContabilidadBoletaje conf, Aerolinea a) {
        
        AsientoContable totalCancelar = new AsientoContable();
        
        totalCancelar.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));
        totalCancelar.setIdLibro(cc.getComprobanteContablePK().getIdLibro());
        totalCancelar.setFechaMovimiento(DateContable.getCurrentDate());
        totalCancelar.setEstado(ComprobanteContable.APROBADO);
        totalCancelar.setIdBoleto(b.getIdBoleto());
        
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            totalCancelar.setMontoDebeExt(b.getTotalMontoCancelado());
            totalCancelar.setMontoDebeNac(b.getTotalMontoCancelado().multiply(b.getFactorCambiario()));
            totalCancelar.setIdPlanCuenta(conf.getIdTotalBoletoUs());
            totalCancelar.setMoneda(Moneda.EXTRANJERA);
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            totalCancelar.setMontoDebeExt(b.getTotalMontoCancelado().divide(b.getFactorCambiario()));
            totalCancelar.setMontoDebeNac(b.getTotalMontoCancelado());
            totalCancelar.setIdPlanCuenta(conf.getIdTotalBoletoBs());
            totalCancelar.setMoneda(Moneda.NACIONAL);
        }
        
        return totalCancelar;
    }

    /**
     *
     * @param b
     * @param cc
     * @param conf
     * @param ac
     * @param a
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoPagarLineaAerea(Boleto b, ComprobanteContable cc,
            ContabilidadBoletaje conf, AerolineaCuenta ac) throws CRUDException {
        Optional op;
        try {
            
            AsientoContable montoPagar = new AsientoContable();
            
            montoPagar.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));
            montoPagar.setIdLibro(cc.getComprobanteContablePK().getIdLibro());
            montoPagar.setFechaMovimiento(DateContable.getCurrentDate());
            montoPagar.setEstado(ComprobanteContable.APROBADO);
            montoPagar.setIdBoleto(b.getIdBoleto());
            
            if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
                op = Optional.ofNullable(ac);
                if (!op.isPresent()) {
                    throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonExt para la linea Aerea");
                }
                montoPagar.setIdPlanCuenta(ac.getIdPlanCuentas());
                montoPagar.setMoneda(Moneda.EXTRANJERA);
                op = Optional.ofNullable(b.getMontoComision());
                if (op.isPresent()) {
                    montoPagar.setMontoHaberExt(b.getTotalBoleto().subtract(b.getMontoComision()));
                    montoPagar.setMontoHaberNac(b.getTotalBoleto().subtract(b.getMontoComision()).multiply(b.getFactorCambiario()));
                } else {
                    
                    montoPagar.setMontoHaberExt(b.getTotalBoleto());
                    montoPagar.setMontoHaberNac(b.getTotalBoleto().multiply(b.getFactorCambiario()));
                }
                
            } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
                
                op = Optional.ofNullable(ac);
                if (!op.isPresent()) {
                    throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonNac para la Aerolinea");
                }
                montoPagar.setIdPlanCuenta(ac.getIdPlanCuentas());
                montoPagar.setMoneda(Moneda.NACIONAL);
                
                op = Optional.ofNullable(b.getMontoComision());
                if (op.isPresent()) {
                    montoPagar.setMontoHaberNac(b.getTotalBoleto().subtract(b.getMontoComision()));
                    montoPagar.setMontoHaberExt(b.getTotalBoleto().subtract(b.getMontoComision()).divide(b.getFactorCambiario()));
                } else {
                    montoPagar.setMontoHaberNac(b.getTotalBoleto());
                    montoPagar.setMontoHaberExt(b.getTotalBoleto().divide(b.getFactorCambiario()));
                }
            }
            return montoPagar;
        } catch (CRUDException ex) {
            throw new CRUDException("Exisitio un error al momento de crear el asiento crearMontoPagarLineaAerea");
        }
    }

    /**
     *
     * @param b
     * @param cc
     * @param a
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoComision(Boleto b, ComprobanteContable cc, Aerolinea a, AerolineaCuenta ac)
            throws CRUDException {
        Optional op;
        AsientoContable montoComision = new AsientoContable();
        
        montoComision.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));
        montoComision.setIdLibro(cc.getComprobanteContablePK().getIdLibro());
        montoComision.setFechaMovimiento(DateContable.getCurrentDate());
        montoComision.setEstado(ComprobanteContable.APROBADO);
        montoComision.setIdBoleto(b.getIdBoleto());
        
        System.out.println("Monto Comision: " + b.getMontoComision());
        
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            op = Optional.ofNullable(ac);
            if (!op.isPresent()) {
                throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonExt para la Aerolinea");
            }
            montoComision.setIdPlanCuenta(ac.getIdPlanCuentas());
            montoComision.setMoneda(Moneda.EXTRANJERA);
            
            op = Optional.ofNullable(b.getMontoComision());
            if (op.isPresent()) {
                montoComision.setMontoHaberExt(b.getMontoComision());
                montoComision.setMontoHaberNac(b.getMontoComision().multiply(b.getFactorCambiario()));
            } else {
                return null;
            }
            
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            op = Optional.ofNullable(ac);
            if (!op.isPresent()) {
                throw new CRUDException("No existe Configuracion de Cuenta de Ventas MonNac para la Aerolinea");
            }
            montoComision.setIdPlanCuenta(ac.getIdPlanCuentas());
            montoComision.setMoneda(Moneda.NACIONAL);
            op = Optional.ofNullable(b.getMontoComision());
            if (op.isPresent()) {
                montoComision.setMontoHaberNac(b.getMontoComision());
                montoComision.setMontoHaberExt(b.getMontoComision().divide(b.getFactorCambiario()));
            } else {
                return null;
            }
        }
        return montoComision;
    }

    /**
     *
     * @param b
     * @param cc
     * @param conf
     * @param a
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoFee(Boleto b, ComprobanteContable cc, ContabilidadBoletaje conf, Aerolinea a)
            throws CRUDException {
        AsientoContable montoFee = new AsientoContable();
        
        montoFee.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));
        montoFee.setIdLibro(cc.getComprobanteContablePK().getIdLibro());
        montoFee.setFechaMovimiento(DateContable.getCurrentDate());
        montoFee.setEstado(ComprobanteContable.APROBADO);
        montoFee.setIdBoleto(b.getIdBoleto());
        
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            montoFee.setIdPlanCuenta(conf.getIdCuentaFee());
            montoFee.setMoneda(Moneda.EXTRANJERA);
            Optional op = Optional.ofNullable(b.getMontoFee());
            if (op.isPresent()) {
                montoFee.setMontoHaberExt(b.getMontoFee());
                montoFee.setMontoHaberNac(b.getMontoFee().multiply(b.getFactorCambiario()));
            } else {
                return null;
            }
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            montoFee.setIdPlanCuenta(conf.getIdCuentaFee());
            montoFee.setMoneda(Moneda.NACIONAL);
            Optional op = Optional.ofNullable(b.getMontoFee());
            if (op.isPresent()) {
                montoFee.setMontoHaberNac(b.getMontoFee());
                montoFee.setMontoHaberExt(b.getMontoFee().divide(b.getFactorCambiario()));
            } else {
                return null;
            }
        }
        return montoFee;
    }

    /**
     *
     * @param b
     * @param cc
     * @param conf
     * @param a
     * @return
     * @throws CRUDException
     */
    @Override
    public AsientoContable crearMontoDescuentos(Boleto b, ComprobanteContable cc,
            ContabilidadBoletaje conf, Aerolinea a)
            throws CRUDException {
        AsientoContable montoDescuentos = new AsientoContable();
        
        montoDescuentos.setAsientoContablePK(new AsientoContablePK(0, cc.getComprobanteContablePK().getGestion()));
        montoDescuentos.setIdLibro(cc.getComprobanteContablePK().getIdLibro());
        montoDescuentos.setFechaMovimiento(DateContable.getCurrentDate());
        montoDescuentos.setEstado(ComprobanteContable.APROBADO);
        montoDescuentos.setIdBoleto(b.getIdBoleto());
        
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            montoDescuentos.setIdPlanCuenta(conf.getIdDescuentos());
            montoDescuentos.setMoneda(Moneda.EXTRANJERA);
            Optional op = Optional.ofNullable(b.getMontoDescuento());
            if (op.isPresent()) {
                montoDescuentos.setMontoDebeExt(b.getMontoDescuento());
                montoDescuentos.setMontoDebeNac(b.getMontoDescuento().multiply(b.getFactorCambiario()));
            } else {
                return null;
            }
            
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            montoDescuentos.setIdPlanCuenta(conf.getIdDescuentos());
            montoDescuentos.setMoneda(Moneda.NACIONAL);
            Optional op = Optional.ofNullable(b.getMontoDescuento());
            if (op.isPresent()) {
                montoDescuentos.setMontoDebeExt(b.getMontoDescuento());
                montoDescuentos.setMontoDebeNac(b.getMontoDescuento().divide(b.getFactorCambiario()));
            } else {
                return null;
            }
        }
        return montoDescuentos;
    }
    
    @Override
    public AsientoContable createTotalCancelarIngresoCajaDebe(ComprobanteContable c,
            ContabilidadBoletaje conf, Aerolinea aerolinea, Boleto boleto) throws CRUDException {
        
        AsientoContable ingCajaDebe = new AsientoContable();
        ingCajaDebe.setAsientoContablePK(new AsientoContablePK(0, c.getComprobanteContablePK().getGestion()));
        ingCajaDebe.setIdLibro(c.getComprobanteContablePK().getIdLibro());
        ingCajaDebe.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaDebe.setEstado(ComprobanteContable.APROBADO);
        ingCajaDebe.setIdBoleto(boleto.getIdBoleto());
        
        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            ingCajaDebe.setMoneda(Moneda.EXTRANJERA);
            ingCajaDebe.setMontoDebeExt(c.getTotalDebeExt());
            ingCajaDebe.setMontoDebeNac(c.getTotalDebeNac());
            //Si es BSP

            if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO) || boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeUsd());
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(boleto.getContadoIdCuenta());
            }
            
            if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
                if (aerolinea.getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeUsd());
                } else {
                    ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeUsd());
                }
            }
            
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            ingCajaDebe.setMoneda(Moneda.NACIONAL);
            ingCajaDebe.setMontoDebeNac(c.getTotalDebeNac());
            ingCajaDebe.setMontoDebeExt(c.getTotalDebeExt());
            //Si es BSP
             if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO) || boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoDebeBs());
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                ingCajaDebe.setIdPlanCuenta(conf.getDepositoBancoDebeBs());
            }
            
            if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
                if (aerolinea.getBsp()) {
                    ingCajaDebe.setIdPlanCuenta(conf.getTarjetaCreditoBspDebeBs());
                } else {
                    ingCajaDebe.setIdPlanCuenta(conf.getCuentaEfectivoNoBspDebeBs());
                }
            }
        }
        
        return ingCajaDebe;
        
    }
    
    @Override
    public AsientoContable createTotalCancelarIngresoClienteHaber(ComprobanteContable c, ContabilidadBoletaje conf, Aerolinea aerolinea, Boleto boleto) throws CRUDException {
        AsientoContable ingCajaHaber = new AsientoContable();
        ingCajaHaber.setAsientoContablePK(new AsientoContablePK(0, c.getComprobanteContablePK().getGestion()));
        ingCajaHaber.setIdLibro(c.getComprobanteContablePK().getIdLibro());
        ingCajaHaber.setFechaMovimiento(DateContable.getCurrentDate());
        ingCajaHaber.setEstado(ComprobanteContable.APROBADO);
        ingCajaHaber.setIdBoleto(boleto.getIdBoleto());
        
        if (boleto.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            ingCajaHaber.setMoneda(Moneda.EXTRANJERA);
            ingCajaHaber.setMontoHaberExt(c.getTotalDebeExt());
            ingCajaHaber.setMontoHaberNac(c.getTotalDebeNac());
            //Si es BSP
            if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO) || boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberUsd());
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberUsd());
            }
            
            if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
                if (aerolinea.getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberUsd());
                } else {
                    ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberUsd());
                }
            }
            
        } else if (boleto.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            ingCajaHaber.setMoneda(Moneda.NACIONAL);
            ingCajaHaber.setMontoHaberNac(c.getTotalDebeNac());
            ingCajaHaber.setMontoHaberExt(c.getTotalDebeExt());
            //Si es BSP
            if (boleto.getContadoTipo().equals(FormasPago.CONTADO_EFECTIVO) || boleto.getContadoTipo().equals(FormasPago.CONTADO_CHEQUE)) {
                ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoHaberBs());
            } else if (boleto.getContadoTipo().equals(FormasPago.CONTADO_DEPOSITO)) {
                ingCajaHaber.setIdPlanCuenta(conf.getDepositoBancoHaberBs());
            }
            
            if (boleto.getFormaPago().equals(FormasPago.TARJETA)) {
                if (aerolinea.getBsp()) {
                    ingCajaHaber.setIdPlanCuenta(conf.getTarjetaCreditoBspHaberBs());
                } else {
                    ingCajaHaber.setIdPlanCuenta(conf.getCuentaEfectivoNoBspHaberBs());
                }
            }
        }
        
        return ingCajaHaber;
    }
    
}
