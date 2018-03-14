/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.ModalidadeProjeto;
import br.projetos.propes.entities.Periodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Eneas Mesquita
 */
public class ModalidadeControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public ModalidadeControlador() {
    }

    public void adicionar(ModalidadeProjeto modalidade, int periodo) {
        try {
            em.getTransaction().begin();
            modalidade.setPeriodo(em.find(Periodo.class, periodo));
            em.persist(modalidade);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Modalidade " + modalidade.getDescricao() + " cadastrada", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da modalidade.", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(ModalidadeProjeto modalidade) {
        try {
            em.getTransaction().begin();
            em.merge(modalidade);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modalidade " + modalidade.getDescricao() + " atualizada.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização da modalidade.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(ModalidadeProjeto modalidade) {
        try {
            ModalidadeProjeto obj_gerenciado = em.merge(modalidade); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Modalidade " + modalidade.getDescricao() + " foi removida.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão da modalidade.", ""));
        } finally {
            em.close();
        }
    }

    public List<ModalidadeProjeto> listar() {
        List<ModalidadeProjeto> modalidades = new ArrayList<>();
        Collection<ModalidadeProjeto> modalidadeCollection = new ArrayList();
        Query query = em.createQuery("SELECT mp FROM ModalidadeProjeto mp ORDER BY mp.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        modalidadeCollection = query.getResultList();
        modalidades = new ArrayList(modalidadeCollection);
        return modalidades;
    }

    public List<Periodo> listarPeriodos() {
        List<Periodo> periodos = new ArrayList<>();
        Collection<Periodo> periodoCollection = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Periodo p ORDER BY p.meses ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        periodoCollection = query.getResultList();
        periodos = new ArrayList(periodoCollection);
        return periodos;
    }
}
