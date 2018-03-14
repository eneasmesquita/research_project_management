/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.ProjetoControlador;
import br.projetos.propes.entities.Projeto;
import br.projetos.propes.login.AutenticadorBean;
import br.projetos.propes.views.ViewControladorMB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "ProjetoPropesMB")
@RequestScoped
public class ProjetoPropesMB {

    Projeto projeto;
    List<Projeto> projetos = new ArrayList<Projeto>();
    List<Projeto> meusProjetos = new ArrayList<Projeto>();
    private boolean convenio = false;
    private boolean fomento = false;
    private StreamedContent projetoCompleto;
    private StreamedContent arquivoTermoResponsabilidade;
    private Integer instituicaoConveniada = 0;
    private Integer modalidade = 0;
    private Integer curso = 0;
    private Integer fomentador = 0;
    private ProjetoControlador controlador;
    private BigInteger valorFomento = BigInteger.ZERO;
    private UploadedFile arquivoProjeto;
    private UploadedFile arquivoTermo;
    AutenticadorBean autB = (AutenticadorBean) getManagedBean("LoginController");

    public ProjetoPropesMB() {
    }

    public List<Projeto> getProjetos() {
        if (projetos.isEmpty()) {
            try {
                controlador = new ProjetoControlador();
                projetos = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return projetos;
    }

    public List<Projeto> getMeusProjetos() {
        if (meusProjetos.isEmpty()) {
            try {
                controlador = new ProjetoControlador();
                meusProjetos = controlador.listarMeusProjetos();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return meusProjetos;
    }

    public UploadedFile getArquivoProjeto() {
        return arquivoProjeto;
    }

    public void setArquivoProjeto(UploadedFile arquivoProjeto) {
        this.arquivoProjeto = arquivoProjeto;
    }

    public UploadedFile getArquivoTermo() {
        return arquivoTermo;
    }

    public void setArquivoTermo(UploadedFile arquivoTermo) {
        this.arquivoTermo = arquivoTermo;
    }

    public void adicionar() throws Exception {

        if (arquivoProjeto != null && arquivoTermo != null) {
            if (!validarTitulo() || !validarFomento() || !validarTamanhoArquivoProjeto() || !validarTamanhoTermoReferencia() || !validarResumo() || !validarModalidade() || !validarCurso() || !validarArquivo(arquivoProjeto) || !validarArquivo(arquivoTermo)) {
            } else {
                this.projeto.setTitulo(projeto.getTitulo().toUpperCase());
                this.projeto.setResumo(projeto.getResumo().toUpperCase());
                this.projeto.setProjetoPdf(arquivoProjeto.getContents());
                int usuario = (Integer) autB.getSession().getAttribute("id_usuario");

                this.projeto.setDataCadastro(new Date());
                controlador = new ProjetoControlador();
                controlador.adicionar(projeto, usuario, instituicaoConveniada, modalidade, curso, fomentador, valorFomento, arquivoTermo.getContents());
                this.projeto = new Projeto();
                this.projetos.clear();
                this.instituicaoConveniada = 0;
                this.modalidade = 0;
                this.curso = 0;
                this.valorFomento = BigInteger.ZERO;
                ViewControladorMB vMB = (ViewControladorMB) getManagedBean("ViewControladorMB");
                vMB.setInstituicaoConveniada(false);
            }
        } else {
            validarTitulo();
            validarModalidade();
            validarCurso();
            validarResumo();
            if (arquivoProjeto == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione o arquivo .pdf que possui o projeto a ser cadastrado.", ""));
            }
            if (arquivoTermo == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione o arquivo .pdf que possui o termo de responsabilidade a ser cadastrado.", ""));
            }
        }
    }

    public void excluir() {
        controlador = new ProjetoControlador();
        controlador.excluir(projeto);
        this.projetos.clear();
    }

    public void alterar() {
        controlador = new ProjetoControlador();
        controlador.alterar(projeto);
        this.projetos.clear();
    }

    public Projeto getProjeto() {
        if (projeto == null) {
            projeto = new Projeto();
        }
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public StreamedContent getProjetoPdf() {
        InputStream stream = new ByteArrayInputStream(this.projeto.getProjetoPdf());
        projetoCompleto = new DefaultStreamedContent(stream, null, this.projeto.getTitulo() + ".pdf");
        return projetoCompleto;
    }

    public boolean validarArquivo(UploadedFile arquivo) {
        String nome = arquivo.getFileName();
        String extensao = nome.substring(nome.length() - 4, nome.length()); //.pdf
        if (extensao.equals(".pdf")) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Somente arquivo .pdf são permitidos!", ""));
            return false;
        }
    }

    public boolean validarTitulo() {
        if (projeto.getTitulo().isEmpty() || projeto.getTitulo() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insira uma descrição para o projeto!", ""));
            return false;
        } else {
            return true;
        }
    }
    
    public boolean validarFomento(){
        if(fomentador != 0 && valorFomento.intValue() == 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Agora insira o valor do fomento!", ""));
            return false;
        } else if (fomentador ==0 && valorFomento.intValue() != 0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Se possui fomentador, insira o valor do fomento!", ""));
            return false;
        } else {
            return true;
        }
    }

    public boolean validarModalidade() {
        if (getModalidade() == 0 || getModalidade() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o tipo de modalidade do projeto!", ""));
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCurso() {
        int curso_id = (Integer) autB.getSession().getAttribute("curso_id");
        if (curso_id != 0) {
            this.curso = curso_id;
        }
        if (getCurso() == 0 || getCurso() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o curso ao qual o projeto está vinculado.", ""));
            return false;
        } else {
            return true;
        }
    }

    public boolean validarTamanhoArquivoProjeto() {
        if (arquivoProjeto.getSize() <= 4096000) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "4Mb é o limite do arquivo para upload.", ""));
            return false;
        }
    }

    public boolean validarTamanhoTermoReferencia() {
        if (arquivoTermo.getSize() <= 1024000) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "1Mb é o limite do arquivo para upload.", ""));
            return false;
        }
    }

