/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contabilidad.entities;

import com.seguridad.control.entities.Entidad;
import com.seguridad.control.exception.CRUDException;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xeio
 */
@Entity
@Table(name = "cnt_plan_cuentas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanCuentas.findAll", query = "SELECT p FROM PlanCuentas p WHERE p.idEmpresa=:idEmpresa ORDER BY p.nroPlanCuenta, p.cuenta, p.nivel asc")
    ,
    //@NamedQuery(name = "PlanCuentas.findAll", query = "SELECT p ,(select c.cuenta from PlanCuentas c where p.idPlanCuentas=c.idPlanCuentas ) as idPlanCuentaPadreNombre FROM PlanCuentas p ORDER BY p.idPlanCuentas, p.cuenta, p.nivel asc"),
    @NamedQuery(name = "PlanCuentas.forCombo", query = "SELECT p.idPlanCuentas,p.cuenta,p.comodin FROM PlanCuentas p WHERE p.nivel = 5 AND p.idEmpresa=:idEmpresa  Order by p.cuenta")
    ,
    @NamedQuery(name = "PlanCuentas.forComboPlan", query = "SELECT p FROM PlanCuentas p WHERE p.idEmpresa=:idEmpresa and p.nroPlanCuentaPadre= (SELECT q.nroPlanCuenta FROM PlanCuentas q WHERE q.cuenta = :cuenta ) ORDER BY p.nroPlanCuenta, p.cuenta, p.nivel asc")
    ,
    @NamedQuery(name = "PlanCuentas.forComboIdPlan", query = "SELECT p FROM PlanCuentas p WHERE p.idEmpresa=:idEmpresa and p.nroPlanCuentaPadre=:nroPlanCuentas ORDER BY p.nroPlanCuenta, p.cuenta, p.nivel asc")
    ,
    @NamedQuery(name = "PlanCuentas.forSearch", query = "SELECT p.idPlanCuentas,p.cuenta FROM PlanCuentas p WHERE p.nivel < 5 Order by p.cuenta")
    ,
    @NamedQuery(name = "PlanCuentas.nivel4Central", query = "SELECT p FROM PlanCuentas p WHERE p.nivel < 5 and p.idEmpresa=1 Order by p.cuenta")

})

@NamedStoredProcedureQuery(
        name = "PlanCuenta.sumasYsaldos",
        procedureName = "sumasYsaldosNivel",
        resultSetMappings = "SumasSaldosDto",
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_start_date")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_end_date")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "in_moneda")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_empresa")
            ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_nivel")
        }
)

@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "PlanCuenta.estadoResultado",
                procedureName = "estadoResultado",
                resultSetMappings = "EstadosResultadosDto",
                parameters = {
                    @StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_start_date")
                    ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Date.class, name = "in_end_date")
                    ,@StoredProcedureParameter(mode = ParameterMode.IN, type = String.class, name = "in_moneda")
                    ,@StoredProcedureParameter(mode = ParameterMode.IN, type = Integer.class, name = "in_id_empresa")
                }
        )
)

