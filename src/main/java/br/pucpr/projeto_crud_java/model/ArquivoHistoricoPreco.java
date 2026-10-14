package br.pucpr.projeto_crud_java.model;

import br.pucpr.projeto_crud_java.observer.MonitorPrecos;
import br.pucpr.projeto_crud_java.observer.VariacaoPreco;

/**
 * Repositório do histórico de preços.
 * Template Method: define o caminho do arquivo e usa o gancho aposAdicionar.
 * Observer: o gancho publica um evento no MonitorPrecos (Subject).
 */
public class ArquivoHistoricoPreco extends RepositorioArquivo<HistoricoPreco> {

    public static final ArquivoHistoricoPreco INSTANCE = new ArquivoHistoricoPreco();

    private ArquivoHistoricoPreco() { }

    @Override
    protected String caminhoArquivo() {
        return "historico_precos.dat";
    }

    @Override
    protected void validar(HistoricoPreco novo) {
        if (novo.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
    }

    @Override
    protected void aposAdicionar(HistoricoPreco novo) {
        HistoricoPreco anterior = null;
        Double menor = null;
        for (HistoricoPreco h : lerLista()) {
            if (h.getId() == novo.getId() || h.getIdProduto() != novo.getIdProduto()) continue;
            if (menor == null || h.getPreco() < menor) menor = h.getPreco();
            boolean maisRecente = anterior == null
                    || h.getData().isAfter(anterior.getData())
                    || (h.getData().isEqual(anterior.getData()) && h.getId() > anterior.getId());
            if (!h.getData().isAfter(novo.getData()) && maisRecente) anterior = h;
        }
        MonitorPrecos.INSTANCE.notificar(new VariacaoPreco(
                novo.getIdProduto(),
                anterior == null ? null : anterior.getPreco(),
                menor, novo.getPreco(), novo.getData()));
    }
}
