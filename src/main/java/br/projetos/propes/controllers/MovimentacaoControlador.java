/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.projetos.propes.controllers;

import br.projetos.propes.entities.AcompanhamentoProjeto18Meses;
import br.projetos.propes.entities.AcompanhamentoProjeto2Anos;
import br.projetos.propes.entities.AcompanhamentoProjeto30Meses;
import br.projetos.propes.entities.AcompanhamentoProjeto3Anos;
import br.projetos.propes.entities.AcompanhamentoProjeto42Meses;
import br.projetos.propes.entities.AcompanhamentoProjeto4Anos;
import br.projetos.propes.entities.AcompanhamentoProjeto6Meses1Ano;
import br.projetos.propes.entities.EventoAluno;
import br.projetos.propes.entities.EventoColaborador;
import br.projetos.propes.entities.EventoSistema;
import br.projetos.propes.entities.MovimentacaoProjeto;
import br.projetos.propes.entities.Projeto;
import br.projetos.propes.entities.Usuario;
import br.projetos.propes.mail.MailAlertaAluno;
import br.projetos.propes.mail.MailAvisoCoordenacao;
import br.projetos.propes.mail.MailExePeriodo18;
import br.projetos.propes.mail.MailExePeriodo24;
import br.projetos.propes.mail.MailExePeriodo30;
import br.projetos.propes.mail.MailExePeriodo36;
import br.projetos.propes.mail.MailExePeriodo42;
import br.projetos.propes.mail.MailExePeriodo48;
import br.projetos.propes.mail.MailExePeriodo6a1;
import br.projetos.propes.utils.Datas;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
public class MovimentacaoControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public MovimentacaoControlador() {
    }

    public void adicionar(int projeto, int eventoSistema, int eventoColaborador, int eventoAluno, int responsavel, byte[] anexoMovimentacao, boolean notificarAlunoPesquisador) {
        try {

            //verifica se foi submetido arquivo ao formulario de movimentacao
            MovimentacaoProjeto movimentacaoProjeto = new MovimentacaoProjeto();
            if (anexoMovimentacao != null) {
                movimentacaoProjeto.setArquivoVinculado(anexoMovimentacao);
            }

            //verifica qual tipo de evento será cadastrado
            EventoColaborador eventoMovimentacao = new EventoColaborador();
            Projeto projetoMovimentacao = new Projeto();
            projetoMovimentacao = em.find(Projeto.class, projeto);
            movimentacaoProjeto.setResponsavel(em.find(Usuario.class, responsavel));
            movimentacaoProjeto.setProjeto(projetoMovimentacao);
            movimentacaoProjeto.setData(new Date());

            //Tratamento de data que servira como parametro para todos os tipos de e-mails
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            if (eventoSistema != 0) {
                movimentacaoProjeto.setEventoSistema(em.find(EventoSistema.class, eventoSistema));
            }
            if (eventoColaborador != 0) {
                eventoMovimentacao = em.find(EventoColaborador.class, eventoColaborador);
                movimentacaoProjeto.setEventoColaborador(eventoMovimentacao);
            }
            if (eventoAluno != 0) {
                EventoAluno evento = em.find(EventoAluno.class, eventoAluno);
                movimentacaoProjeto.setEventoAluno(evento);
                MailAvisoCoordenacao email = new MailAvisoCoordenacao();
                if (email.enviarEmailAvisoCoordenacao("responsavel@universidade.edu.br", projetoMovimentacao.getTitulo(), evento.getDescricao(), String.valueOf(projetoMovimentacao.getId()), projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getCurso().getDescricao(), String.valueOf(c.get(Calendar.YEAR)))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para a Pró-Reitoria!", ""));
                }
            }

            //variavel que controlará o cadastramento simultaneo de mais de um evento
            int maisDeUmEvento = 0;

            //se o evento for de colaborador e estiver colocando o projeto em execucao
            //ira cadastrar o acompanhamento do projeto de acordo com a modalidade do mesmo
            if (eventoColaborador != 0) {
                if (eventoMovimentacao.getDescricao().equals("PROJETO EM EXECUÇÃO")) {

                    maisDeUmEvento++;

                    int periodo = projetoMovimentacao.getModalidade().getPeriodo().getMeses();

                    Datas datasUtil = new Datas(); //CALCULADORA BÁSICA DE DATAS
                    if (periodo == 6) {
                        AcompanhamentoProjeto6Meses1Ano ap = new AcompanhamentoProjeto6Meses1Ano();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp(datasUtil.addMes(new Date(), 3));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 5));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto6Meses1Ano(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo6a1 email = new MailExePeriodo6a1();
                            if (email.enviarEmailExePeriodo6a1(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 3)), df.format(datasUtil.addMes(new Date(), 5)), df.format(datasUtil.addMes(new Date(), 6)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 12) {
                        AcompanhamentoProjeto6Meses1Ano ap = new AcompanhamentoProjeto6Meses1Ano();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRp(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 11));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto6Meses1Ano(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo6a1 email = new MailExePeriodo6a1();
                            if (email.enviarEmailExePeriodo6a1(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 11)), df.format(datasUtil.addMes(new Date(), 12)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 18) {
                        AcompanhamentoProjeto18Meses ap = new AcompanhamentoProjeto18Meses();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 18));
                        ap.setDataExpiracaoRp1(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp2(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 17));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto18Meses(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo18 email = new MailExePeriodo18();
                            if (email.enviarEmailExePeriodo18(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 12)), df.format(datasUtil.addMes(new Date(), 17)), df.format(datasUtil.addMes(new Date(), 18)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 24) {
                        AcompanhamentoProjeto2Anos ap = new AcompanhamentoProjeto2Anos();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 24));
                        ap.setDataExpiracaoRp1(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp2(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRp3(datasUtil.addMes(new Date(), 18));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 23));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto2Anos(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo24 email = new MailExePeriodo24();
                            if (email.enviarEmailExePeriodo24(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 12)), df.format(datasUtil.addMes(new Date(), 18)), df.format(datasUtil.addMes(new Date(), 23)), df.format(datasUtil.addMes(new Date(), 24)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 30) {
                        AcompanhamentoProjeto30Meses ap = new AcompanhamentoProjeto30Meses();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 30));
                        ap.setDataExpiracaoRp1(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp2(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRp3(datasUtil.addMes(new Date(), 18));
                        ap.setDataExpiracaoRp4(datasUtil.addMes(new Date(), 24));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 29));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto30Meses(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo30 email = new MailExePeriodo30();
                            if (email.enviarEmailExePeriodo30(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 12)), df.format(datasUtil.addMes(new Date(), 18)), df.format(datasUtil.addMes(new Date(), 24)), df.format(datasUtil.addMes(new Date(), 29)), df.format(datasUtil.addMes(new Date(), 30)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 36) {
                        AcompanhamentoProjeto3Anos ap = new AcompanhamentoProjeto3Anos();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 36));
                        ap.setDataExpiracaoRp1(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp2(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRp3(datasUtil.addMes(new Date(), 18));
                        ap.setDataExpiracaoRp4(datasUtil.addMes(new Date(), 24));
                        ap.setDataExpiracaoRp5(datasUtil.addMes(new Date(), 30));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 35));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto3Anos(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo36 email = new MailExePeriodo36();
                            if (email.enviarEmailExePeriodo36(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 12)), df.format(datasUtil.addMes(new Date(), 18)), df.format(datasUtil.addMes(new Date(), 24)), df.format(datasUtil.addMes(new Date(), 30)), df.format(datasUtil.addMes(new Date(), 35)), df.format(datasUtil.addMes(new Date(), 36)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 42) {
                        AcompanhamentoProjeto42Meses ap = new AcompanhamentoProjeto42Meses();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 42));
                        ap.setDataExpiracaoRp1(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp2(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRp3(datasUtil.addMes(new Date(), 18));
                        ap.setDataExpiracaoRp4(datasUtil.addMes(new Date(), 24));
                        ap.setDataExpiracaoRp5(datasUtil.addMes(new Date(), 30));
                        ap.setDataExpiracaoRp6(datasUtil.addMes(new Date(), 36));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 41));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto42Meses(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo42 email = new MailExePeriodo42();
                            if (email.enviarEmailExePeriodo42(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 12)), df.format(datasUtil.addMes(new Date(), 18)), df.format(datasUtil.addMes(new Date(), 24)), df.format(datasUtil.addMes(new Date(), 30)), df.format(datasUtil.addMes(new Date(), 36)), df.format(datasUtil.addMes(new Date(), 41)), df.format(datasUtil.addMes(new Date(), 42)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                    if (periodo == 48) {
                        AcompanhamentoProjeto4Anos ap = new AcompanhamentoProjeto4Anos();
                        ap.setDataExpiracaoProjeto(datasUtil.addMes(new Date(), 48));
                        ap.setDataExpiracaoRp1(datasUtil.addMes(new Date(), 6));
                        ap.setDataExpiracaoRp2(datasUtil.addMes(new Date(), 12));
                        ap.setDataExpiracaoRp3(datasUtil.addMes(new Date(), 18));
                        ap.setDataExpiracaoRp4(datasUtil.addMes(new Date(), 24));
                        ap.setDataExpiracaoRp5(datasUtil.addMes(new Date(), 30));
                        ap.setDataExpiracaoRp6(datasUtil.addMes(new Date(), 36));
                        ap.setDataExpiracaoRp7(datasUtil.addMes(new Date(), 42));
                        ap.setDataExpiracaoRf(datasUtil.addMes(new Date(), 47));
                        ap.setProjeto(projetoMovimentacao);
                        AcompanhamentoProjetosControlador apControlador = new AcompanhamentoProjetosControlador();
                        apControlador.adicionarProjeto4Anos(ap);
                        if (notificarAlunoPesquisador) {
                            MailExePeriodo48 email = new MailExePeriodo48();
                            if (email.enviarEmailExePeriodo48(df.format(new Date()), "responsavel@universidade.edu.br", projetoMovimentacao.getResponsavel().getNome(), projetoMovimentacao.getTitulo(), df.format(projetoMovimentacao.getDataCadastro()), projetoMovimentacao.getModalidade().getDescricao(), df.format(datasUtil.addMes(new Date(), 6)), df.format(datasUtil.addMes(new Date(), 12)), df.format(datasUtil.addMes(new Date(), 18)), df.format(datasUtil.addMes(new Date(), 24)), df.format(datasUtil.addMes(new Date(), 30)), df.format(datasUtil.addMes(new Date(), 36)), df.format(datasUtil.addMes(new Date(), 42)), df.format(datasUtil.addMes(new Date(), 47)), df.format(datasUtil.addMes(new Date(), 48)), String.valueOf(c.get(Calendar.YEAR)))) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                            }
                        }
                    }
                } else if (notificarAlunoPesquisador) {
                    MailAlertaAluno email = new MailAlertaAluno();
                    if (email.enviarEmailAlertaAluno("responsavel@universidade.edu.br", projetoMovimentacao.getTitulo(), String.valueOf(projetoMovimentacao.getId()), projetoMovimentacao.getResponsavel().getNome(), String.valueOf(c.get(Calendar.YEAR)))) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email de Alerta enviado para o Aluno/Pesquisador!", ""));
                    }
                }
            }

            if (maisDeUmEvento != 0) {

                int idEventoAutomatico = 0;

                try {
                    Query query = em.createQuery("SELECT es.id FROM EventoSistema es WHERE es.descricao LIKE '%PROJETO EM EXECUÇÃO%'");
                    query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                    idEventoAutomatico = (Integer) query.getSingleResult();
                } catch (Exception e) {
                    System.err.println("ERRO NA CONSULTA AO ID DO EVENTO DE SISTEMA");
                    e.printStackTrace();
                }

                if (idEventoAutomatico != 0) {
                    List<MovimentacaoProjeto> movimentacoesProjeto = new ArrayList<MovimentacaoProjeto>();
                    movimentacoesProjeto.add(movimentacaoProjeto);
                    movimentacaoProjeto = new MovimentacaoProjeto();
                    movimentacaoProjeto.setData(new Date());
                    movimentacaoProjeto.setProjeto(projetoMovimentacao);
                    movimentacaoProjeto.setEventoSistema(em.find(EventoSistema.class, idEventoAutomatico));
                    movimentacoesProjeto.add(movimentacaoProjeto);
                    em.getTransaction().begin();
                    for (MovimentacaoProjeto movimentacaoProjeto1 : movimentacoesProjeto) {
                        em.persist(movimentacaoProjeto1);
                    }
                    em.getTransaction().commit();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimentacao do Projeto '" + movimentacaoProjeto.getProjeto().getTitulo() + "' cadastrada.", ""));
                }

            } else {
                em.getTransaction().begin();
                em.persist(movimentacaoProjeto);
                em.getTransaction().commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimentacao do Projeto '" + movimentacaoProjeto.getProjeto().getTitulo() + "' cadastrada.", ""));
            }

        } catch (Throwable t) {

            t.printStackTrace();

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Igualdade de Registro do Projeto, Os dados de acompanhamento do Projeto já foram cadastrados.", null));
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no cadastro da movimentacao.", ""));
        } finally {
//            em.getTransaction().rollback();
            em.close();
        }
    }

    public void alterar(MovimentacaoProjeto movimentacaoProjeto) {
        try {
            em.getTransaction().begin();
            em.merge(movimentacaoProjeto);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Movimentacao do Projeto '" + movimentacaoProjeto.getProjeto().getTitulo() + "' atualizada.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na atualização da movimentacao.", ""));
        } finally {
            em.close();
        }
    }

    public void excluir(MovimentacaoProjeto movimentacaoProjeto) {
        try {
            MovimentacaoProjeto obj_gerenciado = em.merge(movimentacaoProjeto); //alterando o estado de desacoplado para gerenciado
            em.getTransaction().begin();
            em.remove(obj_gerenciado);
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A Movimentacao do projeto " + movimentacaoProjeto.getProjeto().getTitulo() + " foi removida.", ""));
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na exclusão da movimentacao.", ""));
        } finally {
            em.close();
        }
    }

    public List<MovimentacaoProjeto> listar() {
        List<MovimentacaoProjeto> movimentacoesProjeto = new ArrayList<>();
        Collection<MovimentacaoProjeto> movimentacaoProjetoCollection = new ArrayList();
        Query query = em.createNamedQuery("MovimentacaoProjeto.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        movimentacaoProjetoCollection = query.getResultList();
        movimentacoesProjeto = new ArrayList(movimentacaoProjetoCollection);
        return movimentacoesProjeto;
    }

    public List<MovimentacaoProjeto> listar(int projetoId) {
        List<MovimentacaoProjeto> movimentacoesProjeto = new ArrayList<>();
        Collection<MovimentacaoProjeto> movimentacaoProjetoCollection = new ArrayList();
        Query query = em.createQuery("SELECT m FROM MovimentacaoProjeto m WHERE m.projeto.id = " + projetoId + "");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        movimentacaoProjetoCollection = query.getResultList();
        movimentacoesProjeto = new ArrayList(movimentacaoProjetoCollection);
        return movimentacoesProjeto;
    }

}
