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
import com.configuracion.remote.AmadeusFileRemote;
import com.configuracion.remote.CambioRemote;
import com.configuracion.remote.ParametrosRemote;
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
public class AmadeusFileEJB extends FacadeEJB implements AmadeusFileRemote {

    private static final String CARRIER = "\n";
    private static final String AIR_BLK207 = "AIR-BLK207";
    private static final String PUNTO_COMA = ";";
    //VALID IMR
    private static final String _7A = "7A";
    // VOID MA
    private static final String MA = "MA";
    //Record DATA

    private static final String A = "A-";   //Airline
    private static final String C = "C-";   //Ticket Agent info
    private static final String D = "D-";   //Fecha Emision
    private static final String G = "G-";   //Tipo Cupon Nacional
    private static final String G_X = "G-X";   //Tipo Cupon Internacional
    private static final String H = "H-";   //Rutas
    private static final String K = "K-";   //Importe Neto, Importe Total
    private static final String TA = "TA";  //TAXES
    private static final String TAX = "TAX-";  //TAXES
    private static final String O = "O-";  //Fecha Viaje

    private static final String TAX_BO = "BO"; // Tax BO
    private static final String TAX_QM = "QM"; // Tax BO

    private static final String I = "I-";
    private static final String T = "T-";
    private static final String AMD = "AMD";

    private static final String ENDX = "ENDX";

    private static final String CANT_PROCESS_AIRLINE_EXISTS = "No se puede procesar el archivo <strong><file></strong>. No existe el codigo de Aerolinea : <strong><code></strong>.";
    private static final String FILE_IMR_PROCESSED = "Se proceso el archivo<strong><file></strong> el cual tenia una cabecera <strong>IMR</strong>.";
    private static final String FILE_7A_PROCESSED = "Se proceso el archivo<strong><file></strong> Amadeus el cual genero el Numero de Boleto <strong><numero></strong> como <strong>AIR</strong>";
    private static final String FILE_MA_PROCESSED = "Se proceso el archivo<strong><file></strong> Amadeus el cual genero el Numero de Boleto <strong><numero></strong> como <strong>VOID</strong>";
    private static final String FILE_7A_NUMBER_TICKET_EXIST = "Se proceso el archivo<strong><file></strong> Amadeus, pero genero error ya que el numero de boleto <strong><numero></strong> ya fue ingresado";
    private static final String FILE_7A_NO_DOLAR_CAMBIO = "Se proceso el archivo<strong><file></strong> Amadeus, pero genero error <strong>No se definio el tipo de cambio de dolara para hoy</strong>";
    private static final String FILE_VOID_PROCESSED = "Se proceso el archivo<strong><file></strong> Amadeus el cual genero el Numero de Boleto <strong><numero></strong> como VOID";

    private Aerolinea aerolinea = null;
    private String fechaEmision = null, fechaViaje = null, fechaVoid = null, tipoCupon = null, moneda = null, tipoBoleto = Boleto.Tipo.AMADEUS;
    private String indexPassenger = "";
    private Aeropuerto ruta1 = null, ruta2 = null, ruta3 = null, ruta4 = null, ruta5 = null;
    private BigDecimal importeNeto, totalBoleto;
    private HashMap<String, BigDecimal> impuestos = new HashMap<>();
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

        impuestos.clear();
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

