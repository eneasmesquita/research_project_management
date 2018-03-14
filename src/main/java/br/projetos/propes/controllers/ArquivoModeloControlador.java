/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.ArquivoModelo;
import br.projetos.propes.entities.EventoAluno;
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
public class ArquivoModeloControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public ArquivoModeloControlador() {
    }

    public void adicionar(ArquivoModelo arquivoModelo) {
        if (!arquivoModeloCadastrado(arquivoModelo.getDescricao())) {
            try {
                em.getTransaction().begin();
                em.persist(arquivoModelo);
                em.getTransaction().commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Modelo " + arquivoModelo.getDescricao() + " cadastrado.", ""));
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do Arquivo Modelo.", ""));
            } finally {
                em.close();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Arquivo Modelo já cadastrado.", ""));
        }
    }

    public void alterar(ArquivoModelo arquivoModelo) {
        try {
            em.getTransaction().begin();
            em.merge(arquivoModelo);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ArquivoModelo " + arquivoModelo.getDescricao() + " atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do arquivo modelo.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(ArquivoModelo arquivoModelo) {
        try {
            ArquivoModelo obj_gerenciado = em.merge(arquivoModelo); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Modelo " + arquivoModelo.getDescricao() + " foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do arquivo modelo.", ""));
        } finally {
            em.close();
        }
    }

    public List<ArquivoModelo> listar() {
        List<ArquivoModelo> arquivosModelo = new ArrayList<>();
        Collection<ArquivoModelo> eventoCollection = new ArrayList();
        Query query = em.createQuery("SELECT am FROM ArquivoModelo am ORDER BY am.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        eventoCollection = query.getResultList();
        arquivosModelo = new ArrayList(eventoCollection);
        return arquivosModelo;
    }

    public boolean arquivoModeloCadastrado(String descricao) {
        try {
            Query query = em.createQuery("SELECT am FROM ArquivoModelo am WHERE am.descricao LIKE :descricao");
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
