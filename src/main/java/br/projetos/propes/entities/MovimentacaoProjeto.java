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
import javax.persistence.Lob;
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
 * @author Eneas Mesquita
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "movimentacao_projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimentacaoProjeto.findAll", query = "SELECT m FROM MovimentacaoProjeto m"),
    @NamedQuery(name = "MovimentacaoProjeto.findById", query = "SELECT m FROM MovimentacaoProjeto m WHERE m.id = :id"),
    @NamedQuery(name = "MovimentacaoProjeto.findByData", query = "SELECT m FROM MovimentacaoProjeto m WHERE m.data = :data")})
public class MovimentacaoProjeto implements Serializable {

    @Lob
    @Column(name = "arquivo_vinculado")
    private byte[] arquivoVinculado;
    @JoinColumn(name = "evento_colaborador", referencedColumnName = "id")
    @ManyToOne
    private EventoColaborador eventoColaborador;
    @JoinColumn(name = "evento_aluno", referencedColumnName = "id")
    @ManyToOne
    private EventoAluno eventoAluno;
    @JoinColumn(name = "evento_sistema", referencedColumnName = "id")
    @ManyToOne
    private EventoSistema eventoSistema;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @JoinColumn(name = "responsavel", referencedColumnName = "id")
//    @ManyToOne(optional = false)
    @ManyToOne
    private Usuario responsavel;
    @JoinColumn(name = "projeto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Projeto projeto;

    public MovimentacaoProjeto() {
    }

    public MovimentacaoProjeto(Integer id) {
        this.id = id;
    }

    public MovimentacaoProjeto(Integer id, Date data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public byte[] getArquivoVinculado() {
        return arquivoVinculado;
    }

    public void setArquivoVinculado(byte[] arquivoVinculado) {
        this.arquivoVinculado = arquivoVinculado;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
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
        if (!(object instanceof MovimentacaoProjeto)) {
            return false;
        }
        MovimentacaoProjeto other = (MovimentacaoProjeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.MovimentacaoProjeto[ id=" + id + " ]";
    }

    public EventoAluno getEventoAluno() {
        return eventoAluno;
    }

    public void setEventoAluno(EventoAluno eventoAluno) {
        this.eventoAluno = eventoAluno;
    }

    public EventoSistema getEventoSistema() {
        return eventoSistema;
    }

    public void setEventoSistema(EventoSistema eventoSistema) {
        this.eventoSistema = eventoSistema;
    }

    public EventoColaborador getEventoColaborador() {
        return eventoColaborador;
    }

    public void setEventoColaborador(EventoColaborador eventoColaborador) {
        this.eventoColaborador = eventoColaborador;
    }

}
