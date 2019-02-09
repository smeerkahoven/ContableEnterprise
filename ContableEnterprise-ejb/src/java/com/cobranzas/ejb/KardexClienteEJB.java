/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cobranzas.ejb;

import com.agencia.entities.Boleto;
import com.agencia.entities.Cliente;
import com.cobranzas.dto.KardexClienteDto;
import com.cobranzas.json.KardexClienteSearchJson;
import com.cobranzas.json.KardexClienteSearchTipo;
import com.cobranzas.remote.KardexClienteRemote;
import com.contabilidad.entities.NotaDebito;
import com.seguridad.control.FacadeEJB;
import com.seguridad.control.exception.CRUDException;
import com.seguridad.utils.Estado;
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
            idCliente = b.getIdCliente().getIdCliente() ;
            
        }else if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.CLIENTE)){
            
            if (search.getIdCliente() == null ) {
                throw new CRUDException("Debe Ingresar un cliente Valido.");
            }
            
            Cliente cl = em.find(Cliente.class, ((Double) search.getIdCliente().getId()).intValue()) ;
            if (cl == null) {
                throw new CRUDException("El cliente ingresado no existe");
            }
            
            idCliente = cl.getIdCliente() ;
            
        } else if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.NOTA_DEBITO)){
            if (search.getIdNotaDebito() == null) {
                throw new CRUDException("Debe ingresar un numero de nota de debito valido") ;
            }
            
            NotaDebito n = em.find(NotaDebito.class,search.getIdNotaDebito()) ;
            if (n == null) {
                throw new CRUDException("La nota de debito %s no existe.".replace("%s", search.getIdNotaDebito().toString()));
            }
            
            if (n.getIdCliente() == null) {
                throw new CRUDException("La nota de debito %s no tiene un cliente asociado.".replace("%s", search.getIdNotaDebito().toString()));
            }
            
            idCliente = n.getIdCliente().getId();
            idNotaDebito = 0 ;
        } else if (search.getTipoBusqueda().equals(KardexClienteSearchTipo.PASAJERO)){
            
            if (search.getNombrePasajero() == null){
                throw new CRUDException("Debe especificar un nombre de pasajero.");
            }
            
            Query query = em.createNamedQuery("Boleto.findByNombrePasajero");
            query.setParameter("nombrePasajero", search.getNombrePasajero());
            
            List<Boleto> l = query.getResultList() ;
            
            if (l.isEmpty()) {
                throw new CRUDException("No existen Boletos con ese nombre de pasajero.");
            }
            
            for (Boleto b : l) {
                if ( b.getIdCliente() != null){
                    idCliente = b.getIdCliente().getIdCliente() ;
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
        
        return l ;
        
    }

}
