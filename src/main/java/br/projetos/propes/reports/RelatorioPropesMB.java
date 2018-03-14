package br.projetos.propes.reports;

import br.projetos.propes.controllers.UsuarioControlador;
import br.projetos.propes.entities.Projeto;
import br.projetos.propes.entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.persistence.NoResultException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "RelatorioPropesMB")
public class RelatorioPropesMB implements Serializable {

    private RelatorioControlador controlador;
    private List<ResumoConveniosProjetos> resumoConveniosProjetos = new ArrayList<ResumoConveniosProjetos>();
    private List<ResumoModalidadeProjetos> resumoModalidadeProjetos = new ArrayList<ResumoModalidadeProjetos>();
    private List<ResumoFomentoProjetos> resumoFomentadores = new ArrayList<ResumoFomentoProjetos>();
    private List<Projeto> resumoProjetosComFomento = new ArrayList<Projeto>();
    private List<Projeto> projetosDeAluno = new ArrayList<Projeto>();
    private List<Projeto> novosProjetosAdded = new ArrayList<Projeto>();
    private int idAlunoPesquisador = 0;
    private String nomeAlunoPesquisador = "";
    private long totalFomento = 0;
    private long totalConvenios = 0;

    public RelatorioPropesMB() {
    }

    public List<ResumoConveniosProjetos> getResumoConveniosProjetos() {
        if (resumoConveniosProjetos.isEmpty()) {
            try {
                controlador = new RelatorioControlador();
                resumoConveniosProjetos = controlador.preencherResumoConveniosProjetos();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return resumoConveniosProjetos;
    }

    public List<ResumoModalidadeProjetos> getResumoModalidadeProjetos() {
        if (resumoModalidadeProjetos.isEmpty()) {
            try {
                controlador = new RelatorioControlador();
                resumoModalidadeProjetos = controlador.preencherResumoModalidadeProjeto();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return resumoModalidadeProjetos;
    }

    public List<Projeto> getResumoFomentoProjetos() {
        if (resumoProjetosComFomento.isEmpty()) {
            try {
                controlador = new RelatorioControlador();
                resumoProjetosComFomento = controlador.preencherResumoFomentoProjetos();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return resumoProjetosComFomento;
    }

    public List<Projeto> getProjetosDeAluno() {
        if (idAlunoPesquisador != 0) {
            try {
                controlador = new RelatorioControlador();
                projetosDeAluno = controlador.listarProjetosDeAluno(idAlunoPesquisador);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (NoResultException e) {
                e.printStackTrace();
            }
        }
        return projetosDeAluno;
    }

    public List<Projeto> getNovosProjetosAdded() {

        try {
            controlador = new RelatorioControlador();
            novosProjetosAdded = controlador.listarNovosProjetosAdded();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return novosProjetosAdded;
    }

    public List<ResumoFomentoProjetos> getResumoFomentadores() {
        if (resumoFomentadores.isEmpty()) {
            try {
                controlador = new RelatorioControlador();
                resumoFomentadores = controlador.preencherResumoFomentadoresProjetos();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return resumoFomentadores;
    }

    public void setResumoFomentadores(List<ResumoFomentoProjetos> resumoFomentadores) {
        this.resumoFomentadores = resumoFomentadores;
    }

    public int getIdAlunoPesquisador() {
        return idAlunoPesquisador;
    }

    public void setIdAlunoPesquisador(int idAlunoPesquisador) {
        this.idAlunoPesquisador = idAlunoPesquisador;
    }

    public void addAlunoPesquisador(SelectEvent e) {
        this.idAlunoPesquisador = getIdAlunoPesquisador();
    }

    public void exibirDadosRelacao(SelectEvent e) {
        UsuarioControlador usuControlador = new UsuarioControlador();
        Usuario usuario = usuControlador.buscarUsuario(idAlunoPesquisador);
        nomeAlunoPesquisador = usuario.getNome();
        controlador = new RelatorioControlador();
        totalConvenios = controlador.calcularTotalConvenios(idAlunoPesquisador);
        totalFomento = controlador.calcularTotalFomento(idAlunoPesquisador);
    }

    public String getNomeAlunoPesquisador() {
        return nomeAlunoPesquisador;
    }

    public void setNomeAlunoPesquisador(String nomeAlunoPesquisador) {
        this.nomeAlunoPesquisador = nomeAlunoPesquisador;
    }

    public long getTotalFomento() {
        return totalFomento;
    }

    public void setTotalFomento(long totalFomento) {
        this.totalFomento = totalFomento;
    }

    public long getTotalConvenios() {
        return totalConvenios;
    }

    public void setTotalConvenios(int totalConvenios) {
        this.totalConvenios = totalConvenios;
    }

}
