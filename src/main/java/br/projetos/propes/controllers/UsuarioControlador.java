/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.Curso;
import br.projetos.propes.entities.ProReitoria;
import br.projetos.propes.entities.TipoUsuario;
import br.projetos.propes.entities.Usuario;
import br.projetos.propes.views.ViewControladorMB;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.el.ELContext;
import javax.faces.FacesException;
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
public class UsuarioControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public UsuarioControlador() {
    }

    public void adicionar(Usuario usuario, Integer curso, Integer tipoUsuario) {

        if (usuarioExistente(usuario.getCpf())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cpf já cadastrado no sistema!", ""));
        } else {
            int proReitoria = 0;
            try {
                Query query = em.createQuery("SELECT p.id FROM ProReitoria p WHERE p.sigla = 'PROPES'");
                proReitoria = (Integer) query.getSingleResult();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {

                em.getTransaction().begin();
                if (curso != 0) {
                    usuario.setCurso(em.find(Curso.class, curso));
                }
                if (proReitoria != 0) {
                    usuario.setProReitoria(em.find(ProReitoria.class, proReitoria));
                }
                usuario.setTipo(em.find(TipoUsuario.class, tipoUsuario));
                em.persist(usuario);
                em.getTransaction().commit();
                //reiniciando controle de exibicao do formulario de cadastro de usuario
                ViewControladorMB vMB = (ViewControladorMB) getManagedBean("ViewControladorMB");
                vMB.setCurso(false);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario " + usuario.getNome() + " cadastrado.", ""));
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do usuario.", ""));
            } finally {
                em.close();
            }
        }
    }

    public void alterar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario " + usuario.getNome() + " atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização do usuario.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(Usuario usuario) {
        try {
            Usuario obj_gerenciado = em.merge(usuario); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario " + usuario.getNome() + " foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão do usuario.", ""));
        } finally {
            em.close();
        }
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        Collection<Usuario> usuarioCollection = new ArrayList();
        Query query = em.createQuery("SELECT u FROM Usuario u ORDER BY u.nome ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        usuarioCollection = query.getResultList();
        usuarios = new ArrayList(usuarioCollection);
        return usuarios;
    }
    
    public List<Usuario> listarProfessores() {
        List<Usuario> usuarios = new ArrayList<>();
        Collection<Usuario> usuarioCollection = new ArrayList();
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.tipo.descricao LIKE '%PROFESSOR%' ORDER BY u.nome ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        usuarioCollection = query.getResultList();
        usuarios = new ArrayList(usuarioCollection);
        return usuarios;
    }
    
    public List<Usuario> listarAlunosPesquisadores() {
        List<Usuario> usuarios = new ArrayList<>();
        Collection<Usuario> usuarioCollection = new ArrayList();
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.tipo.descricao LIKE '%ALUNO%' OR u.tipo.descricao LIKE '%PESQUISADOR%' ORDER BY u.nome ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        usuarioCollection = query.getResultList();
        usuarios = new ArrayList(usuarioCollection);
        return usuarios;
    }

    public boolean usuarioExistente(String cpf) {
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.cpf = '" + cpf + "'");
            if (query.getSingleResult() != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Usuario buscarUsuario(int id) {
        Usuario usuario = new Usuario();
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = :usuarioId");
            query.setParameter("usuarioId", id);
            usuario = (Usuario) query.getSingleResult();
            return usuario;
        } catch (NullPointerException e) {
            System.out.println("NULLPOINTEREXCEPTION, usuario não identificado ou não encontrado.");
            e.printStackTrace();
            return null;
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object bean;
        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }
        if (bean == null) {
            throw new FacesException("Managed bean com o nome '" + beanName + "' não foi encontrado. "
                    + "Verifique o seu arquivo faces-config.xml ou anotação @ManagedBean.");
        }
        return bean;
    }

}
