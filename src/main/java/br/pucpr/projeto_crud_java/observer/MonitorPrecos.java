package br.pucpr.projeto_crud_java.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * PADRÃO: Observer (GoF, comportamental) - Subject.
 *
 * Mantém a lista de observadores e os notifica a cada preço registrado.
 * Quem registra o preço (ArquivoHistoricoPreco) não conhece os observadores
 * concretos, o que permite acrescentar novos alertas sem alterar o repositório.
 */
public class MonitorPrecos {

    /** Instância compartilhada pela aplicação (repositório e telas usam o mesmo assunto). */
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
                // um observador com defeito não pode impedir os demais de serem avisados
                System.err.println("Falha em observador de preço: " + e.getMessage());
            }
        }
    }
}
