package br.pucpr.projeto_crud_java.observer;

import java.util.Locale;
import java.util.function.Consumer;

// Avisa quando o preço é o menor já registrado para o produto
public class AlertaMenorPreco implements ObservadorPreco {

    private final Consumer<String> saida;

    public AlertaMenorPreco(Consumer<String> saida) {
        this.saida = saida;
    }

    @Override
    public void precoRegistrado(VariacaoPreco v) {
        Double menor = v.getMenorPrecoAnterior();
        if (menor != null && v.getPrecoNovo() < menor) {
            saida.accept(String.format(Locale.US,
                    "OPORTUNIDADE: produto %d está no menor preço já registrado (R$ %.2f, antes R$ %.2f)",
                    v.getIdProduto(), v.getPrecoNovo(), menor));
        }
    }
}
