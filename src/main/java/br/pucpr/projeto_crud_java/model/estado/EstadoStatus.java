package br.pucpr.projeto_crud_java.model.estado;

// State: cada status é uma classe que diz qual é o próximo/anterior
// e se o registro pode ser editado ou excluído nele.
public interface EstadoStatus {

    String getNome();

    // estados finais retornam this
    EstadoStatus proximo();

    default EstadoStatus anterior() { return this; }

    String rotuloAvancar();

    default String rotuloVoltar() { return "Voltar status"; }

    default boolean permiteAvancar() { return proximo() != this; }

    default boolean permiteVoltar() { return anterior() != this; }

    default boolean permiteEdicao() { return true; }

    default boolean permiteExclusao() { return true; }
}
