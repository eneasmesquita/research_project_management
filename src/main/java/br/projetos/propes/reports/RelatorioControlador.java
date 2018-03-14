package br.projetos.propes.reports;

import br.projetos.propes.entities.Projeto;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author Eneas Mesquita
 */
public class RelatorioControlador implements Serializable {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("propes_PU");
    private EntityManager em = emf.createEntityManager();

    public RelatorioControlador() {
    }

    public List<ResumoModalidadeProjetos> preencherResumoModalidadeProjeto() {

        //PREENCHENDO O ATRIBUTO DESCRICAO DO OBJ DA CLASSE ResumoModalidadeProjetos
        List<ResumoModalidadeProjetos> resumomp = new ArrayList<>();
        List<String> descricaoModalidadeProjeto = new ArrayList<>();
        Collection<String> modalidadeCollection = new ArrayList<>();
        Query query = em.createQuery("SELECT mp.descricao FROM ModalidadeProjeto mp ORDER BY mp.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        modalidadeCollection = query.getResultList();
        descricaoModalidadeProjeto = new ArrayList(modalidadeCollection);

        for (String dmc : descricaoModalidadeProjeto) {
            resumomp.add(new ResumoModalidadeProjetos(dmc, 0));
        }

        for (ResumoModalidadeProjetos rmp : resumomp) {
            query = em.createQuery("SELECT COUNT(p.modalidade) FROM Projeto p WHERE p.modalidade.descricao like :descricao");
            query.setParameter("descricao", rmp.descricao);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            rmp.quantitativo = (Long) query.getSingleResult();
        }

        return resumomp;
    }

    public List<ResumoConveniosProjetos> preencherResumoConveniosProjetos() {

        //PREENCHENDO O ATRIBUTO DESCRICAO DO OBJ DA CLASSE ResumoConveniosProjetos
        List<ResumoConveniosProjetos> resumocp = new ArrayList<>();
        List<String> descricaoInstConvenioProjeto = new ArrayList<>();
        Collection<String> conveniosCollection = new ArrayList();
        Query query = em.createQuery("SELECT ic.descricao FROM InstituicaoConvenio ic ORDER BY ic.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        conveniosCollection = query.getResultList();
        descricaoInstConvenioProjeto = new ArrayList(conveniosCollection);

        for (String dicp : descricaoInstConvenioProjeto) {
            resumocp.add(new ResumoConveniosProjetos(dicp, 0));
        }

        for (ResumoConveniosProjetos ccp : resumocp) {
            query = em.createQuery("SELECT COUNT(p.instituicaoConvenio) FROM Projeto p WHERE p.instituicaoConvenio.descricao like :descricao");
            query.setParameter("descricao", ccp.descricao);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            ccp.quantitativo = (Long) query.getSingleResult();
        }

        return resumocp;
    }

    public List<Projeto> preencherResumoFomentoProjetos() {
        List<Projeto> projetosFomento = new ArrayList<>();
        Collection<Projeto> projetosFomentoCollection = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Projeto p WHERE p.valorFomento <> 0 ORDER BY p.valorFomento DESC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        projetosFomentoCollection = query.getResultList();
        projetosFomento = new ArrayList(projetosFomentoCollection);
        return projetosFomento;
    }

    public List<ResumoFomentoProjetos> preencherResumoFomentadoresProjetos() {

        List<ResumoFomentoProjetos> resumofp = new ArrayList<>();
        List<String> descricaoFomentadores = new ArrayList<>();
        Collection<String> fomentadoresCollection = new ArrayList<>();
        Query query = em.createQuery("SELECT DISTINCT f.descricao FROM Fomentador f, Projeto p WHERE p.fomentador.id = f.id ORDER BY f.descricao ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        fomentadoresCollection = query.getResultList();
        descricaoFomentadores = new ArrayList(fomentadoresCollection);

        for (String dfp : descricaoFomentadores) {
            resumofp.add(new ResumoFomentoProjetos(dfp, BigInteger.ZERO));
        }

        for (ResumoFomentoProjetos rfp : resumofp) {
            query = em.createQuery("SELECT SUM(p.valorFomento) FROM Projeto p, Fomentador f WHERE p.fomentador.id = f.id AND p.fomentador.descricao like :descricao");
            query.setParameter("descricao", rfp.descricao);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (query.getSingleResult() != null) {
                rfp.quantitativo = (BigInteger) query.getSingleResult();
            }
        }

        Collections.reverse(resumofp);
        return resumofp;
    }

    public List<Projeto> listarProjetosDeAluno(int idAlunoPesquisador) {
        List<Projeto> projetos = new ArrayList<>();
        Collection<Projeto> projetoCollection = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Projeto p WHERE p.responsavel.id = :alunoPesquisador ORDER BY p.titulo ASC");
        query.setParameter("alunoPesquisador", idAlunoPesquisador);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        projetoCollection = query.getResultList();
        projetos = new ArrayList(projetoCollection);
        return projetos;
    }

    public long calcularTotalConvenios(int idAlunoPesquisador) {
        long totalConvenios = 0;
        Query query = em.createQuery("SELECT COUNT(p.instituicaoConvenio) FROM Projeto p WHERE p.responsavel.id = :alunoPesquisador");
        query.setParameter("alunoPesquisador", idAlunoPesquisador);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        totalConvenios = (Long) query.getSingleResult();
        return totalConvenios;
    }

    public long calcularTotalFomento(int idAlunoPesquisador) {
        long totalFomento = 0;
        try {
            Query query = em.createQuery("SELECT SUM(p.valorFomento) FROM Projeto p WHERE p.responsavel.id = :alunoPesquisador");
            query.setParameter("alunoPesquisador", idAlunoPesquisador);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            totalFomento = (Long) query.getSingleResult();
        } catch (NullPointerException e) {
        } catch (NoResultException e) {
        }
        return totalFomento;
    }

    public List<Projeto> listarNovosProjetosAdded() {
        List<Projeto> projetos = new ArrayList<Projeto>();
        Collection<Projeto> projetosCollection = new ArrayList<Projeto>();
        Query query = em.createNativeQuery("SELECT \n"
                + "  p.* \n"
                + "FROM \n"
                + "  propes.projeto p,\n"
                + "( \n"
                + "  SELECT DISTINCT mp.projeto, count(mp.projeto) AS qtd_projetos FROM propes.movimentacao_projeto mp GROUP BY mp.projeto\n"
                + "  ) AS m \n"
                + "WHERE \n"
                + "  p.id = m.projeto AND \n"
                + "  m.qtd_projetos = 1", Projeto.class);
        projetosCollection = query.getResultList();
        projetos = new ArrayList(projetosCollection);
        return projetos;
    }

}
