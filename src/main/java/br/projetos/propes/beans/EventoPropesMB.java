/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.EventoControlador;
import br.projetos.propes.entities.EventoColaborador;
import br.projetos.propes.entities.EventoAluno;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "EventoPropesMB")
@RequestScoped
public class EventoPropesMB {

    private EventoControlador controlador;
    private EventoColaborador eventoColaborador;
    private List<EventoColaborador> eventos = new ArrayList<EventoColaborador>();
    private List<EventoColaborador> eventosMovimentacaoProjeto = new ArrayList<EventoColaborador>();
    private List<EventoAluno> eventosMovimentacaoMeusProjeto = new ArrayList<EventoAluno>();
    private int projetoId = 0;

    public EventoPropesMB() {
    }

    public List<EventoColaborador> getEventos() {
        if (eventos.isEmpty()) {
            try {
                controlador = new EventoControlador();
                eventos = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return eventos;
    }

    public List<EventoColaborador> getEventosMovimentacaoProjeto() {
        controlador = new EventoControlador();
        eventosMovimentacaoProjeto = controlador.listarEventosMovimentacaoProjeto(projetoId);
        return eventosMovimentacaoProjeto;
    }

    public List<EventoAluno> getEventosMovimentacaoMeusProjeto() {
        controlador = new EventoControlador();
        eventosMovimentacaoMeusProjeto = controlador.listarEventosMovimentacaoMeuProjeto(projetoId);
        return eventosMovimentacaoMeusProjeto;
    }

    public void adicionar() throws Exception {
        if (!validarDescricao()) {
            this.eventoColaborador.setDescricao(eventoColaborador.getDescricao().toUpperCase());
            controlador = new EventoControlador();
            controlador.adicionar(this.eventoColaborador);
            this.eventoColaborador = new EventoColaborador();
            this.eventos.clear();
        }
    }

    public void excluir() {
        controlador = new EventoControlador();
        controlador.excluir(eventoColaborador);
        this.eventos.clear();
    }

    public void alterar() {
        controlador = new EventoControlador();
        controlador.alterar(eventoColaborador);
        this.eventos.clear();
    }

    public boolean validarDescricao() {
        if (!eventoColaborador.getDescricao().isEmpty() || eventoColaborador.getDescricao() != null) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insira uma descrição para o evento!", ""));
            return false;
        }
    }

    public int getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(int projetoId) {
        this.projetoId = projetoId;
    }

    public EventoColaborador getEvento() {
        if (eventoColaborador == null) {
            eventoColaborador = new EventoColaborador();
        }
        return eventoColaborador;
    }

    public void setEvento(EventoColaborador evento) {
        this.eventoColaborador = evento;
    }

}
