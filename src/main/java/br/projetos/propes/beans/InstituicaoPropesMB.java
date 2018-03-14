/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.InstituicaoControlador;
import br.projetos.propes.entities.InstituicaoConvenio;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "InstituicaoPropesMB")
@ViewScoped
public class InstituicaoPropesMB {

    private InstituicaoControlador controlador;
    private InstituicaoConvenio instituicao;
    private List<InstituicaoConvenio> instituicoes = new ArrayList<InstituicaoConvenio>();

    public InstituicaoPropesMB() {

    }

    public List<InstituicaoConvenio> getInstituicoes() {
        if (instituicoes.isEmpty()) {
            try {
                controlador = new InstituicaoControlador();
                instituicoes = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return instituicoes;
    }

    public void adicionar() throws Exception {
        this.instituicao.setDescricao(instituicao.getDescricao().toUpperCase());
        controlador = new InstituicaoControlador();
        controlador.adicionar(this.instituicao);
        this.instituicao = new InstituicaoConvenio();
        this.instituicoes.clear();
    }

    public void excluir() {
        controlador = new InstituicaoControlador();
        controlador.excluir(instituicao);
        this.instituicoes.clear();
    }

    public void alterar() {
        controlador = new InstituicaoControlador();
        controlador.alterar(instituicao);
        this.instituicoes.clear();
    }

    public InstituicaoConvenio getInstituicao() {
        if (instituicao == null) {
            instituicao = new InstituicaoConvenio();
        }
        return instituicao;
    }

    public void setInstituicao(InstituicaoConvenio instituicao) {
        this.instituicao = instituicao;
    }

}
