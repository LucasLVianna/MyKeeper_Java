package br.pucpr.projeto_crud_java.ordenacao;

import java.util.List;

/**
 * PADRÃO: Strategy (GoF, comportamental) - interface Strategy.
 *
 * Encapsula um critério de ordenação intercambiável. O contexto (CrudUI)
 * recebe uma estratégia por vez e a troca em tempo de execução, quando o
 * usuário escolhe outro critério na tela, sem nenhum if/switch por critério.
 */
public interface EstrategiaOrdenacao<T> {

    /** Texto exibido na lista de critérios da tela. */
    String getNome();

    /** Devolve uma nova lista ordenada (a original não é alterada). */
    List<T> ordenar(List<T> itens);
}
