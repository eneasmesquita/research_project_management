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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eneas
 */
@Entity
@Table(catalog = "universidade", schema = "propes", name = "arquivo_modelo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArquivoModelo.findAll", query = "SELECT a FROM ArquivoModelo a"),
    @NamedQuery(name = "ArquivoModelo.findById", query = "SELECT a FROM ArquivoModelo a WHERE a.id = :id"),
    @NamedQuery(name = "ArquivoModelo.findByDescricao", query = "SELECT a FROM ArquivoModelo a WHERE a.descricao = :descricao"),
    @NamedQuery(name = "ArquivoModelo.findByExtensaoArquivo", query = "SELECT a FROM ArquivoModelo a WHERE a.extensaoArquivo = :extensaoArquivo")})
public class ArquivoModelo implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "extensao_arquivo")
    private String extensaoArquivo;

    public ArquivoModelo() {
    }

    public ArquivoModelo(Integer id) {
        this.id = id;
    }

    public ArquivoModelo(Integer id, String descricao, byte[] arquivo, String extensaoArquivo) {
        this.id = id;
        this.descricao = descricao;
        this.arquivo = arquivo;
        this.extensaoArquivo = extensaoArquivo;
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


    public String getExtensaoArquivo() {
        return extensaoArquivo;
    }

    public void setExtensaoArquivo(String extensaoArquivo) {
        this.extensaoArquivo = extensaoArquivo;
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
        if (!(object instanceof ArquivoModelo)) {
            return false;
        }
        ArquivoModelo other = (ArquivoModelo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.projetosUERR.propes.entities.ArquivoModelo[ id=" + id + " ]";
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }
    
}
