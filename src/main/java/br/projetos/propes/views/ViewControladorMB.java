package br.projetos.propes.views;

import br.projetos.propes.beans.EventoPropesMB;
import br.projetos.propes.beans.MovimentacaoPropesMB;
import br.projetos.propes.beans.ProjetoPropesMB;
import br.projetos.propes.beans.UsuarioPropesMB;
import java.util.Date;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "ViewControladorMB")
@ViewScoped
public class ViewControladorMB {

    public ViewControladorMB() {
    }

    private boolean arquivos = false;
    private boolean projetos = false;
    private boolean movimentacoes = false;
    private boolean movimentacoesMeusProjetos = false;
    private boolean listarProjetos = true;
    private boolean curso = false;
    private boolean instituicaoConveniada = false;
    private boolean arquivoVinculadoMovimentacao = false;
    private boolean fomento = false;
    private boolean botaoFormEvento = true; //disable=true
    private boolean meusProjetos = false;
    private boolean relatorioQuantitativo = false;
    private boolean relacaoProjetoPesquisador = false;
    private boolean arquivoModelo = false;
    private Date dataAtual;

    public void exibirMeusDados() {
        this.meusProjetos = false;
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }
    
    public void exibirMeusProjetos() {
        this.meusProjetos = true;
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }

    public void exibirArquivos() {
        this.arquivos = true;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }

    public void exibirListarProjetos() {
        this.arquivos = false;
        this.listarProjetos = true;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }

    public void exibirEventos() {
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }

    public void exibirProjetos() {
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = true;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }

    public void exibirMovimentacoes() {
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = true;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }

    public void exibirMovimentacoesMeusProjetos() {
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = true;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = false;
    }
    
    public void exibirRelatorioQuantitativo() {
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = true;
        this.relacaoProjetoPesquisador = false;
    }
    
    public void exibirRelacaoProjetoPesquisador() {
        this.arquivos = false;
        this.listarProjetos = false;
        this.projetos = false;
        this.movimentacoes = false;
        this.movimentacoesMeusProjetos = false;
        this.meusProjetos = false;
        this.relatorioQuantitativo = false;
        this.relacaoProjetoPesquisador = true;
    }

    public void exibirCurso(SelectEvent e) {
        UsuarioPropesMB uMB = (UsuarioPropesMB) getManagedBean("UsuarioPropesMB");
        int tipoUsuarioId = uMB.getTipoUsuario();
        if (tipoUsuarioId == 2) {
            this.curso = true;
        } else {
            this.curso = false;
        }
    }

    public void exibirInstituicaoConveniada(SelectEvent e) {
        ProjetoPropesMB pMB = (ProjetoPropesMB) getManagedBean("ProjetoPropesMB");
        if (pMB.isConvenio()) {
            this.instituicaoConveniada = true;
        } else {
            this.instituicaoConveniada = false;
        }
    }

    public void exibirFomento(SelectEvent e) {
        ProjetoPropesMB pMB = (ProjetoPropesMB) getManagedBean("ProjetoPropesMB");
        if (pMB.isFomento()) {
            this.fomento = true;
        } else {
            this.fomento = false;
        }
    }

    public void exibirOpcaoVincularArquivoMovimentacao(SelectEvent e) {
        MovimentacaoPropesMB mMB = (MovimentacaoPropesMB) getManagedBean("MovimentacaoPropesMB");
        if (mMB.isArquivoVinculado()) {
            this.arquivoVinculadoMovimentacao = true;
        } else {
            this.arquivoVinculadoMovimentacao = false;
        }
    }

    public void checarPreenchimentoDescricao() {
        System.out.println("SENDO EXECUTADO!!");
        EventoPropesMB eMB = (EventoPropesMB) getManagedBean("EventoPropesMB");
        if (!eMB.getEvento().getDescricao().isEmpty() || !eMB.getEvento().getDescricao().equals("") || eMB.getEvento().getDescricao() != null) {
            botaoFormEvento = false;
        } else {
            botaoFormEvento = true;
        }
    }

    public void prepararFormNovoUsuario() {
        this.curso = false;
    }

    public String logout() {
        return "/login.xhtml?faces-redirect=true";
    }

    public String login() {
//        return "/index.xhtml?faces-redirect=true";
        return "/PROPES/index_propes.xhtml?faces-redirect=true";
    }

    public String voltarIndex() {
        return "/index.xhtml?faces-redirect=true";
    }

    public boolean isArquivos() {
        return arquivos;
    }

    public boolean isFomento() {
        return fomento;
    }

    public void setFomento(boolean fomento) {
        this.fomento = fomento;
    }

    public void setArquivos(boolean arquivos) {
        this.arquivos = arquivos;
    }

    public boolean isBotaoFormEvento() {
        return botaoFormEvento;
    }

    public void setBotaoFormEvento(boolean botaoFormEvento) {
        this.botaoFormEvento = botaoFormEvento;
    }

    public boolean isListarProjetos() {
        return listarProjetos;
    }

    public void setListarProjetos(boolean listarProjetos) {
        this.listarProjetos = listarProjetos;
    }

    public boolean isCurso() {
        return curso;
    }

    public void setCurso(boolean curso) {
        this.curso = curso;
    }

    public boolean isInstituicaoConveniada() {
        return instituicaoConveniada;
    }

    public void setInstituicaoConveniada(boolean instituicaoConveniada) {
        this.instituicaoConveniada = instituicaoConveniada;
    }

    public boolean isArquivoVinculadoMovimentacao() {
        return arquivoVinculadoMovimentacao;
    }

    public void setArquivoVinculadoMovimentacao(boolean arquivoVinculadoMovimentacao) {
        this.arquivoVinculadoMovimentacao = arquivoVinculadoMovimentacao;
    }

    public boolean isProjetos() {
        return projetos;
    }

    public void setProjetos(boolean projetos) {
        this.projetos = projetos;
    }

    public boolean isMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(boolean movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public boolean isMeusProjetos() {
        return meusProjetos;
    }

    public void setMeusProjetos(boolean meusProjetos) {
        this.meusProjetos = meusProjetos;
    }

    public boolean isMovimentacoesMeusProjetos() {
        return movimentacoesMeusProjetos;
    }

    public void setMovimentacoesMeusProjetos(boolean movimentacoesMeusProjetos) {
        this.movimentacoesMeusProjetos = movimentacoesMeusProjetos;
    }

    public boolean isRelatorioQuantitativo() {
        return relatorioQuantitativo;
    }

    public void setRelatorioQuantitativo(boolean relatorioQuantitativo) {
        this.relatorioQuantitativo = relatorioQuantitativo;
    }

    public Date getDataAtual() {
        dataAtual = new Date();
        return dataAtual;
    }

    public boolean isArquivoModelo() {
        return arquivoModelo;
    }

    public void setArquivoModelo(boolean arquivoModelo) {
        this.arquivoModelo = arquivoModelo;
    }

    public boolean isRelacaoProjetoPesquisador() {
        return relacaoProjetoPesquisador;
    }

    public void setRelacaoProjetoPesquisador(boolean relacaoProjetoPesquisador) {
        this.relacaoProjetoPesquisador = relacaoProjetoPesquisador;
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
