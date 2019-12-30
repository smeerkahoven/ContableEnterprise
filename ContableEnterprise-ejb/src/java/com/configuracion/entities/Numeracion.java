/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.configuracion.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "tb_numeracion")
@NamedQueries({
    @NamedQuery(name = "Numeracion.findNumeracion", query = "SELECT n FROM Numeracion n WHERE n.idGestion=:idGestion and n.formulario=:formulario")
})
public class Numeracion extends Entidad {

    public static final String NOTA_DEBITO = "nota-debito";
    public static final String INGRESO_CAJA = "ingreso-caja";
    public static final String NOTA_CREDITO = "nota-credito";
    public static final String DEVOLUCION = "devolucion";
    public static final String PAGO_ANTICIPADO = "pago-anticipado";
    public static final String PAGO_ANTICIPADO_TRANX = "pago-anticipado-transaccion";

    public static final Integer NOTA_DEBITO_NUM = 10000001;
    public static final Integer INGRESO_CAJA_NUM = 20000001;
    public static final Integer PAGO_ANTICIPADO_NUM = 30000001;
    public static final Integer PAGO_ANTICIPADO_TRX_NUM = 40000001;
    public static final Integer NOTA_CREDITO_NUM = 50000001;
    public static final Integer DEVOLUCION_NUM = 60000001;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_numeracion")
    private Integer idNumeracion;

    @Column(name = "id_gestion")
    private Integer idGestion;

    @Column(name = "formulario")
    private String formulario;

    @Column(name = "valor")
    private Integer valor;

    @Override
    public int getId() throws CRUDException {
        return idNumeracion;
    }

    public Integer getIdNumeracion() {
        return idNumeracion;
    }

    public void setIdNumeracion(Integer idNumeracion) {
        this.idNumeracion = idNumeracion;
    }

    public Integer getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Integer idGestion) {
        this.idGestion = idGestion;
    }

    public String getFormulario() {
        return formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

}
