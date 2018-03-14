package br.projetos.propes.reports;

import java.math.BigInteger;

/**
 *
 * @author eneas
 */
public class ResumoFomentoProjetos implements Comparable<ResumoFomentoProjetos> {

    String descricao;
    BigInteger quantitativo;

    public ResumoFomentoProjetos() {
    }

    public ResumoFomentoProjetos(String descricao, BigInteger quantitativo) {
        this.descricao = descricao;
        this.quantitativo = quantitativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigInteger getQuantitativo() {
        return quantitativo;
    }

    public void setQuantitativo(BigInteger quantitativo) {
        this.quantitativo = quantitativo;
    }

    @Override
    public int compareTo(ResumoFomentoProjetos resumoFomentoProjetos) {
        int resultado = this.quantitativo.compareTo(resumoFomentoProjetos.quantitativo);
        return resultado;
    }

}
