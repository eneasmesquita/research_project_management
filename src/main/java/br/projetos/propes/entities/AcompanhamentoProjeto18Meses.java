/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eneas
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "acompanhamento_projeto_18_meses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findAll", query = "SELECT a FROM AcompanhamentoProjeto18Meses a"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findById", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.id = :id"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDataExpiracaoProjeto", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.dataExpiracaoProjeto = :dataExpiracaoProjeto"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDataExpiracaoRp1", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.dataExpiracaoRp1 = :dataExpiracaoRp1"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDataExpiracaoRp2", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.dataExpiracaoRp2 = :dataExpiracaoRp2"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDataExpiracaoRf", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.dataExpiracaoRf = :dataExpiracaoRf"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail30DiasRp1", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail30DiasRp1 = :disparoEmail30DiasRp1"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail15DiasRp1", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail15DiasRp1 = :disparoEmail15DiasRp1"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail5DiasRp1", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail5DiasRp1 = :disparoEmail5DiasRp1"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail30DiasRp2", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail30DiasRp2 = :disparoEmail30DiasRp2"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail15DiasRp2", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail15DiasRp2 = :disparoEmail15DiasRp2"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail5DiasRp2", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail5DiasRp2 = :disparoEmail5DiasRp2"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail30DiasRf", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail30DiasRf = :disparoEmail30DiasRf"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail15DiasRf", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail15DiasRf = :disparoEmail15DiasRf"),
    @NamedQuery(name = "AcompanhamentoProjeto18Meses.findByDisparoEmail5DiasRf", query = "SELECT a FROM AcompanhamentoProjeto18Meses a WHERE a.disparoEmail5DiasRf = :disparoEmail5DiasRf")})
public class AcompanhamentoProjeto18Meses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_expiracao_projeto")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracaoProjeto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_expiracao_rp1")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracaoRp1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_expiracao_rp2")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracaoRp2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_expiracao_rf")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracaoRf;
    @Column(name = "disparo_email_30_dias_rp1")
    private Boolean disparoEmail30DiasRp1;
    @Column(name = "disparo_email_15_dias_rp1")
    private Boolean disparoEmail15DiasRp1;
    @Column(name = "disparo_email_5_dias_rp1")
    private Boolean disparoEmail5DiasRp1;
    @Column(name = "disparo_email_30_dias_rp2")
    private Boolean disparoEmail30DiasRp2;
    @Column(name = "disparo_email_15_dias_rp2")
    private Boolean disparoEmail15DiasRp2;
    @Column(name = "disparo_email_5_dias_rp2")
    private Boolean disparoEmail5DiasRp2;
    @Column(name = "disparo_email_30_dias_rf")
    private Boolean disparoEmail30DiasRf;
    @Column(name = "disparo_email_15_dias_rf")
    private Boolean disparoEmail15DiasRf;
    @Column(name = "disparo_email_5_dias_rf")
    private Boolean disparoEmail5DiasRf;
    @JoinColumn(name = "projeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projeto projeto;

    public AcompanhamentoProjeto18Meses() {
    }

    public AcompanhamentoProjeto18Meses(Integer id) {
        this.id = id;
    }

    public AcompanhamentoProjeto18Meses(Integer id, Date dataExpiracaoProjeto, Date dataExpiracaoRp1, Date dataExpiracaoRp2, Date dataExpiracaoRf) {
        this.id = id;
        this.dataExpiracaoProjeto = dataExpiracaoProjeto;
        this.dataExpiracaoRp1 = dataExpiracaoRp1;
        this.dataExpiracaoRp2 = dataExpiracaoRp2;
        this.dataExpiracaoRf = dataExpiracaoRf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataExpiracaoProjeto() {
        return dataExpiracaoProjeto;
    }

    public void setDataExpiracaoProjeto(Date dataExpiracaoProjeto) {
        this.dataExpiracaoProjeto = dataExpiracaoProjeto;
    }

    public Date getDataExpiracaoRp1() {
        return dataExpiracaoRp1;
    }

    public void setDataExpiracaoRp1(Date dataExpiracaoRp1) {
        this.dataExpiracaoRp1 = dataExpiracaoRp1;
    }

    public Date getDataExpiracaoRp2() {
        return dataExpiracaoRp2;
    }

    public void setDataExpiracaoRp2(Date dataExpiracaoRp2) {
        this.dataExpiracaoRp2 = dataExpiracaoRp2;
    }

    public Date getDataExpiracaoRf() {
        return dataExpiracaoRf;
    }

    public void setDataExpiracaoRf(Date dataExpiracaoRf) {
        this.dataExpiracaoRf = dataExpiracaoRf;
    }

    public Boolean getDisparoEmail30DiasRp1() {
        return disparoEmail30DiasRp1;
    }

    public void setDisparoEmail30DiasRp1(Boolean disparoEmail30DiasRp1) {
        this.disparoEmail30DiasRp1 = disparoEmail30DiasRp1;
    }

    public Boolean getDisparoEmail15DiasRp1() {
        return disparoEmail15DiasRp1;
    }

    public void setDisparoEmail15DiasRp1(Boolean disparoEmail15DiasRp1) {
        this.disparoEmail15DiasRp1 = disparoEmail15DiasRp1;
    }

    public Boolean getDisparoEmail5DiasRp1() {
        return disparoEmail5DiasRp1;
    }

    public void setDisparoEmail5DiasRp1(Boolean disparoEmail5DiasRp1) {
        this.disparoEmail5DiasRp1 = disparoEmail5DiasRp1;
    }

    public Boolean getDisparoEmail30DiasRp2() {
        return disparoEmail30DiasRp2;
    }

    public void setDisparoEmail30DiasRp2(Boolean disparoEmail30DiasRp2) {
        this.disparoEmail30DiasRp2 = disparoEmail30DiasRp2;
    }

    public Boolean getDisparoEmail15DiasRp2() {
        return disparoEmail15DiasRp2;
    }

    public void setDisparoEmail15DiasRp2(Boolean disparoEmail15DiasRp2) {
        this.disparoEmail15DiasRp2 = disparoEmail15DiasRp2;
    }

    public Boolean getDisparoEmail5DiasRp2() {
        return disparoEmail5DiasRp2;
    }

    public void setDisparoEmail5DiasRp2(Boolean disparoEmail5DiasRp2) {
        this.disparoEmail5DiasRp2 = disparoEmail5DiasRp2;
    }

    public Boolean getDisparoEmail30DiasRf() {
        return disparoEmail30DiasRf;
    }

    public void setDisparoEmail30DiasRf(Boolean disparoEmail30DiasRf) {
        this.disparoEmail30DiasRf = disparoEmail30DiasRf;
    }

    public Boolean getDisparoEmail15DiasRf() {
        return disparoEmail15DiasRf;
    }

    public void setDisparoEmail15DiasRf(Boolean disparoEmail15DiasRf) {
        this.disparoEmail15DiasRf = disparoEmail15DiasRf;
    }

    public Boolean getDisparoEmail5DiasRf() {
        return disparoEmail5DiasRf;
    }

    public void setDisparoEmail5DiasRf(Boolean disparoEmail5DiasRf) {
        this.disparoEmail5DiasRf = disparoEmail5DiasRf;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcompanhamentoProjeto18Meses)) {
            return false;
        }
        AcompanhamentoProjeto18Meses other = (AcompanhamentoProjeto18Meses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.AcompanhamentoProjeto18Meses[ id=" + id + " ]";
    }
    
}
