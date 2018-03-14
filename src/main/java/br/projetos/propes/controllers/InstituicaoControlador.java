/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;
import br.projetos.propes.entities.InstituicaoConvenio;
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
public class InstituicaoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();
    
    public InstituicaoControlador(){}

    public void adicionar(InstituicaoConvenio Instituicao) {
        try {
            em.getTransaction().begin();
            em.persist(Instituicao);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Instituição "+Instituicao.getDescricao()+" cadastrado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da Instituição.", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(InstituicaoConvenio Instituicao) {
        try {
            em.getTransaction().begin();
            em.merge(Instituicao);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Instituição "+Instituicao.getDescricao()+" atualizada.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização da Instituição.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(InstituicaoConvenio Instituicao) {
        try {
            InstituicaoConvenio obj_gerenciado = em.merge(Instituicao); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Instituição "+Instituicao.getDescricao()+" foi removida.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão da Instituição.", ""));
        } finally {
            em.close();
        }
    }

    public List<InstituicaoConvenio> listar() {
        List<InstituicaoConvenio> Instituicaos = new ArrayList<>();
        Collection<InstituicaoConvenio> InstituicaoCollection = new ArrayList();
        Query query = em.createQuery("SELECT ic FROM InstituicaoConvenio ic ORDER BY ic.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        InstituicaoCollection = query.getResultList();
        Instituicaos = new ArrayList(InstituicaoCollection);
        return Instituicaos;
    }
}
