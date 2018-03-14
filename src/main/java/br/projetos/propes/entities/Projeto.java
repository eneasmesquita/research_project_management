/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eneas Mesquita
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "projeto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p"),
    @NamedQuery(name = "Projeto.findById", query = "SELECT p FROM Projeto p WHERE p.id = :id"),
    @NamedQuery(name = "Projeto.findByTitulo", query = "SELECT p FROM Projeto p WHERE p.titulo = :titulo"),
    @NamedQuery(name = "Projeto.findByResumo", query = "SELECT p FROM Projeto p WHERE p.resumo = :resumo")})
public class Projeto implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "projeto_pdf")
    private byte[] projetoPdf;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "projeto")
    private AcompanhamentoProjeto18Meses acompanhamentoProjeto18Meses;
    @JoinColumn(name = "fomentador", referencedColumnName = "id")
    @ManyToOne
    private Fomentador fomentador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto30Meses> acompanhamentoProjeto30MesesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto4Anos> acompanhamentoProjeto4AnosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto18Meses> acompanhamentoProjeto18MesesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto2Anos> acompanhamentoProjeto2AnosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto6Meses1Ano> acompanhamentoProjeto6Meses1AnoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto3Anos> acompanhamentoProjeto3AnosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<AcompanhamentoProjeto42Meses> acompanhamentoProjeto42MesesCollection;
    @Column(name = "valor_fomento")
    private BigInteger valorFomento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "resumo")
    private String resumo;
    @JoinColumn(name = "responsavel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario responsavel;
    @JoinColumn(name = "modalidade", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ModalidadeProjeto modalidade;
    @JoinColumn(name = "instituicao_convenio", referencedColumnName = "id")
    @ManyToOne
    private InstituicaoConvenio instituicaoConvenio;
    @JoinColumn(name = "curso", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Curso curso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projeto")
    private Collection<MovimentacaoProjeto> movimentacaoProjetoCollection;

    public Projeto() {
    }

    public Projeto(Integer id) {
        this.id = id;
    }

    public Projeto(Integer id, String titulo, String resumo, byte[] projetoPdf, byte[] termoResponsabilidade, Date dataCadastro) {
        this.id = id;
        this.titulo = titulo;
        this.resumo = resumo;
        this.projetoPdf = projetoPdf;
        this.dataCadastro = dataCadastro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public byte[] getProjetoPdf() {
        return projetoPdf;
    }

    public void setProjetoPdf(byte[] projetoPdf) {
        this.projetoPdf = projetoPdf;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public ModalidadeProjeto getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeProjeto modalidade) {
        this.modalidade = modalidade;
    }

    public InstituicaoConvenio getInstituicaoConvenio() {
        return instituicaoConvenio;
    }

    public void setInstituicaoConvenio(InstituicaoConvenio instituicaoConvenio) {
        this.instituicaoConvenio = instituicaoConvenio;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
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
        if (!(object instanceof Projeto)) {
            return false;
        }
        Projeto other = (Projeto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.Projeto[ id=" + id + " ]";
    }

    public BigInteger getValorFomento() {
        return valorFomento;
    }

    public void setValorFomento(BigInteger valorFomento) {
        this.valorFomento = valorFomento;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto30Meses> getAcompanhamentoProjeto30MesesCollection() {
        return acompanhamentoProjeto30MesesCollection;
    }

    public void setAcompanhamentoProjeto30MesesCollection(Collection<AcompanhamentoProjeto30Meses> acompanhamentoProjeto30MesesCollection) {
        this.acompanhamentoProjeto30MesesCollection = acompanhamentoProjeto30MesesCollection;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto4Anos> getAcompanhamentoProjeto4AnosCollection() {
        return acompanhamentoProjeto4AnosCollection;
    }

    public void setAcompanhamentoProjeto4AnosCollection(Collection<AcompanhamentoProjeto4Anos> acompanhamentoProjeto4AnosCollection) {
        this.acompanhamentoProjeto4AnosCollection = acompanhamentoProjeto4AnosCollection;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto18Meses> getAcompanhamentoProjeto18MesesCollection() {
        return acompanhamentoProjeto18MesesCollection;
    }

    public void setAcompanhamentoProjeto18MesesCollection(Collection<AcompanhamentoProjeto18Meses> acompanhamentoProjeto18MesesCollection) {
        this.acompanhamentoProjeto18MesesCollection = acompanhamentoProjeto18MesesCollection;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto2Anos> getAcompanhamentoProjeto2AnosCollection() {
        return acompanhamentoProjeto2AnosCollection;
    }

    public void setAcompanhamentoProjeto2AnosCollection(Collection<AcompanhamentoProjeto2Anos> acompanhamentoProjeto2AnosCollection) {
        this.acompanhamentoProjeto2AnosCollection = acompanhamentoProjeto2AnosCollection;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto6Meses1Ano> getAcompanhamentoProjeto6Meses1AnoCollection() {
        return acompanhamentoProjeto6Meses1AnoCollection;
    }

    public void setAcompanhamentoProjeto6Meses1AnoCollection(Collection<AcompanhamentoProjeto6Meses1Ano> acompanhamentoProjeto6Meses1AnoCollection) {
        this.acompanhamentoProjeto6Meses1AnoCollection = acompanhamentoProjeto6Meses1AnoCollection;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto3Anos> getAcompanhamentoProjeto3AnosCollection() {
        return acompanhamentoProjeto3AnosCollection;
    }

    public void setAcompanhamentoProjeto3AnosCollection(Collection<AcompanhamentoProjeto3Anos> acompanhamentoProjeto3AnosCollection) {
        this.acompanhamentoProjeto3AnosCollection = acompanhamentoProjeto3AnosCollection;
    }

    @XmlTransient
    public Collection<AcompanhamentoProjeto42Meses> getAcompanhamentoProjeto42MesesCollection() {
        return acompanhamentoProjeto42MesesCollection;
    }

    public void setAcompanhamentoProjeto42MesesCollection(Collection<AcompanhamentoProjeto42Meses> acompanhamentoProjeto42MesesCollection) {
        this.acompanhamentoProjeto42MesesCollection = acompanhamentoProjeto42MesesCollection;
    }
    
    public Fomentador getFomentador() {
        return fomentador;
    }

    public void setFomentador(Fomentador fomentador) {
        this.fomentador = fomentador;
    }

    public AcompanhamentoProjeto18Meses getAcompanhamentoProjeto18Meses() {
        return acompanhamentoProjeto18Meses;
    }

    public void setAcompanhamentoProjeto18Meses(AcompanhamentoProjeto18Meses acompanhamentoProjeto18Meses) {
        this.acompanhamentoProjeto18Meses = acompanhamentoProjeto18Meses;
    }
    
}
