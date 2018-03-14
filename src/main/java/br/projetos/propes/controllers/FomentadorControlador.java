/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;
import br.projetos.propes.entities.Fomentador;
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
public class FomentadorControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();
    
    public FomentadorControlador(){}

    public void adicionar(Fomentador fomentador) {
        try {
            em.getTransaction().begin();
            em.persist(fomentador);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Fomentador "+fomentador.getDescricao()+" cadastrado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da Fomentador.", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(Fomentador fomentador) {
        try {
            em.getTransaction().begin();
            em.merge(fomentador);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Fomentador "+fomentador.getDescricao()+" atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do Fomentador.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(Fomentador fomentador) {
        try {
            Fomentador obj_gerenciado = em.merge(fomentador); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Fomentador "+fomentador.getDescricao()+" foi removida.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão da Fomentador.", ""));
        } finally {
            em.close();
        }
    }

    public List<Fomentador> listar() {
        List<Fomentador> fomentadors = new ArrayList<>();
        Collection<Fomentador> fomentadorCollection = new ArrayList();
        Query query = em.createQuery("SELECT f FROM Fomentador f ORDER BY f.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        fomentadorCollection = query.getResultList();
        fomentadors = new ArrayList(fomentadorCollection);
        return fomentadors;
    }
}
