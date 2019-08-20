/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cobranzas.ejb;

import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.cobranzas.dto.KardexClienteDto;
import com.cobranzas.dto.ReporteEstadoClienteDto;
import com.cobranzas.dto.ReporteEstadoClienteMainDto;
import com.cobranzas.json.KardexClienteSearchJson;
import com.cobranzas.json.KardexClienteSearchTipo;
import com.cobranzas.json.ReporteEstadoClienteSearchJson;
import com.cobranzas.remote.KardexClienteRemote;
import com.contabilidad.entities.NotaDebito;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.DateContable;
import com.seguridad.utils.Estado;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author xeio
 */
@Stateless
public class KardexClienteEJB extends FacadeEJB implements KardexClienteRemote {

    @Override
    public synchronized List<KardexClienteDto> generarKardexCliente(KardexClienteSearchJson search) throws CRUDException {

        Integer idNotaDebito = null;
        Integer idCliente = null;

        if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.BOLETO)) {
            //Buscar Nota DEbito por Boleto
            Query query = em.createNamedQuery("Boleto.findNroBoleto");
            query.setParameter("numero", search.getNroBoleto());

            List l = query.getResultList();

            if (l.isEmpty()) {
                throw new CRUDException("No se encontraron informacion para generar el kardex con el Numero de Boleto %s".replace("%s", search.getNroBoleto().toString()));
            }

            Boleto b = (Boleto) l.get(0);

            if (b.getIdNotaDebito() == null) {
                throw new CRUDException("El numero de boleto %s no tiene una Nota de Debito Asociada.".replace("%s", search.getNroBoleto().toString()));
            }

            if (b.getEstado().equals(Estado.ANULADO)) {
                throw new CRUDException("El numero de boleto %s se encuentra ANULADO".replace("%s", search.getNroBoleto().toString()));
            }

            if (b.getIdCliente() == null) {
                throw new CRUDException("El numero de boleto %s no se encuenta asociado a un Cliente.".replace("%s", search.getNroBoleto().toString()));
            }

            idNotaDebito = b.getIdNotaDebito();
            idCliente = b.getIdCliente().getIdCliente();

        } else if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.CLIENTE)) {

            if (search.getIdCliente() == null) {
                throw new CRUDException("Debe Ingresar un cliente Valido.");
            }

            Cliente cl = em.find(Cliente.class, ((Double) search.getIdCliente().getId()).intValue());
            if (cl == null) {
                throw new CRUDException("El cliente ingresado no existe");
            }

            idCliente = cl.getIdCliente();

        } else if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.NOTA_DEBITO)) {
            if (search.getIdNotaDebito() == null) {
                throw new CRUDException("Debe ingresar un numero de nota de debito valido");
            }

            NotaDebito n = em.find(NotaDebito.class, search.getIdNotaDebito());
            if (n == null) {
                throw new CRUDException("La nota de debito %s no existe.".replace("%s", search.getIdNotaDebito().toString()));
            }

            if (n.getIdCliente() == null) {
                throw new CRUDException("La nota de debito %s no tiene un cliente asociado.".replace("%s", search.getIdNotaDebito().toString()));
            }

            idCliente = n.getIdCliente().getId();
            idNotaDebito = 0;
        } else if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.PASAJERO)) {

            if (search.getNombrePasajero() == null) {
                throw new CRUDException("Debe especificar un nombre de pasajero.");
            }

            Query query = em.createNamedQuery("Boleto.findByNombrePasajero");
            query.setParameter("nombrePasajero", search.getNombrePasajero());

            List<Boleto> l = query.getResultList();

            if (l.isEmpty()) {
                throw new CRUDException("No existen Boletos con ese nombre de pasajero.");
            }

            for (Boleto b : l) {
                if (b.getIdCliente() != null) {
                    idCliente = b.getIdCliente().getIdCliente();
                    break;
                }
            }

            if (idCliente == null) {
                throw new CRUDException("El nombre de pasajero no genero ningun Cliente. El Pasajero existe, pero no esta asociando a un Cliente.");
            }

        }

        if (idCliente == null) {
            throw new CRUDException("Los parametros de busqueda no generaron ningun Cliente para buscar. Realice un nuevo criterio de busqueda");
        }

        StoredProcedureQuery stp = em.createNamedStoredProcedureQuery("NotaDebito.generarKardexCliente");
        stp.setParameter("in_id_cliente", idCliente);
        stp.setParameter("in_id_selected", idNotaDebito);

        List l = stp.getResultList();

        return l;

    }

    @Override
    public synchronized List<KardexClienteDto> generarHistorico(KardexClienteSearchJson search) throws CRUDException {

        Integer idNotaDebito = null;
        Integer idCliente = null;

        if (search.getIdCliente() == null) {
            throw new CRUDException("Debe Ingresar un cliente Valido.");
        }

        Cliente cl = em.find(Cliente.class, ((Double) search.getIdCliente().getId()).intValue());
        if (cl == null) {
            throw new CRUDException("El cliente ingresado no existe");
        }

        //fechas
        if (search.getFechaInicio() == null) {
            throw new CRUDException("Debe Ingresar una fecha de inicio");
        }

        if (search.getFechaInicio() == null) {
            throw new CRUDException("Debe Ingresar una fecha fin");
        }

        idCliente = cl.getIdCliente();

        if (idCliente == null) {
            throw new CRUDException("Los parametros de busqueda no generaron ningun Cliente para buscar. Realice un nuevo criterio de busqueda");
        }

        Date startDate = DateContable.toLatinAmericaDateFormat(search.getFechaInicio());
        Date endDate = DateContable.toLatinAmericaDateFormat(search.getFechaFin());

        StoredProcedureQuery stp = em.createNamedStoredProcedureQuery("NotaDebito.historicoCliente");
        stp.setParameter("in_id_cliente", idCliente);
        stp.setParameter("in_start_date", startDate);
        stp.setParameter("in_end_date", endDate);
        stp.setParameter("in_selected", 0);

        List l = stp.getResultList();

        return l;

    }

    @Override
    public List<ReporteEstadoClienteMainDto> generarReporteEstadoCliente(ReporteEstadoClienteSearchJson search) throws CRUDException {

        boolean inicio = false;
        boolean fin = false;

        if (search.getFechaInicio() == null) {
            inicio = true;
        }

        if (search.getFechaFin() == null) {
            fin = true;
        }

        // fecha de inicio del sistema
        Date startDate = DateContable.toLatinAmericaDateFormat(inicio ? "01/01/2019" : search.getFechaInicio());
        Date endDate = DateContable.toLatinAmericaDateFormat(fin ? DateContable.getCurrentDateStr() : search.getFechaFin());

        StoredProcedureQuery stp = em.createNamedStoredProcedureQuery("NotaDebito.generarReporteClienteEstados");
        stp.setParameter("in_id_empresa", search.getIdEmpresa());
        stp.setParameter("in_start_date", startDate);
        stp.setParameter("in_end_date", endDate);

        List l = stp.getResultList();

        List<ReporteEstadoClienteMainDto> entities = new LinkedList<>();

        if (!l.isEmpty()) {

            Iterator i = l.iterator();
            while (i.hasNext()) {

                ReporteEstadoClienteDto current = (ReporteEstadoClienteDto) i.next();

                if (entities.isEmpty()) {
                    ReporteEstadoClienteMainDto main = new ReporteEstadoClienteMainDto();
                    main.setIdCliente(current.getIdCliente());
                    main.setNombre(current.getNombreCliente());
                    main.setTotalSaldoExt(current.getSaldoExt() != null ? current.getSaldoExt() : new BigDecimal(BigInteger.ZERO));
                    main.setTotalSaldoNac(current.getSaldoNac() != null ? current.getSaldoNac() : new BigDecimal(BigInteger.ZERO));

                    main.getEntities().add(current);

                    entities.add(main);
                } else {
                    //iteramos la lista de entidades
                    Iterator mainIterator = entities.iterator();
                    boolean findCliente = false;
                    while (mainIterator.hasNext()) {
                        ReporteEstadoClienteMainDto currentMain = (ReporteEstadoClienteMainDto) mainIterator.next();
                        
                        // current row
                        BigDecimal currentSaldoExt = current.getSaldoExt() != null ? current.getSaldoExt() : new BigDecimal(BigInteger.ZERO);
                        BigDecimal currentSaldoNac = current.getSaldoNac() != null ? current.getSaldoNac() : new BigDecimal(BigInteger.ZERO);

                        if (currentMain.getIdCliente().equals(current.getIdCliente())) {
                            currentMain.getEntities().add(current);
                            currentMain.setTotalSaldoExt(currentMain.getTotalSaldoExt()!= null ? currentMain.getTotalSaldoExt().add(currentSaldoExt) : currentSaldoExt);
                            currentMain.setTotalSaldoNac(currentMain.getTotalSaldoNac()!= null ? currentMain.getTotalSaldoNac().add(currentSaldoNac) : currentSaldoNac  );

                            findCliente = true;
                        }

                    }

                    if (!findCliente) {
                        ReporteEstadoClienteMainDto main = new ReporteEstadoClienteMainDto();
                        main.setIdCliente(current.getIdCliente());
                        main.setNombre(current.getNombreCliente());
                        main.setTotalSaldoExt(current.getSaldoExt());
                        main.setTotalSaldoNac(current.getSaldoNac());

                        main.getEntities().add(current);

                        entities.add(main);
                    }

                }

            }

        }

        return entities;

    }

}
