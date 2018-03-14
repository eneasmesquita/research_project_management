/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.Curso;
import br.projetos.propes.entities.AcompanhamentoProjeto18Meses;
import br.projetos.propes.entities.AcompanhamentoProjeto2Anos;
import br.projetos.propes.entities.AcompanhamentoProjeto30Meses;
import br.projetos.propes.entities.AcompanhamentoProjeto3Anos;
import br.projetos.propes.entities.AcompanhamentoProjeto42Meses;
import br.projetos.propes.entities.AcompanhamentoProjeto4Anos;
import br.projetos.propes.entities.AcompanhamentoProjeto6Meses1Ano;
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
public class AcompanhamentoProjetosControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public AcompanhamentoProjetosControlador() {
    }

    public void adicionarProjeto6Meses1Ano(AcompanhamentoProjeto6Meses1Ano ap6m1ano) {
        try {
            em.getTransaction().begin();
            em.persist(ap6m1ano);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap6m1ano.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da Curso.", ""));
        } finally {
            em.close();
        }
    }

    public void adicionarProjeto18Meses(AcompanhamentoProjeto18Meses ap18meses) {
        try {
            em.getTransaction().begin();
            em.persist(ap18meses);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap18meses.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro dos dados de acompanhamento do projeto " + ap18meses.getProjeto().getTitulo(), ""));
        } finally {
            em.close();
        }
    }

    public void adicionarProjeto2Anos(AcompanhamentoProjeto2Anos ap2anos) {
        try {
            em.getTransaction().begin();
            em.persist(ap2anos);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap2anos.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro dos dados de acompanhamento do projeto " + ap2anos.getProjeto().getTitulo(), ""));
        } finally {
            em.close();
        }
    }

    public void adicionarProjeto30Meses(AcompanhamentoProjeto30Meses ap30meses) {
        try {
            em.getTransaction().begin();
            em.persist(ap30meses);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap30meses.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro dos dados de acompanhamento do projeto " + ap30meses.getProjeto().getTitulo(), ""));
        } finally {
            em.close();
        }
    }

    public void adicionarProjeto3Anos(AcompanhamentoProjeto3Anos ap3anos) {
        try {
            em.getTransaction().begin();
            em.persist(ap3anos);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap3anos.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro dos dados de acompanhamento do projeto " + ap3anos.getProjeto().getTitulo(), ""));
        } finally {
            em.close();
        }
    }

    public void adicionarProjeto42Meses(AcompanhamentoProjeto42Meses ap42meses) {
        try {
            em.getTransaction().begin();
            em.persist(ap42meses);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap42meses.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro dos dados de acompanhamento do projeto " + ap42meses.getProjeto().getTitulo(), ""));
        } finally {
            em.close();
        }
    }

    public void adicionarProjeto4Anos(AcompanhamentoProjeto4Anos ap4Anos) {
        try {
            em.getTransaction().begin();
            em.persist(ap4Anos);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dados de acompanhamento do projeto " + ap4Anos.getProjeto().getTitulo() + " cadastrados.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro dos dados de acompanhamento do projeto " + ap4Anos.getProjeto().getTitulo(), ""));
        } finally {
            em.close();
        }
    }

    public List<AcompanhamentoProjeto6Meses1Ano> listarProjeto6Meses1Ano() {
        List<AcompanhamentoProjeto6Meses1Ano> ap6meses1anoLista = new ArrayList<AcompanhamentoProjeto6Meses1Ano>();
        Collection<AcompanhamentoProjeto6Meses1Ano> ap6meses1anoCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto6Meses1Ano.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap6meses1anoCollection = query.getResultList();
        ap6meses1anoLista = new ArrayList(ap6meses1anoCollection);
        return ap6meses1anoLista;
    }

    public List<AcompanhamentoProjeto18Meses> listarProjeto18Meses() {
        List<AcompanhamentoProjeto18Meses> ap18mesesLista = new ArrayList<AcompanhamentoProjeto18Meses>();
        Collection<AcompanhamentoProjeto18Meses> ap18mesesCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto18Meses.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap18mesesCollection = query.getResultList();
        ap18mesesLista = new ArrayList(ap18mesesCollection);
        return ap18mesesLista;
    }

    public List<AcompanhamentoProjeto2Anos> listarProjeto2Anos() {
        List<AcompanhamentoProjeto2Anos> ap2anosLista = new ArrayList<AcompanhamentoProjeto2Anos>();
        Collection<AcompanhamentoProjeto2Anos> ap2anosCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto2Anos.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap2anosCollection = query.getResultList();
        ap2anosLista = new ArrayList(ap2anosCollection);
        return ap2anosLista;
    }

    public List<AcompanhamentoProjeto30Meses> listarProjeto30Meses() {
        List<AcompanhamentoProjeto30Meses> ap30mesesLista = new ArrayList<AcompanhamentoProjeto30Meses>();
        Collection<AcompanhamentoProjeto30Meses> ap30mesesCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto30Meses.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap30mesesCollection = query.getResultList();
        ap30mesesLista = new ArrayList(ap30mesesCollection);
        return ap30mesesLista;
    }

    public List<AcompanhamentoProjeto3Anos> listarProjeto3Anos() {
        List<AcompanhamentoProjeto3Anos> ap3anosLista = new ArrayList<AcompanhamentoProjeto3Anos>();
        Collection<AcompanhamentoProjeto3Anos> ap3anosCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto3Anos.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap3anosCollection = query.getResultList();
        ap3anosLista = new ArrayList(ap3anosCollection);
        return ap3anosLista;
    }

    public List<AcompanhamentoProjeto42Meses> listarProjeto42Meses() {
        List<AcompanhamentoProjeto42Meses> ap42mesesLista = new ArrayList<AcompanhamentoProjeto42Meses>();
        Collection<AcompanhamentoProjeto42Meses> ap42mesesCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto42Meses.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap42mesesCollection = query.getResultList();
        ap42mesesLista = new ArrayList(ap42mesesCollection);
        return ap42mesesLista;
    }

    public List<AcompanhamentoProjeto4Anos> listarProjeto4Anos() {
        List<AcompanhamentoProjeto4Anos> ap4anosLista = new ArrayList<AcompanhamentoProjeto4Anos>();
        Collection<AcompanhamentoProjeto4Anos> ap4anosCollection = new ArrayList();
        Query query = em.createNamedQuery("AcompanhamentoProjeto4Anos.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        ap4anosCollection = query.getResultList();
        ap4anosLista = new ArrayList(ap4anosCollection);
        return ap4anosLista;
    }
}
