/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;
import br.projetos.propes.entities.Curso;
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
public class CursoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();
    
    public CursoControlador(){}

    public void adicionar(Curso curso) {
        try {
            em.getTransaction().begin();
            em.persist(curso);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso "+curso.getDescricao()+" cadastrado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da Curso.", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(Curso curso) {
        try {
            em.getTransaction().begin();
            em.merge(curso);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Curso "+curso.getDescricao()+" atualizada.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização da Curso.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(Curso curso) {
        try {
            Curso obj_gerenciado = em.merge(curso); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Curso "+curso.getDescricao()+" foi removida.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão da Curso.", ""));
        } finally {
            em.close();
        }
    }

    public List<Curso> listar() {
        List<Curso> cursos = new ArrayList<>();
        Collection<Curso> CursoCollection = new ArrayList();
        Query query = em.createQuery("SELECT c FROM Curso c ORDER BY c.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        CursoCollection = query.getResultList();
        cursos = new ArrayList(CursoCollection);
        return cursos;
    }
}
