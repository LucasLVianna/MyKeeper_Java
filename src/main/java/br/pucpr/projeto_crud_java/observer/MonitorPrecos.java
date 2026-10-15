package br.pucpr.projeto_crud_java.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Guarda os observadores e avisa todos quando um preço é registrado.
// O ArquivoHistoricoPreco não sabe quais alertas existem.
public class MonitorPrecos {

    public static final MonitorPrecos INSTANCE = new MonitorPrecos();

    private final List<ObservadorPreco> observadores = new CopyOnWriteArrayList<>();

    public void registrar(ObservadorPreco o) {
        if (o != null && !observadores.contains(o)) observadores.add(o);
    }

    public void remover(ObservadorPreco o) {
        observadores.remove(o);
    }

    public int quantidadeObservadores() {
        return observadores.size();
    }

    public void notificar(VariacaoPreco variacao) {
        for (ObservadorPreco o : observadores) {
            try {
                o.precoRegistrado(variacao);
            } catch (RuntimeException e) {
                // se um observador der erro, os outros continuam sendo avisados
                System.err.println("Falha em observador de preço: " + e.getMessage());
            }
        }
    }
}
