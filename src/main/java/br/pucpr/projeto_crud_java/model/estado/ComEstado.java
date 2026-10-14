package br.pucpr.projeto_crud_java.model.estado;

import br.pucpr.projeto_crud_java.model.Identificavel;

/**
 * Papel "Context" do padrão State: entidade cujo comportamento depende do
 * estado atual. A transição é delegada ao objeto de estado.
 */
public interface ComEstado extends Identificavel {

    String getStatus();

    EstadoStatus getEstado();

    /** Delega ao estado atual a transição para o próximo estado. */
    void avancarStatus();

    /** Delega ao estado atual a transição de retorno. */
    void voltarStatus();
}
