/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.ejb;

import com.agencia.control.remote.BoletoRemote;
import com.agencia.entities.Aerolinea;
import com.agencia.entities.Aeropuerto;
import com.agencia.entities.Boleto;
import com.configuracion.entities.ArchivoBoleto;
import com.configuracion.entities.CambioDolar;
import com.configuracion.entities.LogArchivos;
import com.configuracion.entities.Parametros;
import com.configuracion.entities.Pasajero;
import com.configuracion.remote.CambioRemote;
import com.configuracion.remote.ParametrosRemote;
import com.configuracion.remote.SabreFileRemote;
import com.contabilidad.entities.Moneda;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Contabilidad;
import com.seguridad.utils.DateContable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author xeio
 */
@Stateless
public class SabreFileEJB extends FacadeEJB implements SabreFileRemote {

    private static final String CARRIER = "\n";
    private final static String M01 = "M01";    // VALID TICKET
    private final static String M05 = "M05";    // VOID TICKET
    private final static String M1 = "M1";    // Linea Aerea, Impuestos, Total
    private final static String M5 = "M5";    // Linea Aerea, Ticket Number, Importes
    private final static String M2 = "M2";    // Fecha Viaje, Impuestos Mejor de la M5
    private final static String M3 = "M3";    // Rutas

    private final static String D = "D";
    private final static String F = "F";
    private final static String X = "X";
    private final static String BLANK = " ";
    private final static String NETO = "neto";
    private final static String COMISION = "comision";
    private final static String TOTAL_TAXES = "TOTAL TAXES";
    private final static String USD = "USD";

    private static final String CANT_PROCESS_AIRLINE_EXISTS = "No se puede procesar el archivo <strong><file></strong>. No existe el codigo de Aerolinea : <strong><code></strong>.";
    private static final String FILE_M0_UNKNOW_PROCESSED = "Se proceso el archivo<strong><file></strong> el cual tenia una cabecera diferente a M05 o M01.";
    private static final String FILE_MO_PROCESSED = "Se proceso el archivo<strong><file></strong> SABRE el cual genero el Numero de Boleto <strong><numero></strong> como <strong>VALIDO</strong>";
    private static final String FILE_M0_NUMBER_TICKET_EXIST = "Se proceso el archivo<strong><file></strong> SABRE, pero genero error ya que el numero de boleto <strong><numero></strong> ya fue ingresado";
    private static final String FILE_M0_NO_DOLAR_CAMBIO = "Se proceso el archivo<strong><file></strong> SABRE, pero genero error <strong>No se definio el tipo de cambio de dolara para hoy</strong>";
    private static final String FILE_VOID_PROCESSED = "Se proceso el archivo<strong><file></strong> SABRE el cual genero el Numero de Boleto <strong><numero></strong> como VOID";

    private Aerolinea aerolinea = null;
    private String fechaEmision = null, fechaViaje = null, fechaVoid = null, tipoCupon = null, moneda = null, tipoBoleto = Boleto.Tipo.SABRE;
    private Aeropuerto ruta1 = null, ruta2 = null, ruta3 = null, ruta4 = null, ruta5 = null;
    private BigDecimal importeNeto, totalBoleto;
    private HashMap<String, Pasajero> pasajeros = new HashMap<>();
    @EJB
    private BoletoRemote ejbBoleto;

    @EJB
    private CambioRemote ejbCambio;

    @EJB
    protected ParametrosRemote ejbParametros;

