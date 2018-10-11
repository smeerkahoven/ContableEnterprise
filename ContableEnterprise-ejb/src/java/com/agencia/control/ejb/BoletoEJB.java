/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agencia.control.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Aerolinea;
import com.agencia.entities.AerolineaCuenta;
import com.agencia.entities.Boleto;
import com.agencia.entities.BoletoSearch;
import com.agencia.entities.Cliente;
import com.agencia.entities.ClientePasajero;
import com.agencia.entities.EmisionBoleto;
import com.agencia.entities.FormasPago;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

/**
 *
 * @author xeio
 */
@Stateless
public class BoletoEJB extends FacadeEJB implements BoletoRemote {

    @EJB
    private NotaDebitoRemote ejbNotaDebito;

    @EJB
    private ComprobanteRemote ejbComprobante;

    @EJB
    private IngresoCajaRemote ejbIngresoCaja;

    @Override
    public List getTipoEmision() throws CRUDException {

        Query query = em.createNamedQuery("EmisionBoleto.findAll");
        List l = query.getResultList();

        List resp = new LinkedList();

        l.forEach((x) -> {
            EmisionBoleto e = (EmisionBoleto) x;
            ComboSelect s = new ComboSelect();
            s.setId(e.getIdEmision());
            s.setName(e.getNombre());

            resp.add(s);
        });

        return resp;
    }

    @Override
    public boolean isBoletoRegistrado(Boleto b) throws CRUDException {

        Query q = em.createNamedQuery("Boleto.findNroBoleto", Boleto.class);
        System.out.println("Numero Boleto :" + b.getNumero());
        q.setParameter("numero", b.getNumero());

        List l = q.getResultList();
        return !l.isEmpty();

    }

