/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.FomentadorControlador;
import br.projetos.propes.entities.Fomentador;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "FomentadorPropesMB")
@ViewScoped
public class FomentadorPropesMB {

    private FomentadorControlador controlador;
    private Fomentador fomentador;
    private List<Fomentador> fomentadores = new ArrayList<Fomentador>();

    public FomentadorPropesMB() {
    }

    public List<Fomentador> getFomentadores() {
        if (fomentadores.isEmpty()) {
            try {
                controlador = new FomentadorControlador();
                fomentadores = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return fomentadores;
    }

    public void adicionar() throws Exception {
        this.fomentador.setDescricao(fomentador.getDescricao().toUpperCase());
        controlador = new FomentadorControlador();
        controlador.adicionar(this.fomentador);
        this.fomentador = new Fomentador();
        this.fomentadores.clear();
    }

    public void excluir() {
        controlador = new FomentadorControlador();
        controlador.excluir(fomentador);
        this.fomentadores.clear();
    }
    
    public void alterar() {
        controlador = new FomentadorControlador();
        controlador.alterar(fomentador);
        this.fomentadores.clear();
    }

    public Fomentador getFomentador() {
        if (fomentador == null) {
            fomentador = new Fomentador();
        }
        return fomentador;
    }

    public void setFomentador(Fomentador fomentador) {
        this.fomentador = fomentador;
    }
}
