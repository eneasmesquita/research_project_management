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
@Table(catalog = "universidade", schema = "propes", name = "acompanhamento_projeto_6_meses_1_ano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findAll", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findById", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.id = :id"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDataExpiracaoProjeto", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.dataExpiracaoProjeto = :dataExpiracaoProjeto"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDataExpiracaoRp", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.dataExpiracaoRp = :dataExpiracaoRp"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDataExpiracaoRf", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.dataExpiracaoRf = :dataExpiracaoRf"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDisparoEmail30DiasRp", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.disparoEmail30DiasRp = :disparoEmail30DiasRp"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDisparoEmail15DiasRp", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.disparoEmail15DiasRp = :disparoEmail15DiasRp"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDisparoEmail5DiasRp", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.disparoEmail5DiasRp = :disparoEmail5DiasRp"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDisparoEmail30DiasRf", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.disparoEmail30DiasRf = :disparoEmail30DiasRf"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDisparoEmail15DiasRf", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.disparoEmail15DiasRf = :disparoEmail15DiasRf"),
    @NamedQuery(name = "AcompanhamentoProjeto6Meses1Ano.findByDisparoEmail5DiasRf", query = "SELECT a FROM AcompanhamentoProjeto6Meses1Ano a WHERE a.disparoEmail5DiasRf = :disparoEmail5DiasRf")})
public class AcompanhamentoProjeto6Meses1Ano implements Serializable {

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
    @Column(name = "data_expiracao_rp")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracaoRp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_expiracao_rf")
    @Temporal(TemporalType.DATE)
    private Date dataExpiracaoRf;
    @Column(name = "disparo_email_30_dias_rp")
    private Boolean disparoEmail30DiasRp;
    @Column(name = "disparo_email_15_dias_rp")
    private Boolean disparoEmail15DiasRp;
    @Column(name = "disparo_email_5_dias_rp")
    private Boolean disparoEmail5DiasRp;
    @Column(name = "disparo_email_30_dias_rf")
    private Boolean disparoEmail30DiasRf;
    @Column(name = "disparo_email_15_dias_rf")
    private Boolean disparoEmail15DiasRf;
    @Column(name = "disparo_email_5_dias_rf")
    private Boolean disparoEmail5DiasRf;
    @JoinColumn(name = "projeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projeto projeto;

    public AcompanhamentoProjeto6Meses1Ano() {
    }

    public AcompanhamentoProjeto6Meses1Ano(Integer id) {
        this.id = id;
    }

    public AcompanhamentoProjeto6Meses1Ano(Integer id, Date dataExpiracaoProjeto, Date dataExpiracaoRp, Date dataExpiracaoRf) {
        this.id = id;
        this.dataExpiracaoProjeto = dataExpiracaoProjeto;
        this.dataExpiracaoRp = dataExpiracaoRp;
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

    public Date getDataExpiracaoRp() {
        return dataExpiracaoRp;
    }

    public void setDataExpiracaoRp(Date dataExpiracaoRp) {
        this.dataExpiracaoRp = dataExpiracaoRp;
    }

    public Date getDataExpiracaoRf() {
        return dataExpiracaoRf;
    }

    public void setDataExpiracaoRf(Date dataExpiracaoRf) {
        this.dataExpiracaoRf = dataExpiracaoRf;
    }

    public Boolean getDisparoEmail30DiasRp() {
        return disparoEmail30DiasRp;
    }

    public void setDisparoEmail30DiasRp(Boolean disparoEmail30DiasRp) {
        this.disparoEmail30DiasRp = disparoEmail30DiasRp;
    }

    public Boolean getDisparoEmail15DiasRp() {
        return disparoEmail15DiasRp;
    }

    public void setDisparoEmail15DiasRp(Boolean disparoEmail15DiasRp) {
        this.disparoEmail15DiasRp = disparoEmail15DiasRp;
    }

    public Boolean getDisparoEmail5DiasRp() {
        return disparoEmail5DiasRp;
    }

    public void setDisparoEmail5DiasRp(Boolean disparoEmail5DiasRp) {
        this.disparoEmail5DiasRp = disparoEmail5DiasRp;
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
        if (!(object instanceof AcompanhamentoProjeto6Meses1Ano)) {
            return false;
        }
        AcompanhamentoProjeto6Meses1Ano other = (AcompanhamentoProjeto6Meses1Ano) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.AcompanhamentoProjeto6Meses1Ano[ id=" + id + " ]";
    }
    
}
