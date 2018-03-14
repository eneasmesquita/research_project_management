/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import static br.projetos.propes.beans.ProjetoPropesMB.getManagedBean;
import br.projetos.propes.controllers.MovimentacaoControlador;
import br.projetos.propes.entities.MovimentacaoProjeto;
import br.projetos.propes.login.AutenticadorBean;
import br.projetos.propes.views.ViewControladorMB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "MovimentacaoProjetoPropesMB")
@SessionScoped
public class MovimentacaoPropesMB {

    private List<MovimentacaoProjeto> movimentacoes = new ArrayList<MovimentacaoProjeto>();
    private List<MovimentacaoProjeto> movimentacoesProjeto = new ArrayList<MovimentacaoProjeto>();
    private MovimentacaoProjeto movimentacao;
    private MovimentacaoControlador controlador;
    private StreamedContent anexo;
    AutenticadorBean autB = (AutenticadorBean) getManagedBean("LoginController");
    private int projeto = 0;
    private String projetoTitulo = "";
    private int evento = 0;
    private UploadedFile arquivoUp;
    private StreamedContent arquivoDown;
    private boolean arquivoVinculado = false;
    private boolean bloqueioAcaoAluno = false;
    private boolean notificarAlunoPesquisador = true;

    public MovimentacaoPropesMB() {
    }

