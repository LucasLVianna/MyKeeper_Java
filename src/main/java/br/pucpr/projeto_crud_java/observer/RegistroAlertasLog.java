package br.pucpr.projeto_crud_java.observer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

// Grava todos os preços registrados em alertas_preco.log
public class RegistroAlertasLog implements ObservadorPreco {

    private final String caminho;

    public RegistroAlertasLog() {
        this("alertas_preco.log");
    }

    public RegistroAlertasLog(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public void precoRegistrado(VariacaoPreco v) {
        try (PrintWriter out = new PrintWriter(new FileWriter(caminho, true))) {
            out.println(String.format(Locale.US, "%s | produto=%d | anterior=%s | novo=%.2f | variacao=%.1f%%",
                    v.getData(), v.getIdProduto(),
                    v.getPrecoAnterior() == null ? "-" : String.format(Locale.US, "%.2f", v.getPrecoAnterior()),
                    v.getPrecoNovo(), v.variacaoPercentual()));
        } catch (IOException e) {
            System.err.println("Erro ao gravar log de preços: " + e.getMessage());
        }
    }
}