        while (i < line.length) {
            //El primer parametro debe SER AIR-BLK207
            String[] lineColumn = line[i].split(PUNTO_COMA);

            //FIN DE ARCHIVO
            if (lineColumn[0].equals(ENDX)) {
                break;
            }

            if (header) {
                //analizamos la cabecera del archivo
                if (lineColumn[0].equals(AIR_BLK207)) {
                    if (lineColumn[1].equals(_7A)) {
                        //BOLETO VALIDO
                        i = 4;
                        valid_ticket = true;
                    } else if (lineColumn[1].equals(MA)) {
                        //Boleto VOID
                        void_ticket = true;
                    } else {
                        //Boleto IMR
                        break;
                    }
                    header = false;
                }
            } else if (valid_ticket) {
                // solo buscamos informacion del ticket VALIDO (7A)
                Optional op = Optional.ofNullable(lineColumn[0]);
                if (op.isPresent()) {
                    String tag = lineColumn[0].substring(0, 2);
                    /* if (tag.equals(A)) {
                        //linea aerea
                        //aerolinea.setNumero(lineColumn[1].substring(0, 3).trim());
                        //aerolinea.setIata(lineColumn[1].substring(3, 6).trim());
                        

                        //aerolinea = lineColumn[1].substring(3, 6).trim();
                    } else */
                    if (tag.equals(C)) {
                        //agent ticketing
                    } else if (tag.equals(D)) {
                        //fecha emision formato YYMMDD;YYMMDD;YYMMDD
                        String date = lineColumn[0].substring(2, 8);
                        if (fechaEmision == null) {
                            fechaEmision = DateContable.fromFormatToOtherFormat(date, DateContable.AMADEUS_D_TAG_DATE, DateContable.LATIN_AMERICA_FORMAT);
                        }
                    } else if (tag.equals(G)) {
                        // Si el boleto es Internacional o Nacional
                        tipoCupon = new String();
                        if (tag.trim().equals(G)) {
                            tipoCupon = Boleto.Cupon.NACIONAL;
                        } else if (tag.trim().equals(G_X)) {
                            tipoCupon = Boleto.Cupon.INTERNACIONAL;
                        }
                    } else if (tag.equals(H)) {
                        //Rutas del Boleto ;
                        String r1 = lineColumn[1].substring(4, 7);
                        if (ruta1 == null) {
                            ruta1 = new Aeropuerto();
                            ruta1.setIdAeropuerto(r1);
                        }

                        String r2 = lineColumn[3];
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

                        if (fechaViaje == null) {
                            fechaViaje = new String();
                            //OB    0633 B B 07SEP0750 0835 07SEP
                            //FORMATO DDMMM
                            fechaViaje = lineColumn[5].substring(15, 20);

                            fechaViaje += LocalDate.now().getYear();
                            fechaViaje = DateContable.fromFormatToOtherFormat(fechaViaje, DateContable.AMADEUS_D_TAG_H_DATE, DateContable.LATIN_AMERICA_FORMAT);
                        }

                    } else if (tag.equals(K)) {
                        //Tarifas del Boleto
                        String tmpMoneda = lineColumn[0].substring(3, 6); // KF-BOB
                        if (tmpMoneda.equals(Moneda.NACIONAL_BOB)) {
                            moneda = Moneda.NACIONAL;
                        } else if (tmpMoneda.equals(Moneda.EXTRANJERA_FULL)) {
                            moneda = Moneda.EXTRANJERA;
                        }
                        //IMPORTE NETO
                        String imNet = lineColumn[0].substring(6).trim();
                        importeNeto = new BigDecimal(imNet);
                        // IMPORTE TOTAL
                        String totBol = lineColumn[12].substring(3).trim();
                        totalBoleto = new BigDecimal(totBol);
                    } else if (tag.equals(TA)) {
                        tag = lineColumn[0].substring(0, 4);
                        if (tag.equals(TAX)) {
                            //TASAS VIEJAS y NUEVAS 
                            //@todo Preguntar aqui
                            //se debe iterar la linea KFTF;TAX1;TAX2...TAXN
                            //donde TAX =; BOB123456789A7CB ;
                            //DONDE Col 1 Leng 3 = MONEDA
                            //Col 4 Length 9 = Monto
                            //Col 12 Length 3 = Nombre 
                            lineColumn[0] = lineColumn[0].substring(4);
                            int colkft = 0;
                            while (colkft < lineColumn.length) {
                                String taxAmount = lineColumn[colkft].substring(3, 11).trim();
                                String taxName = lineColumn[colkft].substring(12, 15).trim();

                                if (taxAmount.length() > 0 && taxName.length() > 0) {
                                    impuestos.put(taxName, new BigDecimal(taxAmount));
                                }

                                colkft++;
                            }
                        }
                    } else if (tag.equals(O)) {
                        //Fecha de Viaje Formato O-DDMMXX
                        //La longitud de DDMMXX puede ser 4,7 o 10
                    } else if (tag.equals(I)) {
                        // AHORA Son los datos del Pasajero con I
                        String code = lineColumn[0]; // I-001;01
                        String name = lineColumn[1].substring(2);

                        Pasajero p = new Pasajero();
                        p.setNombrePasajero(name);

                        pasajeros.put(code, p);

                        indexPassenger = code;

                    } else if (tag.equals(T)) {
                        //Si es el ticket number del pasajero
                        String ticketNumber = lineColumn[0].substring(7);
                        Pasajero p = pasajeros.get(indexPassenger);
                        if (p != null) {
                            p.setNumero(new Long(ticketNumber));
                        }

                        String code = lineColumn[0].substring(3, 6);
                        aerolinea = findAerolinea(code, archivoBoleto);

                        op = Optional.ofNullable(aerolinea);
                        if (!op.isPresent()) {
                            return false;
                        }
                        /*HashMap<String, String> parameters = new HashMap<>();
                        parameters.put("iata", code);
                        List l = get("Aerolinea.findByIata", Aerolinea.class, parameters);

                        if (l.isEmpty()) {
                            String msg = CANT_PROCESS_AIRLINE_EXISTS.replace("<file>", archivoBoleto.getNombreArchivo());
                            msg = msg.replace("<code>", code);
                            crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), tipoBoleto, msg);

                        } else {
                            aerolinea = (Aerolinea) l.get(0);
                        }*/
                    }
                }

                i++;

            } else if (void_ticket) {
                // solo buscamos informacion del ticket VOID
                //TAG Longitud 3
                String tag = lineColumn[0].substring(0, 3);

                if (tag.equals(AMD)) {
                    //FECHA VOID
                    if (fechaVoid == null) {
                        fechaVoid = lineColumn[2].substring(4);
                        fechaVoid += LocalDate.now().getYear();
                        fechaVoid = DateContable.fromFormatToOtherFormat(fechaVoid, DateContable.AMADEUS_D_TAG_H_DATE, DateContable.LATIN_AMERICA_FORMAT);
                    }
                }

                tag = lineColumn[0].substring(0, 2);
                if (tag.equals(T)) {
                    //LINEA AEREA
                    String code = lineColumn[0].substring(3, 6);

                    aerolinea = findAerolinea(code, archivoBoleto);
                    /*HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("iata", code);
                    List l = get("Aerolinea.findByIata", Aerolinea.class, parameters);

                    if (l.isEmpty()) {
                        String msg = CANT_PROCESS_AIRLINE_EXISTS.replace("<file>", archivoBoleto.getNombreArchivo());
                        msg = msg.replace("<code>", code);
                        crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), tipoBoleto, msg);

                    } else {
                        aerolinea = (Aerolinea) l.get(0);
                    }*/

                    // Numero de Boleto
                    String ticketNumber = lineColumn[0].substring(7);
                    Pasajero p = pasajeros.get(indexPassenger);
                    if (p != null) {
                        p.setNumero(new Long(ticketNumber));
                    }

                } else if (tag.equals(I)) {
                    // AHORA Son los datos del Pasajero con I
                    String code = lineColumn[0]; // I-001;01
                    String name = lineColumn[1].substring(2);

                    Pasajero p = new Pasajero();
                    p.setNombrePasajero(name);

                    pasajeros.put(code, p);

                    indexPassenger = code;

                }

                i++;
            }

            //System.out.println(archivoBoleto.getNombreArchivo() + ":" + line[i]);
        }

