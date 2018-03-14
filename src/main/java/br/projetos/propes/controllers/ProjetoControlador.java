/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.Curso;
import br.projetos.propes.entities.Fomentador;
import java.io.Serializable;
import javax.persistence.Query;
import br.projetos.propes.entities.InstituicaoConvenio;
import br.projetos.propes.entities.ModalidadeProjeto;
import br.projetos.propes.entities.MovimentacaoProjeto;
import br.projetos.propes.entities.Usuario;
import br.projetos.propes.entities.Projeto;
import br.projetos.propes.login.AutenticadorBean;
import static br.projetos.propes.views.ViewControladorMB.getManagedBean;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.projetos.propes.mail.MailNovoProjeto;
import br.projetos.propes.utils.Datas;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author Eneas Mesquita
 */
public class ProjetoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();
    AutenticadorBean autB = (AutenticadorBean) getManagedBean("LoginController");

    public ProjetoControlador() {
    }

    public void adicionar(Projeto projeto, Integer responsavel, Integer instituicaoConveniada, Integer modalidade, Integer curso, Integer fomentador, BigInteger valorFomento, byte[] termoResponsabilidade) {
        try {
            em.getTransaction().begin();
            projeto.setResponsavel(em.find(Usuario.class, responsavel));
            projeto.setModalidade(em.find(ModalidadeProjeto.class, modalidade));
            projeto.setCurso(em.find(Curso.class, curso));
            if (fomentador != 0) {
                projeto.setFomentador(em.find(Fomentador.class, fomentador));
                projeto.setValorFomento(valorFomento);
            }
            if (instituicaoConveniada != 0) {
                projeto.setInstituicaoConvenio(em.find(InstituicaoConvenio.class, instituicaoConveniada));
            }
            em.persist(projeto);

            //A IMPLEMENTACAO ABAIXO SE FEZ NECESSARIA PORQUE O ID DO EVENTO 'TERMO DE RESPONSABILIDADE' É VOLATIL
            Query query = em.createQuery("SELECT es.id FROM EventoSistema es WHERE es.descricao LIKE 'TERMO DE RESPONSABILIDADE'");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Integer id_eventoSistema = (Integer) query.getSingleResult();

            em.getTransaction().commit();
            MovimentacaoControlador mControlador = new MovimentacaoControlador();
            //DEPOIS DO COMMIT O EclipseLink JA POSSUI O NOVO ID DO PROJETO CADASTRADO
            mControlador.adicionar(projeto.getId(), id_eventoSistema, 0, 0, responsavel, termoResponsabilidade, true);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto " + projeto.getTitulo() + " cadastrado.", ""));
            //ENVIO DE EMAIL PARA A PROPES SOBRE O CADASTRO DE NOVO PROJETO
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            MailNovoProjeto email = new MailNovoProjeto();
            if (email.enviarEmailNovoProjeto("responsavel@universidade.edu.br", projeto.getTitulo(), String.valueOf(projeto.getId()), projeto.getResponsavel().getNome(), projeto.getCurso().getDescricao(), String.valueOf(c.get(Calendar.YEAR)))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para a Pro-Reitoria!", ""));
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro do projeto.", ""));
        } finally {
            em.close();
        }
    }

    public void alterar(Projeto projeto) {
        try {
            em.getTransaction().begin();
            em.merge(projeto);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Projeto " + projeto.getTitulo() + " atualizado.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualizacao do projeto.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(Projeto projeto) {
        try {
            Projeto obj_gerenciado = em.merge(projeto); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Projeto " + projeto.getTitulo() + " foi removido.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusao do projeto.", ""));
        } finally {
            em.close();
        }
    }

    public List<Projeto> listar() {
        List<Projeto> projetos = new ArrayList<>();
        Collection<Projeto> projetoCollection = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Projeto p ORDER BY p.id ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        projetoCollection = query.getResultList();
        projetos = new ArrayList(projetoCollection);

        //TESTES
        for (Projeto projeto : projetos) {
            List<MovimentacaoProjeto> movimentacao = new ArrayList(projeto.getMovimentacaoProjetoCollection());
            MovimentacaoProjeto ultima_movimentacao = movimentacao.get(movimentacao.size() - 1);
            //verifica o tipo de evento que esta presente na ultima movimentacao
            if (ultima_movimentacao.getEventoAluno() != null) {
                //  int periodo = projetoMovimentacao.getModalidade().getPeriodo().getMeses();
            }
            //metodo contais e pesado, tentar outra alternativa
            if (ultima_movimentacao.getEventoColaborador() != null && ultima_movimentacao.getEventoColaborador().getDescricao().equals("PROJETO EM EXECUÃ‡ÃƒO")) {
                int periodo = ultima_movimentacao.getProjeto().getModalidade().getPeriodo().getId();
                Datas datasUtil = new Datas(); //CALCULADORA BASICA DE DATAS
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                DateTime dataAtual = new DateTime(datasUtil.addDia(ultima_movimentacao.getData(), 95).getTime());
                System.out.println("ultimo evento encontrado deste projeto: " + ultima_movimentacao.getEventoColaborador().getDescricao());
                System.out.println("Periodo da Modalidade do Projeto: " + periodo);
                System.out.println("Data das movimentacoes: " + dataAtual);
                DateTime dataMovimentacao = new DateTime(ultima_movimentacao.getData().getTime());

                if (periodo == 1) {
                    //intervalo de 3 meses
                    DateTime dataRelatorio = new DateTime(datasUtil.addMes(ultima_movimentacao.getData(), 3).getTime());
                    System.out.println("DATA DE ENTREGA DO RELATORIO: "+dataRelatorio);
                    long dias = Days.daysBetween(dataAtual, dataRelatorio).getDays();
                    if (dias <= 30 && dias > 15) {
                        //enviar email alerta 1
                        System.out.println("OPA, OLHA O E-MAIL SENDO ENVIADO! --> 30 DIAS - PROJETO NUMERO " + projeto.getId());
                    } else if (dias <= 15 && dias > 5) {
                        //enviar email alerta 2
                        System.out.println("OPA, OLHA O E-MAIL SENDO ENVIADO! --> 15 DIAS - PROJETO NUMERO " + projeto.getId());
                    } else if (dias <= 5 && dias > 0) {
                        //enviar email alerta 3
                        System.out.println("DIAS CALCULADOS NO INTERVALO: "+dias);
                        System.out.println("OPA, OLHA O E-MAIL SENDO ENVIADO! --> 5 DIAS - PROJETO NUMERO " + projeto.getId());
                    } else if (dias <= 0){
                        System.out.println("PRAZO DE ENTREGA DE RELATORIO EXPIRADO: " + dataRelatorio);
                    }
                } else {
                    //intervalo de 6 meses
                    DateTime dataRelatorio = new DateTime(datasUtil.addMes(ultima_movimentacao.getData(), 6).getTime());
                    System.out.println("DATA RELATORIO das movimentacoes: " + dataRelatorio);
                    long dias = Days.daysBetween(dataAtual, dataRelatorio).getDays();
                    if (dias <= 30 && dias > 15) {
                        //enviar email alerta 1
                        System.out.println("OPA, OLHA O E-MAIL SENDO ENVIADO! --> 30 DIAS - PROJETO NUMERO " + projeto.getId());
                    } else if (dias <= 15 && dias > 5) {
                        //enviar email alerta 2
                        System.out.println("OPA, OLHA O E-MAIL SENDO ENVIADO! --> 15 DIAS - PROJETO NUMERO " + projeto.getId());
                    } else if (dias <= 5 && dias > 0) {
                        //enviar email alerta 3
                        System.out.println("DIAS CALCULADOS NO INTERVALO: "+dias);
                        System.out.println("OPA, OLHA O E-MAIL SENDO ENVIADO! --> 5 DIAS - PROJETO NUMERO " + projeto.getId());
                    } else if (dias <= 0){
                        System.out.println("PRAZO DE ENTREGA DE RELATÃ“RIO EXPIRADO: " + dataRelatorio);
                    }
                }
//                
            }

        }
        // FIM DOS TESTES
        return projetos;
    }

    public List<Projeto> listarMeusProjetos() {
        int idUsuario = (Integer) autB.getSession().getAttribute("id_usuario");
        List<Projeto> projetos = new ArrayList<>();
        Collection<Projeto> projetoCollection = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Projeto p WHERE p.responsavel.id = :idUsuario ORDER BY p.id ASC");
        query.setParameter("idUsuario", idUsuario);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        projetoCollection = query.getResultList();
        projetos = new ArrayList(projetoCollection);
        return projetos;
    }

}
