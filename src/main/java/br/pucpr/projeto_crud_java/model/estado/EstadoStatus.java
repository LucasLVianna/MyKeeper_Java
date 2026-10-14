package br.pucpr.projeto_crud_java.model.estado;

/**
 * PADRÃO: State (GoF, comportamental) - interface State.
 *
 * Cada status de uma entidade (ticket, lista de compras, estoque) é um objeto
 * que sabe: qual é o próximo/anterior estado do fluxo e quais operações
 * (editar, excluir) são permitidas enquanto a entidade estiver nele.
 * O contexto (TicketSuporte, ListaCompras, Estoque) delega essas decisões ao
 * estado atual em vez de usar if/switch sobre uma String de status.
 */
public interface EstadoStatus {

    /** Nome persistido do estado (ex.: "Aberto"). */
    String getNome();

    /** Próximo estado do fluxo; retorna {@code this} se for um estado final. */
    EstadoStatus proximo();

    /** Estado anterior (reabertura); retorna {@code this} se não houver retorno. */
    default EstadoStatus anterior() { return this; }

    /** Texto do botão que executa a transição para o próximo estado. */
    String rotuloAvancar();

    default String rotuloVoltar() { return "Voltar status"; }

    default boolean permiteAvancar() { return proximo() != this; }

    default boolean permiteVoltar() { return anterior() != this; }

    default boolean permiteEdicao() { return true; }

    default boolean permiteExclusao() { return true; }
}