        //Verificamos que archivo proceso
        if (valid_ticket) {
            //creamos el boleto AMADEUS AM
            createBoletoAmadeus(archivoBoleto);
        } else if (void_ticket) {
            //creamos el boleto AMADEUS VOID AV
            createBoletoVoidAmadeus(archivoBoleto);

        } else {
            crearLogArchivo(archivoBoleto.getNombreArchivo(), archivoBoleto.getTipoArchivo(), "", FILE_IMR_PROCESSED, LogArchivos.Tipo.WARNING);
        }

        return true;

    }

    private Aerolinea findAerolinea(String code, final ArchivoBoleto archivoBoleto
    ) throws CRUDException {
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

    private boolean createBoletoAmadeus(final ArchivoBoleto archivo) {

        try {
            Parametros porcentajeComision = (Parametros) ejbParametros.get(new Parametros(Parametros.PORCENTAJE_COMISION));

            CambioDolar diario = null;
            for (Map.Entry<String, Pasajero> entry : pasajeros.entrySet()) {
                try {
                    if (ejbBoleto.isBoletoRegistrado(new Boleto(entry.getValue().getNumero()))) {
                        crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(), entry.getValue().getNumero().toString(),
                                FILE_7A_NUMBER_TICKET_EXIST, LogArchivos.Tipo.ERROR);
                        return false;
                    }

                    diario = ejbCambio.get(fechaEmision, "CambioDolar.findFecha");

                    if (diario == null) {
                        List l = ejbCambio.get();
                        if (l.isEmpty()) {
                            crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(), entry.getValue().getNumero().toString(),
                                    FILE_7A_NO_DOLAR_CAMBIO, LogArchivos.Tipo.ERROR);
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
                b.setTipoBoleto(Boleto.Tipo.AMADEUS);
                b.setEstado(Boleto.Estado.CARGADO_AUTOMATICO);
                b.setIdArchivo(archivo);
                b.setIdAerolinea(aerolinea);
                b.setFactorCambiario(diario.getValor());
                b.setIdEmpresa(1);
                b.setEmision(Boleto.Emision.TKT);
                b.setTipoCupon(tipoCupon);
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

                for (Map.Entry<String, BigDecimal> imp : impuestos.entrySet()) {
                    if (imp.getKey().equals("BO")) {
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

                }

                b.setTotalBoleto(totalBoleto);
                b.setTotalMontoCobrar(totalBoleto);

                // Saca la comision del Boleto de acuerdo a la linea aerea
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
                                    Double totalComisonIva = (total * porcentaje) / 100 + total;
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
                                        b.setMontoComision(new BigDecimal(Math.round(totalComisonIva)).setScale(Contabilidad.VALOR_DECIMAL_0, RoundingMode.HALF_DOWN));
                                    } else {
                                        b.setMontoComision(new BigDecimal(totalComisonIva).setScale(Contabilidad.VALOR_DECIMAL_2, RoundingMode.HALF_DOWN));
                                    }
                                }

                            }
                        }
                    }
                }
                try {
                    if (b.getMontoComision() != null) {
                        Double comision = b.getMontoComision().doubleValue();
                        Double totalBoleto = b.getTotalBoleto().doubleValue();

                        Double montoPagarLinea = totalBoleto - comision;
                        b.setMontoPagarLineaAerea(new BigDecimal(montoPagarLinea));
                    }
                    //creamos el boleto
                    insert(b);

                    crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(),
                            entry.getValue().getNumero().toString(), FILE_7A_PROCESSED, LogArchivos.Tipo.INFO);
                } catch (CRUDException ex) {
                    Logger.getLogger(AmadeusFileEJB.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }

        } catch (CRUDException ex) {
            Logger.getLogger(AmadeusFileEJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private void createBoletoVoidAmadeus(ArchivoBoleto archivo) {
        try {
            Pasajero p = new Pasajero();
            for (Map.Entry<String, Pasajero> e : pasajeros.entrySet()) {

                p = (Pasajero) e.getValue();

                Boleto b = new Boleto();
                b.setIdArchivo(archivo);
                b.setIdAerolinea(aerolinea);
                b.setIdEmpresa(1);
                b.setTipoCupon(tipoCupon);
                b.setEstado(Boleto.Estado.CARGADO_AUTOMATICO);
                b.setEmision(Boleto.Emision.TKT);
                b.setFechaInsert(DateContable.getCurrentDate());
                b.setGestion(DateContable.getPartitionDateInt(fechaVoid));
                b.setNumero(e.getValue().getNumero());
                b.setFechaEmision(DateContable.toLatinAmericaDateFormat(fechaVoid)); //fecha anulacion
                b.setMoneda(moneda);
                b.setTipoBoleto(Boleto.Tipo.AMADEUS_VOID);
                b.setIdUsuarioCreador("system");
                b.setNombrePasajero(e.getValue().getNombrePasajero());

                insert(b);
                crearLogArchivo(archivo.getNombreArchivo(), archivo.getTipoArchivo(),
                        e.getValue().getNumero().toString(), FILE_MA_PROCESSED, LogArchivos.Tipo.INFO);
            }
        } catch (CRUDException ex) {
            Logger.getLogger(AmadeusFileEJB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
