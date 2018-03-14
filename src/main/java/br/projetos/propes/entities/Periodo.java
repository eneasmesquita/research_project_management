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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eneas
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p"),
    @NamedQuery(name = "Periodo.findById", query = "SELECT p FROM Periodo p WHERE p.id = :id"),
    @NamedQuery(name = "Periodo.findByMeses", query = "SELECT p FROM Periodo p WHERE p.meses = :meses")})
public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "meses")
    private int meses;
    @OneToMany(mappedBy = "periodo")
    private Collection<EventoAluno> eventoAlunoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
    private Collection<ModalidadeProjeto> modalidadeProjetoCollection;

    public Periodo() {
    }

    public Periodo(Integer id) {
        this.id = id;
    }

    public Periodo(Integer id, int meses) {
        this.id = id;
        this.meses = meses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    @XmlTransient
    public Collection<EventoAluno> getEventoAlunoCollection() {
        return eventoAlunoCollection;
    }

    public void setEventoAlunoCollection(Collection<EventoAluno> eventoAlunoCollection) {
        this.eventoAlunoCollection = eventoAlunoCollection;
    }

    @XmlTransient
    public Collection<ModalidadeProjeto> getModalidadeProjetoCollection() {
        return modalidadeProjetoCollection;
    }

    public void setModalidadeProjetoCollection(Collection<ModalidadeProjeto> modalidadeProjetoCollection) {
        this.modalidadeProjetoCollection = modalidadeProjetoCollection;
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
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.Periodo[ id=" + id + " ]";
    }
    
}
