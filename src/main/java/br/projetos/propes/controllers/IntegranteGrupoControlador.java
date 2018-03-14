/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.GrupoTrabalho;
import br.projetos.propes.entities.IntegrantesGrupo;
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
public class IntegranteGrupoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public IntegranteGrupoControlador() {
    }

    public void adicionar(List<Usuario> integrantesGrupo, int id_grupoTrabalho) {
        try {

            em.getTransaction().begin();
            for (Usuario integrantesGrupo1 : integrantesGrupo) {
                IntegrantesGrupo integranteGrupo = new IntegrantesGrupo();
                integranteGrupo.setUsuario(em.find(Usuario.class, integrantesGrupo1.getId()));
                integranteGrupo.setGrupoTrabalho(em.find(GrupoTrabalho.class, id_grupoTrabalho));
                em.persist(integranteGrupo);
            }
            em.getTransaction().commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Novo(s) integrante(s) inserido(s) com sucesso!", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir novo integrante do grupo!", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(IntegrantesGrupo integrantesGrupo) {
        try {
            IntegrantesGrupo obj_gerenciado = em.merge(integrantesGrupo); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O integrante foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do integrante do grupo.", ""));
        } finally {
            em.close();
        }
    }

    public List<IntegrantesGrupo> listar() {
        List<IntegrantesGrupo> integrantesGrupos = new ArrayList<>();
        Collection<IntegrantesGrupo> integrantesGrupoCollection = new ArrayList();
        Query query = em.createQuery("SELECT ig FROM IntegrantesGrupo ig ORDER BY ig.usuario.nome ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        integrantesGrupoCollection = query.getResultList();
        integrantesGrupos = new ArrayList(integrantesGrupoCollection);
        return integrantesGrupos;
    }
}
