/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.GrupoTrabalhoControlador;
import br.projetos.propes.controllers.UsuarioControlador;
import br.projetos.propes.entities.GrupoTrabalho;
import br.projetos.propes.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "GrupoTrabalhoPropesMB")
@ViewScoped
public class GrupoTrabalhoMB {

    private GrupoTrabalhoControlador controlador;
    private List<Usuario> integrantesGrupo = new ArrayList<Usuario>();
    private List<GrupoTrabalho> gruposTrabalho = new ArrayList<GrupoTrabalho>();
    private int coordenadorGrupo = 0;
    private int integrante;
    private String grupoDescricao = "";

    //integrantes grupo
    private UsuarioControlador controladorUsuario;
    private GrupoTrabalho grupoTrabalho = new GrupoTrabalho();

    public GrupoTrabalhoMB() {
    }

    public void adicionar() throws Exception {
        if (!validarDescricao() || !validarCoordenador() || !validarGrupoTrabalho() || !validarGrupoTrabalho()) {
        } else {
            controlador = new GrupoTrabalhoControlador();
            controlador.adicionar(grupoDescricao, coordenadorGrupo, integrantesGrupo);
            this.grupoDescricao = "";
            this.coordenadorGrupo = 0;
            this.integrante = 0;
            this.gruposTrabalho.clear();
            this.integrantesGrupo.clear();
        }
    }

    public void excluir() {
        controlador = new GrupoTrabalhoControlador();
        controlador.excluir(grupoTrabalho);
        this.gruposTrabalho.clear();
    }

    public void alterar() {
        controlador = new GrupoTrabalhoControlador();
        controlador.alterar(grupoTrabalho);
        this.gruposTrabalho.clear();
    }

    public List<Usuario> getIntegrantesGrupo() {
        return integrantesGrupo;
    }

    public void setIntegrantesGrupo(List<Usuario> integrantesGrupo) {
        this.integrantesGrupo = integrantesGrupo;
    }

    public List<GrupoTrabalho> getGruposTrabalho() {
        if (gruposTrabalho.isEmpty()) {
            try {
                controlador = new GrupoTrabalhoControlador();
                gruposTrabalho = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return gruposTrabalho;
    }

    public int getCoordenadorGrupo() {
        return coordenadorGrupo;
    }

    public void setCoordenadorGrupo(int coordenadorGrupo) {
        this.coordenadorGrupo = coordenadorGrupo;
    }

    public int getIntegrante() {
        return integrante;
    }

    public void setIntegrante(int integrante) {
        this.integrante = integrante;
    }

    public GrupoTrabalho getGrupoTrabalho() {
        if (grupoTrabalho == null) {
            grupoTrabalho = new GrupoTrabalho();
        }
        return grupoTrabalho;
    }

    public void setGrupoTrabalho(GrupoTrabalho grupoTrabalho) {
        this.grupoTrabalho = grupoTrabalho;
    }

    public String getGrupoDescricao() {
        return grupoDescricao;
    }

    public void setGrupoDescricao(String grupoDescricao) {
        this.grupoDescricao = grupoDescricao;
    }

    public void addIntegrante(SelectEvent e) {
        controladorUsuario = new UsuarioControlador();
        Usuario novoIntegrante = new Usuario();
        try {
            if (integrante == coordenadorGrupo) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O coordenador não pode ser um integrante do grupo!", ""));
            } else if (integrantesGrupo.contains(controladorUsuario.buscarUsuario(integrante))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O integrante já foi inserido ao grupo!", ""));
            } else {
                novoIntegrante = controladorUsuario.buscarUsuario(integrante);
                integrantesGrupo.add(novoIntegrante);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void preparaFormGrupoTrabalho(ActionListener e) {
        this.grupoDescricao = "";
        this.coordenadorGrupo = 0;
        this.integrante = 0;
        this.gruposTrabalho.clear();
        this.integrantesGrupo.clear();
    }

    public void removeIntegrante(Usuario integrante) {
        integrantesGrupo.remove(integrante);
    }

    public void inserirDescricaoGrupo() {
        this.grupoDescricao = getGrupoDescricao();
    }

    public void addCoordenadorGrupo(SelectEvent e) {
        this.coordenadorGrupo = getCoordenadorGrupo();
    }

    public boolean validarDescricao() {
        if (grupoDescricao.equals("") || grupoDescricao == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insira o nome do Grupo!", ""));
            return false;
        } else {
            return true;
        }
    }
    
    public boolean validarCoordenador() {
        if (coordenadorGrupo == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Infome o coordenador do grupo!", ""));
            return false;
        } else {
            return true;
        }
    }

    public boolean validarGrupoTrabalho() {
        if (integrantesGrupo.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O grupo não pode estar vazio!", ""));
            return false;
        } else if (integrantesGrupo.size() < 2) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O grupo deve ser composto no mínimo por dois integrantes!", ""));
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCoordenadorGrupo() {
        if (coordenadorGrupo == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O grupo deve ter um coordenador!", ""));
            return false;
        } else {
            return true;
        }
    }
}