    @Override
    public boolean procesarArchivo(final ArchivoBoleto archivoBoleto) throws CRUDException {
        System.out.println("Procesando Archivo:" + archivoBoleto.getNombreArchivo());

        pasajeros.clear();
        ruta1 = null;
        ruta2 = null;
        ruta3 = null;
        ruta4 = null;
        ruta5 = null;
        aerolinea = null;

        fechaEmision = null;
        fechaViaje = null;
        importeNeto = null;
        totalBoleto = null;

        String[] line = archivoBoleto.getContenido().split(CARRIER);
        int i = 0;
        boolean header = true;
        boolean void_ticket = false;
        boolean valid_ticket = false;
        boolean invalid_ticket = false;

        while (i < line.length - 1) {
            //El primer parametro debe SER AADDMMMHHMMM01
            String lineColumn = line[i];

            //FIN DE ARCHIVO
            if (header) {
                //analizamos la cabecera del archivo
                if (!void_ticket || !valid_ticket) {
                    String tck = lineColumn.substring(11, 14);
                    if (tck.equals(M01)) {
                        valid_ticket = true;
                    } else if (tck.equals(M05)) {
                        void_ticket = true;
                    } else {
                        invalid_ticket = true;
                    }

                    fechaEmision = lineColumn.substring(2, 7);

                    fechaEmision += LocalDate.now().getYear();
                    fechaEmision = DateContable.fromFormatToOtherFormat(fechaEmision, DateContable.AMADEUS_D_TAG_H_DATE, DateContable.LATIN_AMERICA_FORMAT);
                }
                header = false;
            } else if (valid_ticket) {
                if (lineColumn.length() > 2) {

                    // Tipo Cupon
                    String token = lineColumn.substring(0, 2);
                    if (token.equals(M2)) {

                        String value = lineColumn.substring(18, 19);
                        if (value.equals(X)) {
                            tipoCupon = Boleto.Cupon.INTERNACIONAL;
                        } else if (value.equals(BLANK)) {
                            tipoCupon = Boleto.Cupon.NACIONAL;
                        } else {
                            crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), "", "No se puede procesar el archivo. No se enconto el valor esperado en la posicion M2 Col 18 (tipo cupon)", LogArchivos.Tipo.ERROR);
                            return false;
                        }
                    } else if (token.equals(M3)) {
                        if (lineColumn.length() > 41) {
                            String r1 = lineColumn.substring(18, 21);
                            String r2 = lineColumn.substring(38, 41);
                            if (ruta1 == null) {
                                ruta1 = new Aeropuerto();
                                ruta1.setIdAeropuerto(r1);
                            }
                            if (ruta2 == null) {
                                ruta2 = new Aeropuerto();
                                ruta2.setIdAeropuerto(r2);
                            } else if (ruta3 == null) {
                                ruta3 = new Aeropuerto();
                                ruta3.setIdAeropuerto(r2);
                            } else if (ruta4 == null) {
                                ruta4 = new Aeropuerto();
                                ruta4.setIdAeropuerto(r2);
                            } else if (ruta5 == null) {
                                ruta5 = new Aeropuerto();
                                ruta5.setIdAeropuerto(r2);
                            }

                            Optional op = Optional.ofNullable(aerolinea);

                            if (!op.isPresent()) {
                                aerolinea = findAerolineaByNumero(lineColumn.substring(58, 60), archivoBoleto);
                                op = Optional.ofNullable(aerolinea);
                                if (!op.isPresent()) {
                                    return false;
                                }
                            }

                            if (fechaViaje == null) {
                                fechaViaje = new String();
                                //OB    0633 B B 07SEP0750 0835 07SEP
                                //FORMATO DDMMM
                                fechaViaje = lineColumn.substring(9, 14);

                                fechaViaje += LocalDate.now().getYear();
                                fechaViaje = DateContable.fromFormatToOtherFormat(fechaViaje, DateContable.SABRE_M3_DATE, DateContable.LATIN_AMERICA_FORMAT);
                            }
                        }

                    } else if (token.equals(M5)) {
                        Pasajero p = new Pasajero();
                        p.setId(lineColumn.substring(2, 4)); // M501

                        //Buscar aerolina por el Codigo AM, CM
                        String values[] = lineColumn.substring(11).split("/");

                        // 0 Ticket Number
                        p.setNumero(new Long(values[0]));
                        // 1 Moneda 
                        moneda = values[2].substring(0, 3);
                        if (moneda.equals(USD)) {
                            moneda = Moneda.EXTRANJERA;
                        } else {
                            moneda = Moneda.NACIONAL;
                        }
                        // 2 Importe Neto
                        String neto = values[2].substring(3).replace(BLANK, "");
                        p.getImportes().put(NETO, new BigDecimal(neto));
                        // 3 Tax
                        p.getImportes().put(TOTAL_TAXES, new BigDecimal(values[3].replace(BLANK, "")));
                        // 4 comision
                        p.getImportes().put(COMISION, new BigDecimal(values[1].replace(BLANK, "")));
                        // 5 nombre pasajero
                        String nombre = values[5].substring(values[5].indexOf("."));
                        if (nombre.length() > 2) {
                            p.setNombrePasajero(nombre.substring(2));
                        } else {
                            p.setNombrePasajero("SIN NOMBRE");
                        }
                        // 6 Tipo de Cupon
                        if (values[7].equals(D)) {
                            p.setTipoCupon(Boleto.Cupon.NACIONAL);
                        } else if (values[7].equals(F)) {
                            p.setTipoCupon(Boleto.Cupon.INTERNACIONAL);
                        }

                        pasajeros.put(p.getId(), p);
                    }
                    //Moneda
                    /*token = lineColumn.substring(34, 37);
                    if (token.equals(Moneda.EXTRANJERA)) {
                        moneda = Moneda.EXTRANJERA;
                    }else {
                        moneda = Moneda.NACIONAL;
                    }*/

                    //01 o 02 o 03 ... 0n
                    /*Pasajero p =new Pasajero();
                    p.setId(lineColumn.substring(2,4));
                    // Importe Neto
                    token = lineColumn.substring(38,46) ;
                    importeNeto = new BigDecimal(token);
                    
                    p.getImportes().put(NETO, importeNeto);*/
                    // TAX 1
                }
                i++;
            } else if (void_ticket) {
                // solo buscamos informacion del ticket VOID
                //TAG Longitud 3
                if (lineColumn.length() > 2) {
                    fechaVoid = lineColumn.substring(2, 7);
                    fechaVoid += LocalDate.now().getYear();
                    fechaVoid = DateContable.fromFormatToOtherFormat(fechaVoid, DateContable.SABRE_M3_DATE, DateContable.LATIN_AMERICA_FORMAT);

                    aerolinea = findAerolineaByIata(lineColumn.substring(26, 29), archivoBoleto);

                    Pasajero p = new Pasajero();
                    p.setNumero(new Long(lineColumn.substring(29, 39)));

                    pasajeros.put("01", p);
                }
                i++;
            } else if (invalid_ticket) {
                // el archivo no es ni M05 ni M01
                i = line.length + 1;
            }
        }

        //Verificamos que archivo proceso
        if (valid_ticket) {
            //creamos el boleto AMADEUS AM
            return createBoletoSabre(archivoBoleto);
        } else if (void_ticket) {
            //creamos el boleto AMADEUS VOID AV
            createBoletoVoidSabre(archivoBoleto);

        } else if (invalid_ticket) {
            crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), "",
                    FILE_M0_UNKNOW_PROCESSED, LogArchivos.Tipo.INFO);
        }
        return true;

    }

    private Aerolinea findAerolineaByIata(String code, final ArchivoBoleto archivoBoleto) throws CRUDException {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("iata", code);
        List l = get("Aerolinea.findByIata", Aerolinea.class, parameters);

        if (l.isEmpty()) {
            String msg = CANT_PROCESS_AIRLINE_EXISTS.replace("<file>", archivoBoleto.getNombreArchivo());
            msg = msg.replace("<code>", code);
            crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), "", msg, LogArchivos.Tipo.ERROR);

        } else {
            return (Aerolinea) l.get(0);
        }
        return null;
    }

    private Aerolinea findAerolineaByNumero(String code, final ArchivoBoleto archivoBoleto
    ) throws CRUDException {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("numero", code);
        List l = get("Aerolinea.findByNumero", Aerolinea.class, parameters);

        if (l.isEmpty()) {
            String msg = CANT_PROCESS_AIRLINE_EXISTS.replace("<file>", archivoBoleto.getNombreArchivo());
            msg = msg.replace("<code>", code);
            crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), "", msg, LogArchivos.Tipo.ERROR);

        } else {
            return (Aerolinea) l.get(0);
        }
        return null;
    }

    private void crearLogArchivo(String nombreArchivo, String tipo, String numeroBoleto, String mensaje, String logTipo) throws CRUDException {
        String msg = mensaje.replace("<file>", nombreArchivo);
        msg = msg.replace("<numero>", numeroBoleto);
        LogArchivos log = new LogArchivos();
        log.setFecha(DateContable.getCurrentDate());
        log.setMensaje(msg);
        log.setNombreArchivo(nombreArchivo);
        log.setTipo(logTipo);
        if (numeroBoleto.length() > 0) {
            log.setNumeroBoleto(new BigInteger(numeroBoleto));
        }
        log.setTipoArchivo(tipo);
        log.setUsuario("system");

        insert(log);
    }

    private boolean createBoletoSabre(final ArchivoBoleto archivo) {
        try {
            Parametros porcentajeComision = (Parametros) ejbParametros.get(new Parametros(Parametros.PORCENTAJE_COMISION));
            CambioDolar diario = null;
            for (Map.Entry<String, Pasajero> entry : pasajeros.entrySet()) {
                try {
                    if (ejbBoleto.isBoletoRegistrado(new Boleto(entry.getValue().getNumero()))) {
                        crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(), entry.getValue().getNumero().toString(),
                                FILE_M0_NUMBER_TICKET_EXIST, LogArchivos.Tipo.ERROR);
                        return false;
                    }

                    diario = ejbCambio.get(fechaEmision, "CambioDolar.findFecha");

                    if (diario == null) {
                        List l = ejbCambio.get();
                        if (l.isEmpty()) {
                            crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(), entry.getValue().getNumero().toString(),
                                    FILE_M0_NO_DOLAR_CAMBIO, LogArchivos.Tipo.ERROR);
                            return false;
                        } else {
                            //Tomamos
                            diario = (CambioDolar) l.get(l.size() - 1);
                        }

                    }

                } catch (CRUDException ex) {
                    Logger.getLogger(AmadeusFileEJB.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

                Boleto b = new Boleto();
                b.setTipoBoleto(Boleto.Tipo.SABRE);
                b.setEstado(Boleto.Estado.CARGADO_AUTOMATICO);
                b.setIdArchivo(archivo);
                b.setIdAerolinea(aerolinea);
                b.setFactorCambiario(diario.getValor());
                b.setIdEmpresa(1);
                b.setEmision(Boleto.Emision.TKT);
                b.setTipoCupon(entry.getValue().getTipoCupon());
                b.setNumero(entry.getValue().getNumero());
                b.setIdRuta1(ruta1.getIdAeropuerto());
                b.setIdRuta2(ruta2.getIdAeropuerto());
                b.setIdRuta3(ruta3 != null ? ruta3.getIdAeropuerto() : null);
                b.setIdRuta4(ruta4 != null ? ruta4.getIdAeropuerto() : null);
                b.setIdRuta5(ruta5 != null ? ruta5.getIdAeropuerto() : null);
                b.setNombrePasajero(entry.getValue().getNombrePasajero());
                b.setFechaEmision(DateContable.toLatinAmericaDateFormat(fechaEmision));
                b.setFechaViaje(DateContable.toLatinAmericaDateFormat(fechaViaje));
                //todo factor cambiario
                b.setEstado(Boleto.Estado.CARGADO_AUTOMATICO);
                b.setFechaInsert(DateContable.getCurrentDate());
                b.setIdUsuarioCreador("system");
                b.setGestion(DateContable.getPartitionDateInt(fechaEmision));
                b.setMoneda(moneda);
                b.setImporteNeto(importeNeto);

                totalBoleto = new BigDecimal(BigInteger.ZERO);
                for (Map.Entry<String, BigDecimal> imp : entry.getValue().getImportes().entrySet()) {
                    if (imp.getKey().equals(NETO)) {
                        b.setImporteNeto(imp.getValue());
                    } else if (imp.getKey().equals(COMISION)) {
                        //b.setMontoComision(imp.getValue()); PREFERIMOS JALAR LA COMISION DE LA CONFIGURACION DE LA LINEA AEREA
                    } else if (imp.getKey().equals("BO")) {
                        b.setImpuestoBob(imp.getValue());
                    } else if (imp.getKey().equals("QM")) {
                        b.setImpuestoQm(imp.getValue());
                    } else if (b.getImpuesto1() == null) {
                        b.setImpuesto1nombre(imp.getKey());
                        b.setImpuesto1(imp.getValue());
                    } else if (b.getImpuesto2() == null) {
                        b.setImpuesto2nombre(imp.getKey());
                        b.setImpuesto2(imp.getValue());
                    } else if (b.getImpuesto3() == null) {
                        b.setImpuesto3nombre(imp.getKey());
                        b.setImpuesto2(imp.getValue());
                    } else if (b.getImpuesto4() == null) {
                        b.setImpuesto4nombre(imp.getKey());
                        b.setImpuesto2(imp.getValue());
                    } else if (b.getImpuesto5() == null) {
                        b.setImpuesto5nombre(imp.getKey());
                        b.setImpuesto2(imp.getValue());
                    }

                    totalBoleto = totalBoleto.add(imp.getValue());

                }

                b.setTotalBoleto(totalBoleto);
                
                //if (b.getMontoComision() == null) {
                if (b.getTipoCupon().equals(Boleto.Cupon.INTERNACIONAL)) {
                    if (aerolinea.getComisionPromIntTipo() != null) {
                        Double valor = 0d;
                        if (aerolinea.getComisionPromIntTipo().equals(Aerolinea.Comision.NETO)) {
                            valor = b.getImporteNeto().doubleValue();
                        } else if (aerolinea.getComisionPromIntTipo().equals(Aerolinea.Comision.TOTAL)) {
                            valor = b.getTotalBoleto().doubleValue();
                        }

                        if (aerolinea.getComisionPromInt() != null) {
                            Double comision = aerolinea.getComisionPromInt().doubleValue();
                            Double total = (valor * comision) / 100;

                            b.setComision(aerolinea.getComisionPromInt());
                            b.setMontoComision(new BigDecimal(total).setScale(Contabilidad.VALOR_DECIMAL_2, RoundingMode.HALF_DOWN));

                            if (aerolinea.getIvaItComision()) {
                                if (porcentajeComision != null) {
                                    Double porcentaje = Double.parseDouble(porcentajeComision.getValor());
                                    Double totalComisonIva = ((total * porcentaje) / 100) + total;
                                    if (aerolinea.getRoundComisionUsd()) {
                                        b.setMontoComision(new BigDecimal(Math.round( totalComisonIva)).setScale(Contabilidad.VALOR_DECIMAL_0, RoundingMode.HALF_DOWN));
                                    } else {
                                        b.setMontoComision(new BigDecimal(totalComisonIva).setScale(Contabilidad.VALOR_DECIMAL_2, RoundingMode.HALF_DOWN));
                                    }
                                }

                            }
                        }
                    }
                } else if (b.getTipoCupon().equals(Boleto.Cupon.NACIONAL)) {
                    if (aerolinea.getComisionPromNacTipo() != null) {
                        Double valor = 0d;
                        if (aerolinea.getComisionPromNacTipo().equals(Aerolinea.Comision.NETO)) {
                            valor = b.getImporteNeto().doubleValue();
                        } else if (aerolinea.getComisionPromNacTipo().equals(Aerolinea.Comision.TOTAL)) {
                            valor = b.getTotalBoleto().doubleValue();
                        }

                        if (aerolinea.getComisionPromNac() != null) {
                            Double comision = aerolinea.getComisionPromNac().doubleValue();
                            Double total = (valor * comision) / 100;

                            b.setComision(aerolinea.getComisionPromNac());
                            b.setMontoComision(new BigDecimal(total).setScale(Contabilidad.VALOR_DECIMAL_2, RoundingMode.HALF_DOWN));

                            if (aerolinea.getIvaItComision()) {
                                if (porcentajeComision != null) {
                                    Double porcentaje = Double.parseDouble(porcentajeComision.getValor());
                                    Double totalComisonIva = ((total * porcentaje) / 100) + total;
                                    if (aerolinea.getRoundComisionBob()) {
                                        b.setMontoComision(new BigDecimal(Math.round( totalComisonIva)).setScale(Contabilidad.VALOR_DECIMAL_0, RoundingMode.HALF_DOWN));
                                    } else {
                                        b.setMontoComision(new BigDecimal(totalComisonIva).setScale(Contabilidad.VALOR_DECIMAL_2, RoundingMode.HALF_DOWN));
                                    }
                                }

                            }
                        }
                    }
                }
                //}
                try {
                    if (b.getMontoComision() != null){
                        Double comision = b.getMontoComision().doubleValue() ;
                        Double totalBoleto = b.getTotalBoleto().doubleValue() ;
                        
                        Double montoPagarLinea = totalBoleto - comision ;
                        b.setMontoPagarLineaAerea(new BigDecimal(montoPagarLinea));
                    }
                    
                    b.setTotalMontoCobrar(totalBoleto);
                    //creamos el boleto
                    insert(b);

                    crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(),
                            entry.getValue().getNumero().toString(), FILE_MO_PROCESSED, LogArchivos.Tipo.INFO);
                } catch (CRUDException ex) {
                    Logger.getLogger(AmadeusFileEJB.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        } catch (CRUDException ex) {
            Logger.getLogger(SabreFileEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private void createBoletoVoidSabre(ArchivoBoleto archivo) {
        try {
            Pasajero p = new Pasajero();
            for (Map.Entry<String, Pasajero> e : pasajeros.entrySet()) {

                p = (Pasajero) e.getValue();

                Boleto b = new Boleto();
                b.setIdArchivo(archivo);
                b.setIdAerolinea(aerolinea);
                b.setIdEmpresa(1);
                b.setTipoCupon(null);
                b.setEstado(Boleto.Estado.CARGADO_AUTOMATICO);
                b.setEmision(Boleto.Emision.TKT);
                b.setFechaInsert(DateContable.getCurrentDate());
                b.setGestion(DateContable.getPartitionDateInt(fechaVoid));
                b.setNumero(e.getValue().getNumero());
                b.setFechaEmision(DateContable.toLatinAmericaDateFormat(fechaVoid)); //fecha anulacion
                b.setMoneda(null);
                b.setTipoBoleto(Boleto.Tipo.SABRE_VOID);
                b.setIdUsuarioCreador("system");
                b.setNombrePasajero(null);

                insert(b);
                crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(),
                        e.getValue().getNumero().toString(), FILE_VOID_PROCESSED, LogArchivos.Tipo.INFO);
            }
        } catch (CRUDException ex) {
            Logger.getLogger(AmadeusFileEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
