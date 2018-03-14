/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author eneas
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "evento_sistema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoSistema.findAll", query = "SELECT e FROM EventoSistema e"),
    @NamedQuery(name = "EventoSistema.findById", query = "SELECT e FROM EventoSistema e WHERE e.id = :id"),
    @NamedQuery(name = "EventoSistema.findByDescricao", query = "SELECT e FROM EventoSistema e WHERE e.descricao = :descricao")})
public class EventoSistema implements Serializable {

    @Column(name = "periodo")
    private Integer periodo;
    @Column(name = "ordem")
    private Integer ordem;

    @OneToMany(mappedBy = "eventoSistema")
    private Collection<MovimentacaoProjeto> movimentacaoProjetoCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "descricao")
    private String descricao;

    public EventoSistema() {
    }

    public EventoSistema(Integer id) {
        this.id = id;
    }

    public EventoSistema(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        if (!(object instanceof EventoSistema)) {
            return false;
        }
        EventoSistema other = (EventoSistema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.EventoSistema[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<MovimentacaoProjeto> getMovimentacaoProjetoCollection() {
        return movimentacaoProjetoCollection;
    }

    public void setMovimentacaoProjetoCollection(Collection<MovimentacaoProjeto> movimentacaoProjetoCollection) {
        this.movimentacaoProjetoCollection = movimentacaoProjetoCollection;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }
    
}
