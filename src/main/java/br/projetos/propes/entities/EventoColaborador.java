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
 * @author Eneas
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "evento_colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoColaborador.findAll", query = "SELECT e FROM EventoColaborador e"),
    @NamedQuery(name = "EventoColaborador.findById", query = "SELECT e FROM EventoColaborador e WHERE e.id = :id"),
    @NamedQuery(name = "EventoColaborador.findByDescricao", query = "SELECT e FROM EventoColaborador e WHERE e.descricao = :descricao")})
public class EventoColaborador implements Serializable {

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
    @OneToMany(mappedBy = "eventoColaborador")
    private Collection<MovimentacaoProjeto> movimentacaoProjetoCollection;

    public EventoColaborador() {
    }

    public EventoColaborador(Integer id) {
        this.id = id;
    }

    public EventoColaborador(Integer id, String descricao) {
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

    @XmlTransient
    public Collection<MovimentacaoProjeto> getMovimentacaoProjetoCollection() {
        return movimentacaoProjetoCollection;
    }

    public void setMovimentacaoProjetoCollection(Collection<MovimentacaoProjeto> movimentacaoProjetoCollection) {
        this.movimentacaoProjetoCollection = movimentacaoProjetoCollection;
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
        if (!(object instanceof EventoColaborador)) {
            return false;
        }
        EventoColaborador other = (EventoColaborador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.EventoColaborador[ id=" + id + " ]";
    }
    
}
