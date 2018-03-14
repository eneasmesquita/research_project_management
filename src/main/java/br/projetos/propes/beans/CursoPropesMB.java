/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.CursoControlador;
import br.projetos.propes.entities.Curso;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "CursoPropesMB")
@ViewScoped
public class CursoPropesMB {

    private CursoControlador controlador;
    private Curso curso;
    private List<Curso> cursos = new ArrayList<Curso>();

    public CursoPropesMB() {
    }

    public List<Curso> getCursos() {
        
        if (cursos.isEmpty()) {
            try {
                controlador = new CursoControlador();
                cursos = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return cursos;
    }

    public void adicionar() throws Exception {
        this.curso.setDescricao(curso.getDescricao().toUpperCase());
        controlador = new CursoControlador();
        controlador.adicionar(this.curso);
        this.curso = new Curso();
        this.cursos.clear();
    }

    public void excluir() {
        controlador = new CursoControlador();
        controlador.excluir(curso);
        this.cursos.clear();
    }
    
    public void alterar() {
        controlador = new CursoControlador();
        controlador.alterar(curso);
        this.cursos.clear();
    }

    public Curso getCurso() {
        if (curso == null) {
            curso = new Curso();
        }
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
