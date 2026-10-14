package br.pucpr.projeto_crud_java.observer;

import java.time.LocalDate;

/**
 * Evento publicado pelo Subject (MonitorPrecos) quando um novo preço é registrado.
 * Imutável: carrega tudo que os observadores precisam para decidir se alertam.
 */
public final class VariacaoPreco {

    private final int idProduto;
    private final Double precoAnterior;      // null se for o primeiro preço do produto
    private final Double menorPrecoAnterior; // null se for o primeiro preço do produto
    private final double precoNovo;
    private final LocalDate data;

    public VariacaoPreco(int idProduto, Double precoAnterior, Double menorPrecoAnterior,
                         double precoNovo, LocalDate data) {
        this.idProduto = idProduto;
        this.precoAnterior = precoAnterior;
        this.menorPrecoAnterior = menorPrecoAnterior;
        this.precoNovo = precoNovo;
        this.data = data;
    }

    public int getIdProduto() { return idProduto; }
    public Double getPrecoAnterior() { return precoAnterior; }
    public Double getMenorPrecoAnterior() { return menorPrecoAnterior; }
    public double getPrecoNovo() { return precoNovo; }
    public LocalDate getData() { return data; }

    public boolean temPrecoAnterior() { return precoAnterior != null && precoAnterior > 0; }

    /** Variação percentual em relação ao preço anterior (0 se não houver preço anterior). */
    public double variacaoPercentual() {
        if (!temPrecoAnterior()) return 0;
        return (precoNovo - precoAnterior) / precoAnterior * 100.0;
    }
}
