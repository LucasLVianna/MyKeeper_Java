package br.pucpr.projeto_crud_java.model.estado;

import br.pucpr.projeto_crud_java.model.Identificavel;

// Entidade que tem status (ticket, lista de compras e estoque)
public interface ComEstado extends Identificavel {

    String getStatus();

    EstadoStatus getEstado();

    void avancarStatus();

    void voltarStatus();
}
