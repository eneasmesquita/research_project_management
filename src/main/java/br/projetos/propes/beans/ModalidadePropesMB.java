/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.ModalidadeControlador;
import br.projetos.propes.entities.ModalidadeProjeto;
import br.projetos.propes.entities.Periodo;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "ModalidadePropesMB")
@ViewScoped
public class ModalidadePropesMB {

    private ModalidadeControlador controlador;
    private ModalidadeProjeto modalidade;
    private int periodo = 0;
    private List<ModalidadeProjeto> modalidades = new ArrayList<ModalidadeProjeto>();
    private List<Periodo> periodos = new ArrayList<Periodo>();

    public ModalidadePropesMB() {

    }

    public List<ModalidadeProjeto> getModalidades() {
        if (modalidades.isEmpty()) {
            try {
                controlador = new ModalidadeControlador();
                modalidades = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return modalidades;
    }

    public List<Periodo> getPeriodos() {
        if (periodos.isEmpty()) {
            try {
                controlador = new ModalidadeControlador();
                periodos = controlador.listarPeriodos();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return periodos;
    }

    public void adicionar() throws Exception {
        this.modalidade.setDescricao(modalidade.getDescricao().toUpperCase());
        controlador = new ModalidadeControlador();
        controlador.adicionar(this.modalidade, periodo);
        this.modalidade = new ModalidadeProjeto();
        this.modalidades.clear();
    }

    public void excluir() {
        controlador = new ModalidadeControlador();
        controlador.excluir(modalidade);
        this.modalidades.clear();
    }

    public void alterar() {
        controlador = new ModalidadeControlador();
        controlador.alterar(modalidade);
        this.modalidades.clear();
    }

    public ModalidadeProjeto getModalidade() {
        if (modalidade == null) {
            modalidade = new ModalidadeProjeto();
        }
        return modalidade;
    }

    public void setModalidade(ModalidadeProjeto modalidade) {
        this.modalidade = modalidade;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

}
