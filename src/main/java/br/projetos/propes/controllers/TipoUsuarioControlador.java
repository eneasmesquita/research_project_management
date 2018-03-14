/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;
import br.projetos.propes.entities.TipoUsuario;
import java.io.Serializable;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Eneas Mesquita
 */
public class TipoUsuarioControlador implements Serializable {

     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public TipoUsuarioControlador() {
    }

    public void adicionar(TipoUsuario tipoUsuario) {
        try {
            em.getTransaction().begin();
            em.persist(tipoUsuario);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "TipoUsuario "+tipoUsuario.getDescricao()+" cadastrado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do tipoUsuario.", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(TipoUsuario tipoUsuario) {
        try {
            em.getTransaction().begin();
            em.merge(tipoUsuario);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "TipoUsuario "+tipoUsuario.getDescricao()+" atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do tipoUsuario.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(TipoUsuario tipoUsuario) {
        try {
            TipoUsuario obj_gerenciado = em.merge(tipoUsuario); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "TipoUsuario "+tipoUsuario.getDescricao()+" foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do tipoUsuario.", ""));
        } finally {
            em.close();
        }
    }

    public List<TipoUsuario> listar() {
        List<TipoUsuario> tipoUsuarios = new ArrayList<>();
        Collection<TipoUsuario> tipoUsuarioCollection = new ArrayList();
        Query query = em.createQuery("SELECT tu FROM TipoUsuario tu ORDER BY tu.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        tipoUsuarioCollection = query.getResultList();
        tipoUsuarios = new ArrayList(tipoUsuarioCollection);
        return tipoUsuarios;
    }
}
