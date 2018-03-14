/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.EventoColaborador;
import br.projetos.propes.entities.EventoAluno;
import br.projetos.propes.entities.Projeto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Eneas Mesquita
 */
public class EventoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public EventoControlador() {
    }

    public void adicionar(EventoColaborador eventoColaborador) {
        if (!eventoCadastrado(eventoColaborador.getDescricao())) {
            try {
                em.getTransaction().begin();
                em.persist(eventoColaborador);
                em.getTransaction().commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento " + eventoColaborador.getDescricao() + " cadastrado.", ""));
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do evento.", ""));
            } finally {
                em.close();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Evento já cadastrado.", ""));
        }
    }

    public void alterar(EventoColaborador eventoColaborador) {
        try {
            em.getTransaction().begin();
            em.merge(eventoColaborador);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento " + eventoColaborador.getDescricao() + " atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do evento.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(EventoColaborador evento) {
        try {
            EventoColaborador obj_gerenciado = em.merge(evento); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento " + evento.getDescricao() + " foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do evento.", ""));
        } finally {
            em.close();
        }
    }

    public List<EventoColaborador> listar() {
        List<EventoColaborador> eventos = new ArrayList<>();
        Collection<EventoColaborador> eventoCollection = new ArrayList();
        Query query = em.createQuery("SELECT ec FROM EventoColaborador ec ORDER BY ec.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        eventoCollection = query.getResultList();
        eventos = new ArrayList(eventoCollection);
        return eventos;
    }

    public List<EventoAluno> listarAluno() {
        List<EventoAluno> eventosAluno = new ArrayList<>();
        Collection<EventoAluno> eventoCollection = new ArrayList();
        Query query = em.createQuery("SELECT ea FROM EventoAluno ea ORDER BY ea.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        eventoCollection = query.getResultList();
        eventosAluno = new ArrayList(eventoCollection);
        return eventosAluno;
    }

    public List<EventoAluno> listarAluno(int projetoId) {
        List<EventoAluno> eventosAluno = new ArrayList<>();
        Collection<EventoAluno> eventoCollection = new ArrayList();
        Projeto projetoAux = em.find(Projeto.class, projetoId);
        Query query = em.createQuery("SELECT ea FROM EventoAluno ea WHERE ea.periodo.id = :periodo ORDER BY ea.periodo ASC");
        query.setParameter("periodo", projetoAux.getModalidade().getPeriodo().getId());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        eventoCollection = query.getResultList();
        eventosAluno = new ArrayList(eventoCollection);
        return eventosAluno;
    }

    public List<EventoColaborador> listarEventosMovimentacaoProjeto(int projetoId) {
        List<EventoColaborador> eventos = new ArrayList<>();
        List<EventoColaborador> eventosFaltantes = new ArrayList<>();
        List<EventoColaborador> eventosProjetoEsp = new ArrayList<>();
        Collection<EventoColaborador> eventoCollection = new ArrayList();
        Query query = em.createQuery("SELECT ec FROM EventoColaborador ec, MovimentacaoProjeto mp, Projeto p WHERE ec.id = mp.eventoColaborador.id AND p.id = mp.projeto.id AND p.id = :projetoId ORDER BY ec.descricao ASC");
        query.setParameter("projetoId", projetoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        eventoCollection = query.getResultList();
        eventosProjetoEsp = new ArrayList(eventoCollection);
        eventos = listar();
        for (EventoColaborador ec : eventos) {
            if (!eventosProjetoEsp.contains(ec)) {
//                if (ec.getDescricao().contains("PROJETO EM EXECUÇÃO") || ec.getDescricao().equals("PROJETO EM EXECUÇÃO")) {
                eventosFaltantes.add(ec);
//                }
            }
        }
        return eventosFaltantes;
    }

    public List<EventoAluno> listarEventosMovimentacaoMeuProjeto(int projetoId) {
        List<EventoAluno> eventosAluno = new ArrayList<>();
        List<EventoAluno> eventosFaltantes = new ArrayList<>();
        List<EventoAluno> eventosProjetoEsp = new ArrayList<>();
        Collection<EventoColaborador> eventoCollection = new ArrayList();
        boolean nenhumEventoAluno = false;
        try {
            Query query = em.createQuery("SELECT ea FROM EventoAluno ea, MovimentacaoProjeto mp, Projeto p WHERE ea.id = mp.eventoAluno.id AND p.id = mp.projeto.id AND p.id = :projetoId ORDER BY ea.descricao ASC");
            query.setParameter("projetoId", projetoId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            eventoCollection = query.getResultList();
            eventosProjetoEsp = new ArrayList(eventoCollection);
        } catch (NullPointerException npe) {
            nenhumEventoAluno = true;
        } catch (NoResultException nre) {
            nenhumEventoAluno = true;
        }
        if (projetoId != 0) {
            eventosAluno = listarAluno(projetoId);
        } else {
            eventosAluno = listarAluno();
        }

        if (nenhumEventoAluno) {
            return eventosAluno;
        } else {
            for (EventoAluno ea : eventosAluno) {
                if (!eventosProjetoEsp.contains(ea)) {
                    eventosFaltantes.add(ea);
                }
            }
            return eventosFaltantes;
        }
    }

    public boolean eventoCadastrado(String descricao) {
        try {
            Query query = em.createQuery("SELECT ec FROM EventoColaborador ec WHERE ec.descricao LIKE :descricao");
            query.setParameter("descricao", descricao);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
