package br.pucpr.projeto_crud_java.observer;

// Observer: quem quiser ser avisado de preços novos implementa e se registra no MonitorPrecos
public interface ObservadorPreco {
    void precoRegistrado(VariacaoPreco variacao);
}
