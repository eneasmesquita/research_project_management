/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eneas Mesquita
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "integrantes_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IntegrantesGrupo.findAll", query = "SELECT i FROM IntegrantesGrupo i"),
    @NamedQuery(name = "IntegrantesGrupo.findById", query = "SELECT i FROM IntegrantesGrupo i WHERE i.id = :id")})
public class IntegrantesGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "grupo_trabalho", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private GrupoTrabalho grupoTrabalho;

    public IntegrantesGrupo() {
    }

    public IntegrantesGrupo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public GrupoTrabalho getGrupoTrabalho() {
        return grupoTrabalho;
    }

    public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
        this.grupoTrabalho = grupoTrabalho;
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
        if (!(object instanceof IntegrantesGrupo)) {
            return false;
        }
        IntegrantesGrupo other = (IntegrantesGrupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.IntegrantesGrupo[ id=" + id + " ]";
    }
    
}
