/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.beans;

import br.projetos.propes.controllers.UsuarioControlador;
import br.projetos.propes.entities.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Eneas Mesquita
 */
@ManagedBean(name = "UsuarioPropesMB")
@SessionScoped
public class UsuarioPropesMB {

    private UsuarioControlador controlador;
    private Usuario usuario;
    private Integer curso = 0;
    private Integer tipoUsuario;
    private String senha = "";
    private String confSenha = "";
    private String novaSenha = "";
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private List<Usuario> professores = new ArrayList<Usuario>();
    private List<Usuario> alunosPesquisadores = new ArrayList<Usuario>();

    public UsuarioPropesMB() {
    }

    public List<Usuario> getUsuarios() {
        if (usuarios.isEmpty()) {
            try {
                controlador = new UsuarioControlador();
                usuarios = controlador.listar();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }
    
    public List<Usuario> getProfessores() {
        if (professores.isEmpty()) {
            try {
                controlador = new UsuarioControlador();
                professores = controlador.listarProfessores();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return professores;
    }

    public List<Usuario> getAlunosPesquisadores() {
        if (alunosPesquisadores.isEmpty()) {
            try {
                controlador = new UsuarioControlador();
                alunosPesquisadores = controlador.listarAlunosPesquisadores();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return alunosPesquisadores;
    }

    public void adicionar() throws Exception {
        if (senha.equals(confSenha)) {
            this.usuario.setNome(usuario.getNome().toUpperCase());
            this.usuario.setSenha(md5(senha));
            controlador = new UsuarioControlador();
            controlador.adicionar(this.usuario, curso, tipoUsuario);
            this.usuario = new Usuario();
            this.curso = 0;
            this.tipoUsuario = 0;
            this.usuarios.clear();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As senhas não conferem!", ""));
        }
    }

    public void alterar() {
        controlador = new UsuarioControlador();
        if (!novaSenha.equals("")) {
            if (novaSenha.equals(confSenha)) {
                usuario.setNome(usuario.getNome().toUpperCase());
                usuario.setSenha(md5(novaSenha));
                controlador.alterar(usuario);
                this.usuarios.clear();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As senhas não conferem!", ""));
            }
        } else {
            usuario.setNome(usuario.getNome().toUpperCase());
            controlador.alterar(usuario);
            this.usuarios.clear();
        }
    }

    public void excluir() {
        controlador = new UsuarioControlador();
        controlador.excluir(usuario);
        this.usuarios.clear();
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }
    
    public void prepararFormulario(){
        this.usuario = null;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getCurso() {
        return curso;
    }

    public void setCurso(Integer curso) {
        this.curso = curso;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
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

}
