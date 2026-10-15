package br.pucpr.projeto_crud_java.observer;

import java.util.Locale;
import java.util.function.Consumer;

// Avisa quando o preço sobe mais que o limite (%)
public class AlertaAumentoPreco implements ObservadorPreco {

    private final double limitePercentual;
    private final Consumer<String> saida;

    public AlertaAumentoPreco(double limitePercentual, Consumer<String> saida) {
        this.limitePercentual = limitePercentual;
        this.saida = saida;
    }

    @Override
    public void precoRegistrado(VariacaoPreco v) {
        if (v.temPrecoAnterior() && v.variacaoPercentual() >= limitePercentual) {
            saida.accept(String.format(Locale.US,
                    "ALERTA: produto %d subiu %.1f%% (R$ %.2f -> R$ %.2f)",
                    v.getIdProduto(), v.variacaoPercentual(), v.getPrecoAnterior(), v.getPrecoNovo()));
        }
    }
}
