package br.projetos.propes.login;

import br.projetos.propes.beans.EventoPropesMB;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import br.projetos.propes.entities.Usuario;
import javax.el.ELContext;
import javax.faces.FacesException;

@ManagedBean(name = "LoginController")
@SessionScoped
public class AutenticadorBean implements Serializable {

    Usuario usuario;
    FacesContext fc;
    HttpSession session;
    private String login = "";
    private String senha = "";
    EventoPropesMB eMB = (EventoPropesMB) getManagedBean("EventoPropesMB");

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public String logon() {

        criaSession();
        try {
            usuario = new Usuario();
            senha = md5(getSenha());
            Query query = em.createQuery("select u from Usuario u where u.cpf = '" + login + "' and u.senha = '" + senha + "'");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            this.usuario = (Usuario) query.getSingleResult();
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao autenticar usuario.", "Entre em contato com o Administrador do Sistema!"));
        }

        if (usuario.getNome() != null) {
            session.setAttribute("autenticado", true);
            session.setAttribute("usuario_logado", usuario);
            session.setAttribute("perfil", usuario.getTipo().getDescricao());
            session.setAttribute("login", usuario.getCpf());
            session.setAttribute("nome_usuario", usuario.getNome());
            session.setAttribute("id_usuario", usuario.getId());
            session.setAttribute("tipo", usuario.getTipo().getId());
            if (usuario.getTipo().getId() == 2) {
                session.setAttribute("curso_id", usuario.getCurso().getId());
                session.setAttribute("curso", usuario.getCurso().getDescricao());
            } else {
                session.setAttribute("curso_id", 0);
            }
            return "/PROPES/index_propes.xhtml?faces-redirect=true";
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário e/ou Senha Inválidos!!!", ""));
            return null;
        }
    }

    public String logout() {

        FacesContext fcon = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fcon.getExternalContext().getSession(false);
        session.invalidate();

        return "/login.xhtml?faces-redirect=true";
    }

    public static String md5(String texto) {
        String sen = "";
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
        sen = hash.toString(16);

        return sen;
    }

    public HttpSession criaSession() {
        fc = FacesContext.getCurrentInstance();
        session = (HttpSession) fc.getExternalContext().getSession(false);
        return session;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EntityManager getEm() {
        return em;
    }

    public HttpSession getSession() {
        return session;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
