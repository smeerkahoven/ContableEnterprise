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
import com.agencia.entities.BoletoPlanillaBsp;
import com.agencia.entities.BoletoSearch;
import com.agencia.entities.Cliente;
import com.agencia.entities.ClientePasajero;
import com.agencia.entities.EmisionBoleto;
import com.agencia.entities.FormasPago;
import com.agencia.search.dto.BoletoSearchForm;
import com.configuracion.entities.ContabilidadBoletaje;
import com.contabilidad.entities.AsientoContable;
import com.contabilidad.entities.CargoBoleto;
import com.contabilidad.entities.ComprobanteContable;
import com.contabilidad.entities.IngresoCaja;
import com.contabilidad.entities.IngresoTransaccion;
import com.contabilidad.entities.Moneda;
import com.contabilidad.entities.NotaDebito;
import com.contabilidad.entities.NotaDebitoTransaccion;
import com.contabilidad.remote.ComprobanteRemote;
import com.contabilidad.remote.IngresoCajaRemote;
import com.contabilidad.remote.NotaDebitoRemote;
import com.response.json.boletaje.PlanillaSearchForm;
import com.response.json.boletaje.VentaBoletosSearchJson;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.ComboSelect;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Operacion;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        /*q.setParameter("list", Boleto.Estado.EMITIDO);
        q.setParameter("list", Boleto.Estado.VOID);
        q.setParameter("list", Boleto.Estado.PENDIENTE);*/

        List l = q.getResultList();

        System.out.println("Lista de BOletos : " + l.size());
        return !l.isEmpty();

    }

    public Boleto getBoletoRegistrado(Boleto b) throws CRUDException {

        Query q = em.createNamedQuery("Boleto.findNroBoleto", Boleto.class);
        System.out.println("Numero Boleto :" + b.getNumero());
        q.setParameter("numero", b.getNumero());
        /*q.setParameter("list", Boleto.Estado.EMITIDO);
        q.setParameter("list", Boleto.Estado.VOID);
        q.setParameter("list", Boleto.Estado.PENDIENTE);*/

        List l = q.getResultList();

        System.out.println("Lista de BOletos : " + l.size());

        if (l.size() > 0) {
            return (Boleto) l.get(0);
        }

        return new Boleto();
    }

    /**
     * Actualiza la informacion del Boleto. Ademas de la descripcion de las
     * transacciones de la nota de debito y de los comprobantes contables
     *
     * @param e
     * @throws CRUDException
     */
    @Override
    public void update(Entidad e) throws CRUDException {

        Boleto newBol = (Boleto) e;
        Boleto oldBol = em.find(Boleto.class, newBol.getId());

        Optional op = Optional.ofNullable(oldBol);
        if (op.isPresent()) {

            op = Optional.ofNullable(oldBol.getIdNotaDebitoTransaccion());
            if (op.isPresent()) {
                //actualizamos la descripcion de la Transaccion de la Nota de Debito
                NotaDebitoTransaccion ndt = em.find(NotaDebitoTransaccion.class, oldBol.getIdNotaDebitoTransaccion());
                ndt.setDescripcion(createDescription(newBol));
                em.merge(ndt);
            }

            //Actualiza la descripcion del Ingreso de Caja
            op = Optional.ofNullable(oldBol.getIdIngresoCajaTransaccion());
            if (op.isPresent()) {
                IngresoTransaccion it = em.find(IngresoTransaccion.class, oldBol.getIdIngresoCajaTransaccion());
                it.setDescripcion(createDescription(newBol));
                em.merge(it);
            }

            //Actualiza el comprobante Contable
            op = Optional.ofNullable(oldBol.getIdLibro());
            if (op.isPresent()) {
                ComprobanteContable cc = em.find(ComprobanteContable.class, oldBol.getIdLibro());
                cc.setConcepto(createDescription(newBol));
                em.merge(cc);
            }

        }

        super.update(e); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Crea la descripcion del Boleto de acuerdo a
     *
     * NUMERO LINEA AEREA / NUMERO BOLETO / NOMBRE PASAJERO / RUTA 1/ RUTA 2/
     * RUTA 3/ RUTA 4/ RUTA 5
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    private String createDescription(final Boleto boleto) throws CRUDException {
        try {
            StringBuilder buff = new StringBuilder();

            // DESCRIPCION
            // AEROLINEA/ # Boleto / Pasajero / Rutas
            Aerolinea a = em.find(Aerolinea.class, boleto.getIdAerolinea().getId());

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

            return buff.toString();
        } catch (CRUDException ex) {
            Logger.getLogger(BoletoEJB.class.getName()).log(Level.SEVERE, null, ex);
            throw new CRUDException("No existe la Linea Aerea para crear la descripcion");
        }
    }

    /**
     * Valida la configuracion del Contabilidad del Boleto
     *
     * @param conf
     * @return
     * @throws CRUDException
     */
    @Override
    public boolean validarConfiguracion(ContabilidadBoletaje conf) throws CRUDException {
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

        op = Optional.ofNullable(conf.getOtrosCargosClienteCobrarDebeBs());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Otros Cargos Clientes x Cobrar Debe Bs.");
        }
        op = Optional.ofNullable(conf.getOtrosCargosClienteCobrarDebeUsd());
        if (!op.isPresent()) {
            throw new CRUDException("No existe Cuenta Configurada para Otros Cargos Clientes x Cobrar Debe Usd.");
        }

        return true;
    }

    @Override
    public AerolineaCuenta getAerolineCuenta(Boleto b, String tipo) throws CRUDException {

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

        // En los VOID el idCliente es null
        if (b.getIdCliente() != null) {
            ClientePasajero cp = createClientePasajero(b);
            HashMap<String, Object> parpas = new HashMap<>();
            parpas.put("idCliente", b.getIdCliente().getIdCliente());
            parpas.put("nombrePasajero", b.getNombrePasajero());

            List search = get("ClientePasajero.findPasajero", ClientePasajero.class, parpas);
            if (search.isEmpty()) {
                cp.setIdClientePasajero(insert(cp));
            }//else ya existe el cliente
        }
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

                b.setIdNotaDebito(notaDebito.getIdNotaDebito());
                b.setEstado(Boleto.Estado.EMITIDO);
                insert(b);
                //notaDebito.getNotaDebitoPK().setIdNotaDebito(insert(notaDebito));
                //crea la transaccion de la nota de Debito
                transaccion = ejbNotaDebito.createNotaDebitoTransaccion(b, notaDebito);
                //transaccion.getNotaDebitoTransaccionPK().setIdNotaDebitoTransaccion(insert(transaccion));
                transaccion.setIdNotaDebitoTransaccion(insert(transaccion));
                //3. Registramos el Boleto

                //insert(b);
                // creamos el Comprobante Contable
                comprobanteAsiento = ejbComprobante.createAsientoDiarioBoleto(b);
                comprobanteAsiento.setIdNotaDebito(notaDebito.getIdNotaDebito());
                comprobanteAsiento.setIdLibro(insert(comprobanteAsiento));
                // se crean los asientos de acuerdo a la configuracion.
                b.setIdLibro(comprobanteAsiento.getIdLibro());

                //TotalCancelar
                //totalCancelar = ejbComprobante.crearTotalCancelar(b, comprobanteAsiento, cbconf, a, transaccion.getIdNotaDebitoTransaccion());
                totalCancelar = ejbComprobante.crearTotalCancelar(b, comprobanteAsiento, cbconf, a, transaccion);
                insert(totalCancelar);
                //ejbComprobante.insert(totalCancelar);
                //DiferenciaTotalBoleto
                montoPagarLinea = ejbComprobante.crearMontoPagarLineaAerea(b, comprobanteAsiento, cbconf, av, notaDebito, transaccion);
                //ejbComprobante.insert(montoPagarLinea);
                insert(montoPagarLinea);
                //Comision
                montoComision = ejbComprobante.crearMontoComision(b, comprobanteAsiento, a, ac, notaDebito, transaccion);
                //ejbComprobante.insert(montoComision);
                insert(montoComision);
                //Fee
                montoFee = ejbComprobante.crearMontoFee(b, comprobanteAsiento, cbconf, a, notaDebito, transaccion);
                //ejbComprobante.insert(montoFee);
                insert(montoFee);
                //Descuento
                montoDescuento = ejbComprobante.crearMontoDescuentos(b, comprobanteAsiento, cbconf, a, notaDebito, transaccion);
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

                em.merge(comprobanteAsiento);

                // creamos para las formas de pago
                //Si son Contado o Tarjeta, se crea el Ingreso a Caja y el Comprobante de Ingreso
                if (b.getFormaPago().equals(FormasPago.CONTADO) || b.getFormaPago().equals(FormasPago.TARJETA)) {
                    //Crear Ingreso a Caja
                    ingreso = ejbIngresoCaja.createIngresoCaja(b, notaDebito);
                    ingreso.setIdIngresoCaja(insert(ingreso));
                    b.setIdIngresoCaja(ingreso.getIdIngresoCaja());

                    ingTran = ejbIngresoCaja.createIngresoCajaTransaccion(b, notaDebito, transaccion, ingreso);
                    ingTran.setIdTransaccion(insert(ingTran));
                    b.setIdIngresoCajaTransaccion(ingTran.getIdTransaccion());

                    //Crear Comprobante de Ingreso
                    comprobanteIngreso = ejbComprobante.createComprobante(a, b, c, ComprobanteContable.Tipo.COMPROBANTE_INGRESO);
                    comprobanteIngreso.setIdNotaDebito(notaDebito.getIdNotaDebito());
                    /* if (ingreso.getMoneda().equals(Moneda.EXTRANJERA)) {
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
                    }*/
                    insert(comprobanteIngreso);

                    /**
                     *
                     */
                    // ESTAS TRANSACCIONES PASARLAS. al nuevo metodo de cada uno
                    /*ingTotalCancelarCaja = ejbComprobante.createTotalCancelarIngresoCajaDebe(comprobanteIngreso, cbconf, a, b);
                    ingTotalCancelarCaja.setIdIngresoCajaTransaccion(ingTran.getIdTransaccion());
                    insert(ingTotalCancelarCaja);

                    ingTotalCancelarHaber = ejbComprobante.createTotalCancelarIngresoClienteHaber(comprobanteIngreso, cbconf, a, b);
                    ingTotalCancelarHaber.setIdIngresoCajaTransaccion(ingTran.getIdTransaccion());
                    insert(ingTotalCancelarHaber);*/
                    /**
                     *
                     */
                    //actualizar nota debito
                    em.merge(notaDebito);
                }

                //b.setIdLibro(notaDebito.getIdNotaDebito());
                if (ingTran != null) {
                    b.setIdIngresoCajaTransaccion(ingTran.getIdTransaccion());
                }

                em.merge(b);
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

    @Override
    public boolean enviarAsientoDiario(final Boleto boleto, final NotaDebito nota,
            final ComprobanteContable comprobante, final ContabilidadBoletaje conf) throws CRUDException {

        AsientoContable totalCancelar = null, montoPagarLinea = null,
                montoDescuento = null, montoComision = null, montoFee = null;

        // 1. Obtenemos la configuracion de Ventas y Comision por Aerolinea
        AerolineaCuenta av = getAerolineCuenta(boleto, "V");

        if (av == null) {
            throw new CRUDException("No existe Cuenta asociada a la Aerolinea" + boleto.getIdAerolinea().getNombre() + " para Ventas");
        }

        AerolineaCuenta ac = getAerolineCuenta(boleto, "C");

        if (ac == null) {
            throw new CRUDException("No existe Cuenta asociada a la Aerolinea" + boleto.getIdAerolinea().getNombre() + " para Comisiones");
        }

        NotaDebitoTransaccion ndtrx = em.find(NotaDebitoTransaccion.class, boleto.getIdNotaDebitoTransaccion());
        if (ndtrx == null) {
            throw new CRUDException("No existe la transaccion del boleto " + boleto.getNumero().toString() + " Nota debito Transaccion :" + boleto.getIdNotaDebitoTransaccion().toString());
        }

        // 2. Guardamos el Cliente Pasajero
        saveClientePasajero(boleto);
        // se crean los asientos de acuerdo a la configuracion.

        //TotalCancelar
        //totalCancelar = ejbComprobante.crearTotalCancelar(boleto, comprobante, conf, boleto.getIdAerolinea(), boleto.getIdNotaDebitoTransaccion());
        totalCancelar = ejbComprobante.crearTotalCancelar(boleto, comprobante, conf, boleto.getIdAerolinea(), ndtrx);
        insert(totalCancelar);
        //ejbComprobante.insert(totalCancelar);
        //DiferenciaTotalBoleto
        montoPagarLinea = ejbComprobante.crearMontoPagarLineaAerea(boleto, comprobante, conf, av, nota, ndtrx);
        //ejbComprobante.insert(montoPagarLinea);
        insert(montoPagarLinea);
        //Comision
        montoComision = ejbComprobante.crearMontoComision(boleto, comprobante, boleto.getIdAerolinea(), ac, nota, ndtrx);
        //ejbComprobante.insert(montoComision);
        insert(montoComision);
        //Fee
        montoFee = ejbComprobante.crearMontoFee(boleto, comprobante, conf, boleto.getIdAerolinea(), nota, ndtrx);
        //ejbComprobante.insert(montoFee);
        insert(montoFee);
        //Descuento
        montoDescuento = ejbComprobante.crearMontoDescuentos(boleto, comprobante, conf, boleto.getIdAerolinea(), nota, ndtrx);
        //ejbComprobante.insert(montoDescuento);
        insert(montoDescuento);

        return false;

    }

    @Override
    public boolean enviarAsientoDiario(final CargoBoleto cargo, final NotaDebito nota,
            final ComprobanteContable comprobante, final ContabilidadBoletaje conf) throws CRUDException {

        AsientoContable clientexCobrar = null, comisionMayorista = null,
                comisionAgencia = null, comisionCounter = null;

        NotaDebitoTransaccion trx = em.find(NotaDebitoTransaccion.class, cargo.getIdNotaDebitoTransaccion());

        // se crean los asientos de acuerdo a la configuracion.
        clientexCobrar = ejbComprobante.crearClienteXCobrar(cargo, nota, comprobante, conf, trx);
        //clientexCobrar = ejbComprobante.crearClienteXCobrar(cargo, nota, comprobante, conf, cargo.getIdNotaDebitoTransaccion());
        insert(clientexCobrar);

        //comisionMayorista = ejbComprobante.crearOperadorMayotistaXPagar(cargo, nota, comprobante, cargo.getIdNotaDebitoTransaccion());
        comisionMayorista = ejbComprobante.crearOperadorMayotistaXPagar(cargo, nota, comprobante, trx);
        insert(comisionMayorista);

        //comisionAgencia = ejbComprobante.crearComisionAgenciaHaber(cargo, nota, comprobante, cargo.getIdNotaDebitoTransaccion());
        comisionAgencia = ejbComprobante.crearComisionAgenciaHaber(cargo, nota, comprobante, trx);
        insert(comisionAgencia);

        //comisionCounter = ejbComprobante.crearComisionCounterHaber(cargo, nota, comprobante, cargo.getIdNotaDebitoTransaccion());
        comisionCounter = ejbComprobante.crearComisionCounterHaber(cargo, nota, comprobante, trx);
        insert(comisionCounter);

        return false;

    }

    @Override
    public boolean crearTransaccionIngresoCaja(final Boleto boleto, final NotaDebito nota,
            final ComprobanteContable comprobante, final IngresoCaja caja, final NotaDebitoTransaccion notaTran) throws CRUDException {

        IngresoTransaccion transaccion = ejbIngresoCaja.createIngresoCajaTransaccion(boleto, nota, notaTran, caja);
        transaccion.setIdTransaccion(insert(transaccion));
        boleto.setIdIngresoCajaTransaccion(transaccion.getIdTransaccion());

        return true;
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
    public List getBoletos(BoletoSearchForm search) throws CRUDException {

        Optional op = Optional.ofNullable(search.getIdEmpresa());
        if (!op.isPresent()) {
            throw new CRUDException("Debe enviar el parametro de Id Empresa");
        }
        String q = "SELECT b FROM Boleto b WHERE b.idEmpresa=:idEmpresa ";

        op = Optional.ofNullable(search.getNumeroBoleto());
        if (op.isPresent()) {
            q += " AND b.numero=:numero";
        }

        op = Optional.ofNullable(search.getAerolinea());
        if (op.isPresent()) {
            q += " AND b.idAerolinea=:idAerolinea";
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            q += " AND b.fechaEmision>=:fechaI ";
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            q += " AND b.fechaEmision<=:fechaF";
        }

        Query query = em.createQuery(q, BoletoSearch.class);

        op = Optional.ofNullable(search.getNumeroBoleto());
        if (op.isPresent()) {
            query.setParameter("numero", search.getNumeroBoleto());
        }

        op = Optional.ofNullable(search.getAerolinea());
        if (op.isPresent()) {
            query.setParameter("idAerolinea", new Aerolinea(search.getAerolinea()));
        }

        op = Optional.ofNullable(search.getFechaInicio());
        if (op.isPresent()) {
            query.setParameter("fechaI", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        }

        op = Optional.ofNullable(search.getFechaFin());
        if (op.isPresent()) {
            query.setParameter("fechaF", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        }

        query.setParameter("idEmpresa", search.getIdEmpresa());

        return query.getResultList();

    }

    @Override
    public Boleto saveBoletoVoid(Boleto b, NotaDebito n, String usuario) throws CRUDException {
        Optional op;
        Aerolinea a = em.find(Aerolinea.class, b.getIdAerolinea().getIdAerolinea());

        // Revisamos que el boleto no este registrado
        if (isBoletoRegistrado(b)) {
            throw new CRUDException("El Numero de Boleto ya ha sido registrado");
        }

        //4. Obtenemos la configuracion del Boleto para guardar en el comprobanteAsiento
        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            b.setMoneda(Moneda.EXTRANJERA);
        } else {
            b.setMoneda(Moneda.NACIONAL);
        }

        b.setEstado(Boleto.Estado.PENDIENTE);

        ejbNotaDebito.asociarBoletoNotaDebito(n, b.getIdBoleto(), usuario);

        insert(b);

        return b;
    }

    @Override
    public Boleto saveBoletoMultiple(Boleto b) throws CRUDException {

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

        if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
            b.setMoneda(Moneda.EXTRANJERA);
        } else {
            b.setMoneda(Moneda.NACIONAL);
        }

        b.setEstado(Boleto.Estado.PENDIENTE);

        saveClientePasajero(b);

        insert(b);

        return b;

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
        if (!opt.isPresent()) {
            querie.setParameter("idBoleto", idBoleto);
            querie.setParameter("idBoletoPadre", idBoleto);
        } else {
            querie.setParameter("idBoleto", idBoletoPadre);
            querie.setParameter("idBoletoPadre", idBoletoPadre);
        }

        return querie.getResultList();

    }

    @Override
    public Boleto anularBoleto(Integer boleto) throws CRUDException {

        Boleto boletoAnular = em.find(Boleto.class, boleto);

        Optional op = Optional.ofNullable(boletoAnular);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el Boleto");
        }

        if (boletoAnular.getEstado().equals(Boleto.Estado.ANULADO)) {
            throw new CRUDException("El Boleto ya se encuentra anulado");
        }

        //Si el Boleto esta Emitido se deben dar de bajas su contabilidad
        boletoAnular.setEstado(Boleto.Estado.ANULADO);
        em.merge(boletoAnular);

        return boletoAnular;
    }

    @Override
    public CargoBoleto anularCargo(Integer cargo) throws CRUDException {

        CargoBoleto cargoBoleto = em.find(CargoBoleto.class, cargo);

        Optional op = Optional.ofNullable(cargoBoleto);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el Cargo de Boleto");
        }

        if (cargoBoleto.getEstado().equals(Boleto.Estado.ANULADO)) {
            throw new CRUDException("El Cargo del Boleto ya se encuentra anulado");
        }

        //Si el Boleto esta Emitido se deben dar de bajas su contabilidad
        cargoBoleto.setEstado(Boleto.Estado.ANULADO);
        em.merge(cargoBoleto);

        return cargoBoleto;
    }

    /**
     * Realiza la anulacion del Boleto. Si el boleto Es Simple o Multiple puede
     * anular Si el boleto es VOID
     *
     * @param boleto
     * @return
     * @throws CRUDException
     */
    @Override
    public Boleto anular(Boleto boleto) throws CRUDException {

        Boleto boletoAnular = em.find(Boleto.class, boleto.getIdBoleto());

        Optional op = Optional.ofNullable(boletoAnular);

        if (!op.isPresent()) {
            throw new CRUDException("No existe el Boleto");
        }

        if (boletoAnular.getEstado().equals(Boleto.Estado.ANULADO)) {
            throw new CRUDException("El Boleto ya se encuentra anulado");
        }

        //Si el Boleto esta Emitido se deben dar de bajas su contabilidad
        if (boletoAnular.getEstado().equals(Boleto.Estado.EMITIDO)) {
            System.out.println("Anulando Boleto:" + boleto.getIdBoleto());
            System.out.println("Anulando Boleto NotaDebito:" + boleto.getIdNotaDebito());
            System.out.println("Anulando Boleto IngresoCaja :" + boleto.getIdIngresoCaja());
            boletoAnular.setEstado(Boleto.Estado.ANULADO);
            em.merge(boletoAnular);

            //anulamos los asientos contables de los asientos. (AD y CI)
            ejbComprobante.anularAsientosContables(boletoAnular);

            //anulamos las transacciones de la nota de debito
            //El proceso de Anular la transaccion de la Nota de Debito
            //llama internamente en el Procedimiento Almacenado a un proceso de anulacion 
            //las transacciones del Ingreso de Caja. esto debido a mejorar el proceso y no 
            //realizar un doble barrido en la tabla de transacciones de 
            //la nota dede
            ejbNotaDebito.anularTransaccion(boletoAnular);

            //anulamos las transacciones del Ingreso de Caja
            //ejbIngresoCaja.anularTransaccion(boleto) ;
            // si esta en Pendiente solo debe cambiar el estado
        } else if (boletoAnular.getEstado().equals(Boleto.Estado.PENDIENTE)
                || boletoAnular.getEstado().equals(Boleto.Estado.CANCELADO)) {
            boletoAnular.setEstado(Boleto.Estado.ANULADO);
            em.merge(boletoAnular);
            //Si el Boleto es Void, se puede volver a ingresar el boleto.
        }
        return boletoAnular;

    }

    @Override
    public Boleto isBoletoRegistradoOrigen(Boleto b) throws CRUDException {

        Query q = em.createNamedQuery("Boleto.findNroBoletoE", Boleto.class);
        q.setParameter("numero", b.getNumero());

        List l = q.getResultList();

        if (l.isEmpty()) {
            return new Boleto(0);
        }

        return (Boleto) l.get(0);

    }

    @Override
    public List<Boleto> getBoletosAmadeusCargados(Integer idEmpresa) throws CRUDException {

        Query q = em.createNamedQuery("Boleto.getAllAmadeusAutomaticos", Boleto.class);
        q.setParameter("idEmpresa", idEmpresa);

        List l = q.getResultList();

        return l;
    }

    @Override
    public List<Boleto> getBoletosSabreCargados(Integer idEmpresa) throws CRUDException {
        Query q = em.createNamedQuery("Boleto.getAllSabreAutomaticos", Boleto.class);
        q.setParameter("idEmpresa", idEmpresa);

        List l = q.getResultList();

        return l;
    }

    @Override
    public Entidad get(Entidad e) throws CRUDException {
        Boleto b = em.find(Boleto.class, e.getId());

        Optional op = Optional.ofNullable(b);
        if (op.isPresent()) {
            return b;
        }

        return null;
    }

    @Override
    public int insertarBoleto(Boleto boleto) throws CRUDException {

        if (isBoletoRegistrado(boleto)) {
            throw new CRUDException("El Numero de Boleto ya ha sido registrado");
        }

        NotaDebito n = em.find(NotaDebito.class, boleto.getIdNotaDebito());
        Optional op = Optional.ofNullable(n);
        if (!op.isPresent()) {
            throw new CRUDException("No existe la nota de Debito %s en la Base de datos.".replace("%s", boleto.getIdNotaDebito().toString()));
        }
        // Se guarda el Boleto
        boleto.setIdBoleto(insert(boleto));

        saveClientePasajero(boleto);
        //se asocia el Boleto con la Transaccion
        ejbNotaDebito.asociarBoletoNotaDebitoManual(n, boleto);

        //actualizamos los montos de la Nota de debito
        ejbNotaDebito.actualizarMontosNotaDebito(n.getIdNotaDebito());

        return Operacion.REALIZADA;

    }

    @Override
    public void eliminar(Boleto b) throws CRUDException {

        Boleto fromDb = em.find(Boleto.class, b.getIdBoleto());
        Optional op = Optional.ofNullable(fromDb);

        if (!op.isPresent()) {
            throw new CRUDException("No se econtro el Boleto %s.".replace("%s", b.getNumero().toString()));
        }

        em.remove(fromDb);

    }

    @Override
    public int updateBoleto(Boleto boleto) throws CRUDException {

        Boleto boletoBd = getBoletoRegistrado(boleto);

        if (boletoBd.getIdBoleto() != null) {
            if (!Objects.equals(boletoBd.getIdBoleto(), boleto.getIdBoleto())) {
                throw new CRUDException("El Numero de Boleto ya ha sido registrado");
            }
        }

        em.merge(boleto);

        NotaDebito n = (NotaDebito) ejbNotaDebito.get(new NotaDebito(boleto.getIdNotaDebito()));
        // Se guarda el Boleto

        saveClientePasajero(boleto);

        //se asocia el Boleto con la Transaccion
        ejbNotaDebito.updateBoletoNotaDebito(boleto, n);

        //actualizamos los montos de la Nota de debito
        ejbNotaDebito.actualizarMontosNotaDebito(n.getIdNotaDebito());

        return Operacion.REALIZADA;

    }

    @Override
    public List<BoletoPlanillaBsp> getPlanillaBsp(PlanillaSearchForm search) throws CRUDException {

        if (search.getIdEmpresa() == null) {
            throw new CRUDException("El parametro Id Empresa es necesario");
        }
        if (search.getTipoCupon()== null) {
            throw new CRUDException("El parametro Tipo es necesario");
        }

        if (search.getFechaInicio() == null) {
            throw new CRUDException("El parametro Fecha Inicio es necesario");
        }

        if (search.getFechaFin() == null) {
            throw new CRUDException("El parametro Fecha Fin es necesario");
        }

        Query q = em.createNamedQuery("Boleto.getPlanillaBsp");
        q.setParameter("1", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        q.setParameter("2", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        q.setParameter("3", search.getIdEmpresa());
        q.setParameter("4", search.getTipoCupon());

        List<BoletoPlanillaBsp> l = q.getResultList();

        return l;

    }

    @Override
    public List<BoletoPlanillaBsp> getReporteVentas(VentaBoletosSearchJson search) throws CRUDException {

        if (search.getTipoCupon()== null) {
            throw new CRUDException("El parametro Id Empresa es necesario");
        }

        if (search.getIdEmpresa() == null) {
            throw new CRUDException("El parametro Id Empresa es necesario");
        }

        if (search.getFechaInicio() == null) {
            throw new CRUDException("El parametro Fecha Inicio es necesario");
        }

        if (search.getFechaFin() == null) {
            throw new CRUDException("El parametro Fecha Fin es necesarioa");
        }

        if (search.getIdAerolinea() == null) {
            throw new CRUDException("El parametro Linea Aerea es necesario.");
        }

        Query q = em.createNamedQuery("Boleto.getReporteVentas");
        q.setParameter("1", search.getIdEmpresa());
        q.setParameter("2", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        q.setParameter("3", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        q.setParameter("4", search.getIdAerolinea().getId());
        q.setParameter("5", search.getTipoCupon());

        List<BoletoPlanillaBsp> l = q.getResultList();

        return l;

    }

    @Override
    public List<BoletoPlanillaBsp> getReporteComisionCliente(VentaBoletosSearchJson search) throws CRUDException {
        if (search.getIdEmpresa() == null) {
            throw new CRUDException("El parametro Id Empresa es necesario");
        }

        if (search.getFechaInicio() == null) {
            throw new CRUDException("El parametro Fecha Inicio es necesario");
        }

        if (search.getFechaFin() == null) {
            throw new CRUDException("El parametro Fecha Fin es necesarioa");
        }

        if (search.getIdCliente()== null) {
            throw new CRUDException("El parametro Cliente es necesario.");
        }

        Query q = em.createNamedQuery("Boleto.getReporteComisionCliente");
        q.setParameter("1", search.getIdEmpresa());
        q.setParameter("2", DateContable.toLatinAmericaDateFormat(search.getFechaInicio()));
        q.setParameter("3", DateContable.toLatinAmericaDateFormat(search.getFechaFin()));
        q.setParameter("4", search.getIdCliente().getId());
        q.setParameter("5", search.getTipoCupon());

        List<BoletoPlanillaBsp> l = q.getResultList();

        return l;
    }

}
