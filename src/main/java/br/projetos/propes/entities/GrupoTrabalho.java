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
@Table(catalog = "universidade", schema = "propes", name = "grupo_trabalho")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GrupoTrabalho.findAll", query = "SELECT g FROM GrupoTrabalho g"),
    @NamedQuery(name = "GrupoTrabalho.findById", query = "SELECT g FROM GrupoTrabalho g WHERE g.id = :id"),
    @NamedQuery(name = "GrupoTrabalho.findByDescricao", query = "SELECT g FROM GrupoTrabalho g WHERE g.descricao = :descricao")})
public class GrupoTrabalho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupoTrabalho")
    private Collection<IntegrantesGrupo> integrantesGrupoCollection;
    @JoinColumn(name = "coordenador", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario coordenador;

    public GrupoTrabalho() {
    }

    public GrupoTrabalho(Integer id) {
        this.id = id;
    }

    public GrupoTrabalho(Integer id, String descricao) {
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
    public Collection<IntegrantesGrupo> getIntegrantesGrupoCollection() {
        return integrantesGrupoCollection;
    }

    public void setIntegrantesGrupoCollection(Collection<IntegrantesGrupo> integrantesGrupoCollection) {
        this.integrantesGrupoCollection = integrantesGrupoCollection;
    }

    public Usuario getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Usuario coordenador) {
        this.coordenador = coordenador;
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
        if (!(object instanceof GrupoTrabalho)) {
            return false;
        }
        GrupoTrabalho other = (GrupoTrabalho) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.GrupoTrabalho[ id=" + id + " ]";
    }
    
}