    public List<MovimentacaoProjeto> getMovimentacoes() {
        if (movimentacoes.isEmpty()) {
            try {
                controlador = new MovimentacaoControlador();
                movimentacoes = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return movimentacoes;
    }

    public List<MovimentacaoProjeto> getMovimentacoesProjeto() {

        //AQUI QUE É VERIFICADO O CALCULO DE DATA PARA OS RELATORIOS CASO O PROJETO ESTEJA EM EXECUÇÃO
        try {
            controlador = new MovimentacaoControlador();
            movimentacoesProjeto = controlador.listar(projeto);
            int ocorrencia = 0;
            for (MovimentacaoProjeto m : movimentacoesProjeto) {
                try {
                    if (m.getEventoColaborador().getDescricao().contains("PROJETO EM EXECUÇÃO")) {
                        ocorrencia++;
                    }
                } catch (NullPointerException npe) {
                }
            }
            if (ocorrencia != 0) {
                //O ALUNO TEM PERMISSÃO PARA MOVIMENTAR SEU PROJETO
                bloqueioAcaoAluno = false;
            } else {
                //O ALUNO NÃO TEM PERMISSÃO PARA MOVIMENTAR SEU PROJETO
                bloqueioAcaoAluno = true;
            }
            return movimentacoesProjeto;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void adicionar() throws Exception {
        //O METODO DO MB SO E CHAMADO COM A INTERACAO DO USUARIO
        //O SISTEMA SO ATIVA O METODO DO CONTROLADOR
        int usuario = (Integer) autB.getSession().getAttribute("id_usuario");
        int tipo_usuario = (Integer) autB.getSession().getAttribute("tipo");

        if (arquivoUp != null) {
            if (!validarArquivo(arquivoUp) || !validarTamanhoArquivo() /*verificarAnexoEvento()*/) {
            } else //CONDICAO QUE OBRIGA O USUARIO A INSERIR O EVENTO
            if (evento != 0) {
                controlador = new MovimentacaoControlador();
                //APENAS DUAS CONDICOES POIS DISCARTA O PARAMETRO eventoSistema
                if (tipo_usuario == 2) {
                    controlador.adicionar(projeto, 0, 0, evento, usuario, arquivoUp.getContents(), false);
                } else {
                    controlador.adicionar(projeto, 0, evento, 0, usuario, arquivoUp.getContents(), notificarAlunoPesquisador);
                }
                this.movimentacao = new MovimentacaoProjeto();
                this.movimentacoes.clear();
                evento = 0;
                notificarAlunoPesquisador = true;
                //DEPURAR ESTE TRECHO DE CODIGO
                ViewControladorMB vMB = (ViewControladorMB) getManagedBean("ViewControladorMB");
                vMB.setArquivoVinculadoMovimentacao(false);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe um evento para a nova movimentação do projeto!", ""));
            }
        } else if (evento != 0) {
            controlador = new MovimentacaoControlador();
            if (tipo_usuario == 2) {
                controlador.adicionar(projeto, 0, 0, evento, usuario, null, false);
            } else {
                controlador.adicionar(projeto, 0, evento, 0, usuario, null, notificarAlunoPesquisador);
            }
            this.movimentacao = new MovimentacaoProjeto();
            this.movimentacoes.clear();
            evento = 0;
            notificarAlunoPesquisador = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe um evento para a nova movimentação do projeto!", ""));
        }
    }

    public void excluir() {
        controlador = new MovimentacaoControlador();
        controlador.excluir(movimentacao);
        this.movimentacoes.clear();
        this.movimentacao = new MovimentacaoProjeto();
    }

    public void alterar() {
        controlador = new MovimentacaoControlador();
        controlador.alterar(movimentacao);
        this.movimentacoes.clear();
    }

    public MovimentacaoProjeto getMovimentacaoProjeto() {
        if (movimentacao == null) {
            movimentacao = new MovimentacaoProjeto();
        }
        return movimentacao;
    }

    public void setMovimentacaoProjeto(MovimentacaoProjeto movimentacao) {
        this.movimentacao = movimentacao;
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

    public boolean validarTamanhoArquivo() {
        if (arquivoUp.getSize() <= 1024000) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "1Mb é o limite do arquivo para upload.", ""));
            return false;
        }
    }

    public StreamedContent getAnexo() {
        return anexo;
    }

    public void setAnexo(StreamedContent anexo) {
        this.anexo = anexo;
    }

    //NÃO FOI TRATADO COM IF DEVIDO A FALTA DE TRATAMENTO DE NullPointerException
    //UMA DAS TRES TENTATIVAS ABAIXO RETORNARA O RESULTADO DESEJADO
    public StreamedContent getAnexoPdf() {
        InputStream stream = new ByteArrayInputStream(this.movimentacao.getArquivoVinculado());

        try {
            anexo = new DefaultStreamedContent(stream, null, this.movimentacao.getEventoAluno().getDescricao() + ".pdf");
        } catch (NullPointerException e) {
        } catch (NoResultException e) {
        }

        try {
            anexo = new DefaultStreamedContent(stream, null, this.movimentacao.getEventoColaborador().getDescricao() + ".pdf");
        } catch (NullPointerException e) {
        } catch (NoResultException e) {
        }

        try {
            anexo = new DefaultStreamedContent(stream, null, this.movimentacao.getEventoSistema().getDescricao() + ".pdf");
        } catch (NullPointerException e) {
        } catch (NoResultException e) {
        }

        return anexo;
    }

    public int getProjeto() {
        return projeto;
    }

    public void setProjeto(int projeto) {
        this.projeto = projeto;
    }

    public int getEvento() {
        return evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }

    public UploadedFile getArquivoUp() {
        return arquivoUp;
    }

    public void setArquivoUp(UploadedFile arquivoUp) {
        this.arquivoUp = arquivoUp;
    }

    public StreamedContent getArquivoDown() {
        return arquivoDown;
    }

    public void setArquivoDown(StreamedContent arquivoDown) {
        this.arquivoDown = arquivoDown;
    }

    public void setTramitacoes(List<MovimentacaoProjeto> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public boolean isArquivoVinculado() {
        return arquivoVinculado;
    }

    public void setArquivoVinculado(boolean arquivoVinculado) {
        this.arquivoVinculado = arquivoVinculado;
    }

    public String getProjetoTitulo() {
        return projetoTitulo;
    }

    public void setProjetoTitulo(String projetoTitulo) {
        this.projetoTitulo = projetoTitulo;
    }

    public boolean isBloqueioAcaoAluno() {
        return bloqueioAcaoAluno;
    }

    public void setBloqueioAcaoAluno(boolean bloqueioAcaoAluno) {
        this.bloqueioAcaoAluno = bloqueioAcaoAluno;
    }

    public boolean isNotificarAlunoPesquisador() {
        return notificarAlunoPesquisador;
    }

    public void setNotificarAlunoPesquisador(boolean notificarAlunoPesquisador) {
        this.notificarAlunoPesquisador = notificarAlunoPesquisador;
    }

}
