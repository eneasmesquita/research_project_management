package br.projetos.propes.reports;

/**
 *
 * @author eneas
 */
public class ResumoModalidadeProjetos {

    String descricao;
    long quantitativo;

    public ResumoModalidadeProjetos() {
    }

    public ResumoModalidadeProjetos(String descricao, long quantitativo) {
        this.descricao = descricao;
        this.quantitativo = quantitativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getQuantitativo() {
        return quantitativo;
    }

    public void setQuantitativo(long quantitativo) {
        this.quantitativo = quantitativo;
    }
}
