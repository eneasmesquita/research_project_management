/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.TipoUsuarioControlador;
import br.projetos.propes.entities.TipoUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean
@RequestScoped
public class TipoUsuarioPropesMB {

    private TipoUsuarioControlador controlador;
    private TipoUsuario tipoUsuario;
    private List<TipoUsuario> tipoUsuarios = new ArrayList<TipoUsuario>();

    public TipoUsuarioPropesMB() {
    }

    public List<TipoUsuario> getTipoUsuarios() {
        if (tipoUsuarios.isEmpty()) {
            try {
                controlador = new TipoUsuarioControlador();
                tipoUsuarios = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tipoUsuarios;
    }

    public void adicionar() throws Exception {
        this.tipoUsuario.setDescricao(tipoUsuario.getDescricao().toUpperCase());
        controlador = new TipoUsuarioControlador();
        controlador.adicionar(this.tipoUsuario);
        this.tipoUsuario = new TipoUsuario();
        this.tipoUsuarios.clear();
    }

    public void excluir() {
        controlador = new TipoUsuarioControlador();
        controlador.excluir(tipoUsuario);
        this.tipoUsuarios.clear();
    }

    public void alterar() {
        controlador = new TipoUsuarioControlador();
        controlador.alterar(tipoUsuario);
        this.tipoUsuarios.clear();
    }

    public TipoUsuario getTipoUsuario() {
        if (tipoUsuario == null) {
            tipoUsuario = new TipoUsuario();
        }
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
