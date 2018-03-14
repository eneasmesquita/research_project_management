/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.ArquivoModeloControlador;
import br.projetos.propes.entities.ArquivoModelo;
import br.projetos.propes.entities.EventoAluno;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "ArquivoModeloPropesMB")
@RequestScoped
public class ArquivoModeloPropesMB {

    private ArquivoModeloControlador controlador;
    private ArquivoModelo arquivoModelo;
    private UploadedFile arquivoUp;
    private StreamedContent arquivoDown;
    private List<ArquivoModelo> eventosMovimentacaoProjeto = new ArrayList<ArquivoModelo>();
    private List<EventoAluno> eventosMovimentacaoMeusProjeto = new ArrayList<EventoAluno>();
    private List<ArquivoModelo> arquivosModelo = new ArrayList<ArquivoModelo>();
    private int projetoId = 0;
    private boolean eventoModelo = false;

    public ArquivoModeloPropesMB() {
    }

    public List<ArquivoModelo> getArquivosModelo() {
        if (arquivosModelo.isEmpty()) {
            try {
                controlador = new ArquivoModeloControlador();
                arquivosModelo = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return arquivosModelo;
    }

    public void adicionar() throws Exception {
        if (!validarDescricao() || !validarArquivo()) {
        } else {
            this.arquivoModelo.setDescricao(arquivoModelo.getDescricao().toUpperCase());
            this.arquivoModelo.setArquivo(arquivoUp.getContents());
            controlador = new ArquivoModeloControlador();
            controlador.adicionar(this.arquivoModelo);
            this.arquivoModelo = new ArquivoModelo();
            this.arquivosModelo.clear();
        }
    }

    public void excluir() {
        controlador = new ArquivoModeloControlador();
        controlador.excluir(arquivoModelo);
        this.arquivosModelo.clear();
    }

    public void alterar() {
        controlador = new ArquivoModeloControlador();
        controlador.alterar(arquivoModelo);
        this.arquivosModelo.clear();
    }

    public StreamedContent getArquivoDown() {
        InputStream stream = new ByteArrayInputStream(this.arquivoModelo.getArquivo());
        arquivoDown = new DefaultStreamedContent(stream, null, this.arquivoModelo.getDescricao() + this.arquivoModelo.getExtensaoArquivo());
        return arquivoDown;
    }

    public boolean validarExtArquivo(UploadedFile arquivo) {

        String nome = arquivo.getFileName();
        String extensao = nome.substring(nome.length() - 4, nome.length()); //.odt e .doc
        String extensao2 = nome.substring(nome.length() - 5, nome.length()); //.docx

        if (extensao.equals(".odt") || extensao.equals(".doc") || extensao2.equals(".docx")) {
            if (extensao2.equals(".docx")) {
                this.arquivoModelo.setExtensaoArquivo(extensao2);
            } else {
                this.arquivoModelo.setExtensaoArquivo(extensao);
            }
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Somente arquivos .odt/.doc/.docx", ""));
            return false;
        }
    }

    public boolean validarDescricao() {
        if (!arquivoModelo.getDescricao().isEmpty() || arquivoModelo.getDescricao() != null) {

            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insira uma descrição para o arquivo modelo!", ""));
            return false;
        }
    }

    public boolean validarArquivo() {
        if (arquivoUp != null) {
            if (!validarExtArquivo(arquivoUp) || !validarTamanhoArquivo()) {
                return false;
            } else {
                return true;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum arquivo foi selecionado para upload!", ""));
            return false;
        }
    }

    public boolean validarTamanhoArquivo() {
        if (arquivoUp.getSize() <= 2048000) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "2Mb é o limite do arquivo para upload.", ""));
            return false;
        }
    }

    public ArquivoModelo getArquivoModelo() {
        if (arquivoModelo == null) {
            arquivoModelo = new ArquivoModelo();
        }
        return arquivoModelo;
    }

    public void setArquivoModelo(ArquivoModelo arquivoModelo) {
        this.arquivoModelo = arquivoModelo;
    }
    
    public UploadedFile getArquivoUp() {
        return arquivoUp;
    }

    public void setArquivoUp(UploadedFile arquivo) {
        this.arquivoUp = arquivo;
    }

}