@SqlResultSetMappings(
        {
            @SqlResultSetMapping(
                    name = "SumasSaldosDto",
                    classes = @ConstructorResult(
                            targetClass = SumasSaldosDto.class,
                            columns = {
                                @ColumnResult(name = "v_id_plan_cuenta", type = Integer.class)
                                ,@ColumnResult(name = "v_cuenta", type = String.class)
                                ,@ColumnResult(name = "v_nro_plan_cuenta", type = Long.class)
                                ,@ColumnResult(name = "v_nro_plan_cuenta_padre", type = Long.class)
                                ,@ColumnResult(name = "v_suma_debe", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_suma_haber", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_saldo_debe", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_saldo_haber", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_nivel", type = Integer.class)
                            }
                    )
            )
            ,
        
                @SqlResultSetMapping(
                    name = "EstadosResultadosDto",
                    classes = @ConstructorResult(
                            targetClass = EstadosResultadosDto.class,
                            columns = {
                                @ColumnResult(name = "v_id_plan_cuenta", type = Integer.class)
                                ,@ColumnResult(name = "v_cuenta", type = String.class)
                                ,@ColumnResult(name = "v_nro_plan_cuenta", type = Long.class)
                                ,@ColumnResult(name = "v_nro_plan_cuenta_padre", type = Long.class)
                                ,@ColumnResult(name = "v_suma_debe", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_suma_haber", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_saldo_debe", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_saldo_haber", type = BigDecimal.class)
                                ,@ColumnResult(name = "v_nivel", type = Integer.class)
                                ,@ColumnResult(name = "v_saldo", type = String.class)
                                ,@ColumnResult(name = "v_saldo_monto", type = BigDecimal.class)
                            }
                    )
            )
        }
)

public class PlanCuentas extends Entidad {

    class Regularizacion {

        public static final String DEPRECIACION = "D";
        public static final String PREVISION = "P";
        public static final String AMORTIZACION = "A";
        public static final String NINGUNO = "A";
    }

    private static final long serialVersionUID = 1L;
    @Column(name = "id_empresa", updatable = false)
    private Integer idEmpresa;

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_plan_cuentas")
    private Integer idPlanCuentas;

    @Column(name = "nro_plan_cuenta")
    private Long nroPlanCuenta;

    @Column(name = "nro_plan_cuenta_padre")
    private Long nroPlanCuentaPadre;

    @Column(name = "cuenta", length = 40)
    private String cuenta;

    @Column(name = "aplica_movimiento")
    private String aplicaMovimiento;

    @Column(name = "moneda", length = 2)
    private String moneda;

    @Column(name = "marco", length = 2)
    private String marco;

    @Column(name = "cta_itb", length = 24)
    private Integer ctaItb;

    @Column(name = "nivel")
    private Integer nivel;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "mantenimiento_valor")
    private String mantenimientoValor;

    @Column(name = "comodin")
    private String comodin;

    @Column(name = "tipo_regularizacion")
    private String tipoRegularizacion;

    @Column(name = "id_cuenta_regularizacion")
    private Integer idCuentaRegularizacion;

    @Transient
    private String ctaItbNombre;

    @Transient
    private String idPlanCuentaPadreNombre;

    public String getTipoRegularizacion() {
        return tipoRegularizacion;
    }

    public void setTipoRegularizacion(String tipoRegularizacion) {
        this.tipoRegularizacion = tipoRegularizacion;
    }

    public Integer getIdCuentaRegularizacion() {
        return idCuentaRegularizacion;
    }

    public void setIdCuentaRegularizacion(Integer idCuentaRegularizacion) {
        this.idCuentaRegularizacion = idCuentaRegularizacion;
    }

    public PlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public String getComodin() {
        return comodin;
    }

    public void setComodin(String comodin) {
        this.comodin = comodin;
    }

    public String getCtaItbNombre() {
        return ctaItbNombre;
    }

    public void setCtaItbNombre(String ctaItbNombre) {
        this.ctaItbNombre = ctaItbNombre;
    }

    public String getIdPlanCuentaPadreNombre() {
        return idPlanCuentaPadreNombre;
    }

    public void setIdPlanCuentaPadreNombre(String idPlanCuentaPadreNombre) {
        this.idPlanCuentaPadreNombre = idPlanCuentaPadreNombre;
    }

    public String getMantenimientoValor() {
        return mantenimientoValor;
    }

    public void setMantenimientoValor(String mantenimientoValor) {
        this.mantenimientoValor = mantenimientoValor;
    }

    public PlanCuentas() {
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdPlanCuentas() {
        return idPlanCuentas;
    }

    public void setIdPlanCuentas(Integer idPlanCuentas) {
        this.idPlanCuentas = idPlanCuentas;
    }

    public Long getNroPlanCuenta() {
        return nroPlanCuenta;
    }

    public void setNroPlanCuenta(Long nroPlanCuenta) {
        this.nroPlanCuenta = nroPlanCuenta;
    }

    public Long getNroPlanCuentaPadre() {
        return nroPlanCuentaPadre;
    }

    public void setNroPlanCuentaPadre(Long nroPlanCuentaPadre) {
        this.nroPlanCuentaPadre = nroPlanCuentaPadre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getAplicaMovimiento() {
        return aplicaMovimiento;
    }

    public void setAplicaMovimiento(String aplica) {
        this.aplicaMovimiento = aplica;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMarco() {
        return marco;
    }

    public void setMarco(String marco) {
        this.marco = marco;
    }

    public Integer getCtaItb() {
        return ctaItb;
    }

    public void setCtaItb(Integer ctaItb) {
        this.ctaItb = ctaItb;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanCuentas != null ? idPlanCuentas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanCuentas)) {
            return false;
        }
        PlanCuentas other = (PlanCuentas) object;
        if ((this.idPlanCuentas == null && other.idPlanCuentas != null) || (this.idPlanCuentas != null && !this.idPlanCuentas.equals(other.idPlanCuentas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.contabilidad.entities.PlanCuentas[ idPlanCuentas=" + idPlanCuentas + " ]";
    }

    @Override
    public int getId() throws CRUDException {
        return 0;
    }

}
