/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Eneas Mesquita
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "modalidade_projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModalidadeProjeto.findAll", query = "SELECT m FROM ModalidadeProjeto m"),
    @NamedQuery(name = "ModalidadeProjeto.findById", query = "SELECT m FROM ModalidadeProjeto m WHERE m.id = :id"),
    @NamedQuery(name = "ModalidadeProjeto.findByDescricao", query = "SELECT m FROM ModalidadeProjeto m WHERE m.descricao = :descricao")})
public class ModalidadeProjeto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modalidade")
    private Collection<Projeto> projetoCollection;
    @JoinColumn(name = "periodo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Periodo periodo;

    public ModalidadeProjeto() {
    }

    public ModalidadeProjeto(Integer id) {
        this.id = id;
    }

    public ModalidadeProjeto(Integer id, String descricao, Periodo periodo) {
        this.id = id;
        this.descricao = descricao;
        this.periodo = periodo;
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
    public Collection<Projeto> getProjetoCollection() {
        return projetoCollection;
    }

    public void setProjetoCollection(Collection<Projeto> projetoCollection) {
        this.projetoCollection = projetoCollection;
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
        if (!(object instanceof ModalidadeProjeto)) {
            return false;
        }
        ModalidadeProjeto other = (ModalidadeProjeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.ModalidadeProjeto[ id=" + id + " ]";
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

}
