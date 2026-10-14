package br.pucpr.projeto_crud_java.observer;

/**
 * PADRÃO: Observer (GoF, comportamental) - interface Observer.
 * Qualquer classe interessada em variações de preço implementa este contrato
 * e se registra no MonitorPrecos.
 */
public interface ObservadorPreco {
    void precoRegistrado(VariacaoPreco variacao);
}