    /**
     * Valida la configuracion del Contabilidad del Boleto
     *
     * @param conf
     * @return
     * @throws CRUDException
     */
    private boolean validarConfiguracion(ContabilidadBoletaje conf) throws CRUDException {
        Optional op = Optional.ofNullable(conf.getIdTotalBoletoBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe configuracion para la el Total Boleto Bs");
        }

        op = Optional.ofNullable(conf.getIdTotalBoletoUs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe configuracion para la el Total Boleto Usd");
        }

        op = Optional.ofNullable(conf.getIdCuentaFee());
        if (!op.isPresent()) {
            throw new CRUDException("No existe configuracion para la el Total Cuenta Fee");
        }

        op = Optional.ofNullable(conf.getIdDescuentos());
        if (!op.isPresent()) {
            throw new CRUDException("No existe configuracion para la el Total Descuentos");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspDebeBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Efectivo No BSP Debe Bs");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspDebeUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Efectivo No BSP Debe Usd");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspHaberBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Efectivo No BSP Haber Bs");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspHaberUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Efectivo No BSP Haber Usd");
        }
        op = Optional.ofNullable(conf.getTarjetaCreditoBspDebeBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Tarjeta Credito Bsp Debe BS");
        }
        op = Optional.ofNullable(conf.getTarjetaCreditoBspDebeUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Tarjeta Credito Bsp Debe USD");
        }
        op = Optional.ofNullable(conf.getTarjetaCreditoBspHaberBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Tarjeta Credito Bsp Haber BS");
        }
        op = Optional.ofNullable(conf.getTarjetaCreditoBspHaberUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Tarjeta Credito Bsp Debe USD");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspDebeBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Cuenta Efectivo No BSP Debe Bs");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspDebeUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Cuenta Efectivo No BSP Debe USD");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspHaberBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Cuenta Efectivo No BSP Haber Bs");
        }
        op = Optional.ofNullable(conf.getCuentaEfectivoNoBspHaberUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Cuenta Efectivo No BSP Haber USD");
        }

        return true;
    }

    private AerolineaCuenta getAerolineCuenta(Boleto b, String tipo) throws CRUDException {

        HashMap<String, Object> parameters2 = new HashMap<>();
        parameters2.put("idAerolinea", b.getIdAerolinea().getIdAerolinea());
        parameters2.put("tipo", tipo);//Ventas
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            parameters2.put("moneda", Moneda.EXTRANJERA);
        } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
            parameters2.put("moneda", Moneda.NACIONAL);
        }
        List lac = get("AerolineaCuenta.find", AerolineaCuenta.class, parameters2);
        // Verifica que la linea aerea tena configurada las cuentas de Ventas

        if (lac.isEmpty()) {
            return null;
        }
        AerolineaCuenta ac = (AerolineaCuenta) lac.get(0);
        return ac;
    }

    private void saveClientePasajero(Boleto b) throws CRUDException {
        ClientePasajero cp = createClientePasajero(b);
        HashMap<String, Object> parpas = new HashMap<>();
        parpas.put("idCliente", b.getIdCliente().getIdCliente());
        parpas.put("nombrePasajero", b.getNombrePasajero());

        List search = get("ClientePasajero.findPasajero", ClientePasajero.class, parpas);
        if (search.isEmpty()) {
            cp.setIdClientePasajero(insert(cp));
        }//else ya existe el cliente
    }

    /**
     * Realiza el proceso de insertado del boleto en la siguiente manera 1.
     * Registra el boleto en la tabla cnt_boletaje 2. Crea la nota de debito
     * para el boleto en la tabla cnt_nota_debito 3. Regitra en el asiento
     * diario de acuerdo al modo de pago del boleto en la cnt_comprobantes
     *
     * @param b
     * @return
     * @throws CRUDException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public synchronized Boleto procesarBoleto(Boleto b) throws CRUDException {
        Optional op;
        Aerolinea a = em.find(Aerolinea.class, b.getIdAerolinea().getIdAerolinea());
        Cliente c = em.find(Cliente.class, b.getIdCliente().getIdCliente());
        NotaDebito notaDebito = null;
        NotaDebitoTransaccion transaccion = null;
        ComprobanteContable comprobanteAsiento = null, comprobanteIngreso = null;
        AsientoContable totalCancelar = null, montoPagarLinea = null,
                montoDescuento = null, montoComision = null, montoFee = null;

        AsientoContable ingTotalCancelarCaja = null, ingTotalCancelarHaber = null;
        IngresoCaja ingreso = null;
        IngresoTransaccion ingTran = null;

        try {
            // Revisamos que el boleto no este registrado
            if (isBoletoRegistrado(b)) {
                throw new CRUDException("El Numero de Boleto ya ha sido registrado");
            }

            //4. Obtenemos la configuracion del Boleto para guardar en el comprobanteAsiento
            HashMap<String, Integer> parameters = new HashMap<>();
            parameters.put("idEmpresa", b.getIdEmpresa());
            List lconf = ejbComprobante.get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);
            if (lconf.isEmpty()) {
                throw new CRUDException("Los parametros de Contabilidad para la empresa no estan Configurados");
            }

            AerolineaCuenta av = getAerolineCuenta(b, "V");

            if (av == null) {
                throw new CRUDException("No existe Cuenta asociada a la Aerolinea para Ventas");
            }

            AerolineaCuenta ac = getAerolineCuenta(b, "C");

            if (ac == null) {
                throw new CRUDException("No existe Cuenta asociada a la Aerolinea para Comisiones");
            }

            //Obtenemos la configuracion de las cuentas para el boletaje
            ContabilidadBoletaje cbconf = (ContabilidadBoletaje) lconf.get(0);

            // SI no existiera alguna configuraion, no hace nada
            if (validarConfiguracion(cbconf)) {
                //1. Registra el nombre del pasajero en la tabla cnt_cliente_pasajero
                saveClientePasajero(b);

                //2. CRear nota de debito para el boleto en la tabla cnt_nota_debito
                notaDebito = ejbNotaDebito.createNotaDebito(b);
                notaDebito.setIdNotaDebito(insert(notaDebito));
                //notaDebito.getNotaDebitoPK().setIdNotaDebito(insert(notaDebito));
                //crea la transaccion de la nota de Debito
                transaccion = ejbNotaDebito.createNotaDebitoTransaccion(b, notaDebito);
                //transaccion.getNotaDebitoTransaccionPK().setIdNotaDebitoTransaccion(insert(transaccion));
                insert(transaccion);
                //3. Registramos el Boleto
                b.setIdNotaDebito(notaDebito.getIdNotaDebito());
                b.setEstado(Boleto.Estado.APROBADO);
                
                //insert(b);
                insert(b);

                // creamos el Comprobante Contable
                comprobanteAsiento = ejbComprobante.createAsientoDiarioBoleto(b);
                comprobanteAsiento.setIdNotaDebito(notaDebito.getIdNotaDebito());
                insert(comprobanteAsiento);
                // se crean los asientos de acuerdo a la configuracion.

                //TotalCancelar
                totalCancelar = ejbComprobante.crearTotalCancelar(b, comprobanteAsiento, cbconf, a);
                insert(totalCancelar);
                //ejbComprobante.insert(totalCancelar);
                //DiferenciaTotalBoleto
                montoPagarLinea = ejbComprobante.crearMontoPagarLineaAerea(b, comprobanteAsiento, cbconf, av);
                //ejbComprobante.insert(montoPagarLinea);
                insert(montoPagarLinea);
                //Comision
                montoComision = ejbComprobante.crearMontoComision(b, comprobanteAsiento, a, ac);
                //ejbComprobante.insert(montoComision);
                insert(montoComision);
                //Fee
                montoFee = ejbComprobante.crearMontoFee(b, comprobanteAsiento, cbconf, a);
                //ejbComprobante.insert(montoFee);
                insert(montoFee);
                //Descuento
                montoDescuento = ejbComprobante.crearMontoDescuentos(b, comprobanteAsiento, cbconf, a);
                //ejbComprobante.insert(montoDescuento);
                insert(montoDescuento);

                //actualizamos los montos Totales del Comprobante.
                double totalDebeNac = 0;
                double totalDebeExt = 0;
                double totalHaberNac = 0;
                double totalHaberExt = 0;
                //Se realizan las sumas para el comprobanteAsiento.
                if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
                    op = Optional.ofNullable(totalCancelar.getMontoDebeExt());
                    if (op.isPresent()) {
                        totalDebeExt += totalCancelar.getMontoDebeExt().doubleValue();
                    }

                    op = Optional.ofNullable(montoDescuento);
                    if (op.isPresent()) {
                        totalDebeExt += montoDescuento.getMontoDebeExt().doubleValue();
                    }

                    op = Optional.ofNullable(totalDebeExt);
                    if (op.isPresent()) {
                        totalDebeNac = totalDebeExt * b.getFactorCambiario().doubleValue();
                    }
                    // Haber

                    op = Optional.ofNullable(montoPagarLinea.getMontoHaberExt());
                    if (op.isPresent()) {
                        totalHaberExt += montoPagarLinea.getMontoHaberExt().doubleValue();
                    }

                    op = Optional.ofNullable(montoComision);
                    if (op.isPresent()) {
                        totalHaberExt += montoComision.getMontoHaberExt().doubleValue();
                    }

                    op = Optional.ofNullable(montoFee);
                    if (op.isPresent()) {
                        totalHaberExt += montoFee.getMontoHaberExt().doubleValue();
                    }

                    op = Optional.ofNullable(totalHaberExt);
                    if (op.isPresent()) {
                        totalHaberNac = totalHaberExt * b.getFactorCambiario().doubleValue();
                    }

                } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
                    op = Optional.ofNullable(totalCancelar.getMontoDebeNac());
                    if (op.isPresent()) {
                        totalDebeNac += totalCancelar.getMontoDebeNac().doubleValue();
                    }

                    op = Optional.ofNullable(montoDescuento.getMontoDebeNac());
                    if (op.isPresent()) {
                        totalDebeNac += montoDescuento.getMontoDebeNac().doubleValue();
                    }
                    op = Optional.ofNullable(totalDebeNac);
                    if (op.isPresent()) {
                        totalDebeExt = totalDebeNac / b.getFactorCambiario().doubleValue();
                    }
                    //

                    op = Optional.ofNullable(montoPagarLinea.getMontoHaberNac());
                    if (op.isPresent()) {
                        totalHaberNac += montoPagarLinea.getMontoHaberNac().doubleValue();
                    }
                    op = Optional.ofNullable(montoComision.getMontoHaberNac());
                    if (op.isPresent()) {
                        totalHaberNac += montoComision.getMontoHaberNac().doubleValue();
                    }
                    op = Optional.ofNullable(montoFee.getMontoHaberNac());
                    if (op.isPresent()) {
                        totalHaberNac += montoFee.getMontoHaberNac().doubleValue();
                    }
                    op = Optional.ofNullable(totalHaberNac);
                    if (op.isPresent()) {
                        totalHaberExt = totalHaberNac / b.getFactorCambiario().doubleValue();
                    }
                }

                comprobanteAsiento.setTotalDebeExt(new BigDecimal(totalDebeExt));
                comprobanteAsiento.setTotalHaberExt(new BigDecimal(totalHaberExt));
                comprobanteAsiento.setTotalDebeNac(new BigDecimal(totalDebeNac));
                comprobanteAsiento.setTotalHaberNac(new BigDecimal(totalHaberNac));

                update(comprobanteAsiento);

                // creamos para las formas de pago
                //Si son Contado o Tarjeta, se crea el Ingreso a Caja y el Comprobante de Ingreso
                if (b.getFormaPago().equals(FormasPago.CONTADO) || b.getFormaPago().equals(FormasPago.TARJETA)) {
                    //Crear Ingreso a Caja
                    ingreso = ejbIngresoCaja.createIngresoCaja(b, notaDebito);
                    insert(ingreso);

                    ingTran = ejbIngresoCaja.createIngresoCajaTransaccion(b, notaDebito, transaccion, ingreso);
                    insert(ingTran);

                    //Crear Comprobante de Ingreso
                    comprobanteIngreso = ejbComprobante.createComprobante(a, b, c, ComprobanteContable.Tipo.COMPROBANTE_INGRESO);
                    if (ingreso.getMoneda().equals(Moneda.EXTRANJERA)) {
                        comprobanteIngreso.setTotalDebeExt(ingreso.getMontoAbonadoUsd());
                        comprobanteIngreso.setTotalHaberExt(ingreso.getMontoAbonadoUsd());
                        comprobanteIngreso.setTotalDebeNac(ingreso.getMontoAbonadoUsd().multiply(ingreso.getFactorCambiario()));
                        comprobanteIngreso.setTotalHaberNac(ingreso.getMontoAbonadoUsd().multiply(ingreso.getFactorCambiario()));

                        notaDebito.setMontoAdeudadoUsd(notaDebito.getMontoTotalUsd().subtract(ingreso.getMontoAbonadoUsd()));
                    } else {
                        comprobanteIngreso.setTotalDebeExt(ingreso.getMontoAbonadoBs());
                        comprobanteIngreso.setTotalHaberExt(ingreso.getMontoAbonadoBs());
                        comprobanteIngreso.setTotalDebeNac(ingreso.getMontoAbonadoBs().divide(ingreso.getFactorCambiario()));
                        comprobanteIngreso.setTotalHaberNac(ingreso.getMontoAbonadoBs().divide(ingreso.getFactorCambiario()));

                        notaDebito.setMontoAdeudadoBs(notaDebito.getMontoTotalBs().subtract(ingreso.getMontoAbonadoBs()));
                    }
                    insert(comprobanteIngreso);

                    ingTotalCancelarCaja = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, cbconf, a, b);
                    insert(ingTotalCancelarCaja);

                    ingTotalCancelarHaber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, cbconf, a, b);
                    insert(ingTotalCancelarHaber);

                    //actualizar nota debito
                    update(notaDebito);
                }

                b.setIdLibro(notaDebito.getIdNotaDebito());

                if (ingreso != null) {
                    b.setIdIngresoCaja(ingreso.getIdIngresoCaja());
                }

                update(b);
            }
        } catch (Exception e) {

            em.clear();

            Optional opex = Optional.ofNullable(notaDebito);
            if (opex.isPresent()) {
                remove(notaDebito);
            }

            opex = Optional.ofNullable(transaccion);
            if (opex.isPresent()) {
                remove(transaccion);
            }

            opex = Optional.ofNullable(comprobanteAsiento);
            if (opex.isPresent()) {
                remove(comprobanteAsiento);
            }

            opex = Optional.ofNullable(totalCancelar);
            if (opex.isPresent()) {
                remove(totalCancelar);
            }

            opex = Optional.ofNullable(montoPagarLinea);
            if (opex.isPresent()) {
                remove(montoPagarLinea);
            }
            opex = Optional.ofNullable(montoDescuento);
            if (opex.isPresent()) {
                remove(montoDescuento);
            }
            opex = Optional.ofNullable(montoComision);
            if (opex.isPresent()) {
                remove(montoComision);
            }
            opex = Optional.ofNullable(montoFee);
            if (opex.isPresent()) {
                remove(montoFee);
            }

            opex = Optional.ofNullable(b);
            if (opex.isPresent() && b.getIdBoleto() > 0) {
                remove(b);
            }

            opex = Optional.ofNullable(ingreso);
            if (opex.isPresent()) {
                remove(ingreso);
            }

            opex = Optional.ofNullable(ingTran);
            if (opex.isPresent()) {
                remove(ingTran);
            }

            opex = Optional.ofNullable(ingTotalCancelarCaja);
            if (opex.isPresent()) {
                remove(ingTotalCancelarCaja);
            }

            opex = Optional.ofNullable(ingTotalCancelarHaber);
            if (opex.isPresent()) {
                remove(ingTotalCancelarHaber);
            }

            throw new CRUDException(e.getMessage());

        }

        em.flush();
        return b;
    }

    private ClientePasajero createClientePasajero(Boleto b) {
        ClientePasajero cp = new ClientePasajero();
        cp.setIdCliente(b.getIdCliente().getIdCliente());
        cp.setNombrePasajero(b.getNombrePasajero());
        return cp;
    }

    @Override
    public List getPasajerosPorCliente(Integer idCliente) throws CRUDException {

        Optional op = Optional.ofNullable(idCliente);
        if (op.isPresent()) {
            Query q = em.createNamedQuery("ClientePasajero.find");
            q.setParameter("idCliente", idCliente);
            List res = new LinkedList();

            q.getResultList().forEach((x) -> {
                ComboSelect s = new ComboSelect();
                s.setName((String) x);
                res.add(s);
            });

            return res;
        }

        return new LinkedList();

    }

    @Override
    public List getBoletos(Integer idCliente, Integer idAerolinea, Integer idEmpresa,
            String fechaI, String fechaF) throws CRUDException {

        Optional op = Optional.ofNullable(idEmpresa);
        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        String q = "SELECT b FROM Boleto b WHERE b.idEmpresa=:idEmpresa ";

        op = Optional.ofNullable(idCliente);
        if (op.isPresent()) {
            q += " AND b.idCliente=:idCliente";
        }

        op = Optional.ofNullable(idAerolinea);
        if (op.isPresent()) {
            q += " AND b.idAerolinea=:idAerolinea";
        }

        op = Optional.ofNullable(fechaI);
        if (op.isPresent()) {
            q += " AND b.fechaEmision>=:fechaI ";
        }

        op = Optional.ofNullable(fechaF);
        if (op.isPresent()) {
            q += " AND b.fechaEmision<=:fechaF";
        }

        Query query = em.createQuery(q, BoletoSearch.class);

        op = Optional.ofNullable(idCliente);
        if (op.isPresent()) {
            query.setParameter("idCliente", idCliente);
        }

        op = Optional.ofNullable(idAerolinea);
        if (op.isPresent()) {
            query.setParameter("idAerolinea", idAerolinea);
        }

        op = Optional.ofNullable(fechaI);
        if (op.isPresent()) {
            query.setParameter("fechaI", DateContable.toLatinAmericaDateFormat(fechaI));
        }

        op = Optional.ofNullable(fechaF);
        if (op.isPresent()) {
            query.setParameter("fechaF", DateContable.toLatinAmericaDateFormat(fechaF));
        }

        query.setParameter("idEmpresa", idEmpresa);

        return query.getResultList();

    }

    @Override
    public Boleto saveBoleto(Boleto b) throws CRUDException {

        Optional op;
        Aerolinea a = em.find(Aerolinea.class, b.getIdAerolinea().getIdAerolinea());
        Cliente c = em.find(Cliente.class, b.getIdCliente().getIdCliente());

        // Revisamos que el boleto no este registrado
        if (isBoletoRegistrado(b)) {
            throw new CRUDException("El Numero de Boleto ya ha sido registrado");
        }

        //4. Obtenemos la configuracion del Boleto para guardar en el comprobanteAsiento
        HashMap<String, Integer> parameters = new HashMap<>();
        parameters.put("idEmpresa", b.getIdEmpresa());
        List lconf = ejbComprobante.get("ContabilidadBoletaje.find", ContabilidadBoletaje.class, parameters);
        if (lconf.isEmpty()) {
            throw new CRUDException("Los parametros de Contabilidad para la empresa no estan Configurados");
        }

        AerolineaCuenta av = getAerolineCuenta(b, "V");

        if (av == null) {
            throw new CRUDException("No existe Cuenta asociada a la Aerolinea para Ventas");
        }

        AerolineaCuenta ac = getAerolineCuenta(b, "C");

        if (ac == null) {
            throw new CRUDException("No existe Cuenta asociada a la Aerolinea para Comisiones");
        }
        
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)){
            b.setMoneda(Moneda.EXTRANJERA);
        }else {
            b.setMoneda(Moneda.NACIONAL);
        }
        
        b.setEstado(Boleto.Estado.PENDIENTE);
        
        insert(b);
        
        
        return b ;

    }

    @Override
    public List getBoletosMultiples(Integer idBoleto, Integer idBoletoPadre) throws CRUDException {
        
        Optional opt = Optional.ofNullable(idBoleto);
        
        if (!opt.isPresent()) {
            throw new CRUDException("No se ha especificado un Boleto");
        }
        
        
        String q = "SELECT b FROM Boleto b WHERE b.idBoleto=:idBoleto or b.idBoletoPadre=:idBoletoPadre ORDER BY b.idBoleto";
        Query querie = em.createQuery(q);
        
        
        opt = Optional.ofNullable(idBoletoPadre);
        if (!opt.isPresent()){
            querie.setParameter("idBoleto", idBoleto);
            querie.setParameter("idBoletoPadre", idBoleto);
        }else {
            querie.setParameter("idBoleto", idBoletoPadre);
            querie.setParameter("idBoletoPadre", idBoletoPadre);
        }
        
        return querie.getResultList() ;
        
    }

}
