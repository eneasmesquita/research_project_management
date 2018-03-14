/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.GrupoTrabalho;
import br.projetos.propes.entities.Usuario;
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
public class GrupoTrabalhoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public GrupoTrabalhoControlador() {
    }

    public void adicionar(String grupoDescricao, int coordenador, List<Usuario> integrantesGrupo) {

        //adicionando o grupo
        GrupoTrabalho grupoTrabalho = new GrupoTrabalho();
        grupoTrabalho.setDescricao(grupoDescricao.toUpperCase());
        grupoTrabalho.setCoordenador(em.find(Usuario.class, coordenador));
        try {
            em.getTransaction().begin();
            em.persist(grupoTrabalho);
            //buscando o gt recém-adicionado
            int id_grupoTrabalho = 0;
            id_grupoTrabalho = (Integer) em.createQuery("SELECT MAX(gt.id) FROM GrupoTrabalho gt").getSingleResult();
            em.getTransaction().commit();

            //adicionando os integrantes
            IntegranteGrupoControlador igc = new IntegranteGrupoControlador();
            igc.adicionar(integrantesGrupo, id_grupoTrabalho);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "GrupoTrabalho " + grupoTrabalho.getDescricao() + " cadastrado.", ""));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível finalizar o cadastro do grupo!", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(GrupoTrabalho grupoTrabalho) {
        try {
            em.getTransaction().begin();
            em.merge(grupoTrabalho);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "GrupoTrabalho " + grupoTrabalho.getDescricao() + " atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do grupoTrabalho.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(GrupoTrabalho grupoTrabalho) {
        try {
            GrupoTrabalho obj_gerenciado = em.merge(grupoTrabalho); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "GrupoTrabalho " + grupoTrabalho.getDescricao() + " foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do grupoTrabalho.", ""));
        } finally {
            em.close();
        }
    }

    public List<GrupoTrabalho> listar() {
        List<GrupoTrabalho> grupoTrabalhos = new ArrayList<>();
        Collection<GrupoTrabalho> grupoTrabalhoCollection = new ArrayList();
        Query query = em.createQuery("SELECT gt FROM GrupoTrabalho gt ORDER BY gt.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        grupoTrabalhoCollection = query.getResultList();
        grupoTrabalhos = new ArrayList(grupoTrabalhoCollection);
        return grupoTrabalhos;
    }

}