    public boolean validarResumo() {
        if (projeto.getResumo().isEmpty() || projeto.getResumo() == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insira um Resumo do projeto!", ""));
            return false;
        } else {
            return true;
        }
    }

    public boolean isConvenio() {
        return convenio;
    }

    public void setConvenio(boolean convenio) {
        this.convenio = convenio;
    }

    public StreamedContent getProjetoCompleto() {
        return projetoCompleto;
    }

    public StreamedContent getArquivoTermoResponsabilidade() {
        return arquivoTermoResponsabilidade;
    }

    public void setArquivoTermoResponsabilidade(StreamedContent arquivoTermoResponsabilidade) {
        this.arquivoTermoResponsabilidade = arquivoTermoResponsabilidade;
    }

    public void setProjetoCompleto(StreamedContent projetoCompleto) {
        this.projetoCompleto = projetoCompleto;
    }

    public Integer getInstituicaoConveniada() {
        if (instituicaoConveniada == null) {
            instituicaoConveniada = 0;
        }
        return instituicaoConveniada;
    }

    public void setInstituicaoConveniada(Integer instituicaoConveniada) {
        this.instituicaoConveniada = instituicaoConveniada;
    }

    public Integer getModalidade() {
        if (modalidade == null) {
            modalidade = 0;
        }
        return modalidade;
    }

    public void setModalidade(Integer modalidade) {
        this.modalidade = modalidade;
    }

    public Integer getCurso() {
        if (curso == null) {
            curso = 0;
        }
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public boolean isFomento() {
        return fomento;
    }

    public void setFomento(boolean fomento) {
        this.fomento = fomento;
    }

    public Integer getFomentador() {
        return fomentador;
    }

    public void setFomentador(Integer fomentador) {
        this.fomentador = fomentador;
    }

    public BigInteger getValorFomento() {
        return valorFomento;
    }

    public void setValorFomento(BigInteger valorFomento) {
        this.valorFomento = valorFomento;
    }

    public static Object getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object bean;
        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }
        if (bean == null) {
            throw new FacesException("Managed bean com o nome '" + beanName + "' não foi encontrado. "
                    + "Verifique o seu arquivo faces-config.xml ou anotação @ManagedBean.");
        }
        return bean;
    }
}
