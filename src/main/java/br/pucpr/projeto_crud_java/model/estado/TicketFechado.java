package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState final: ticket encerrado. Somente leitura. */
public class TicketFechado implements EstadoStatus {
    @Override public String getNome() { return "Fechado"; }
    @Override public EstadoStatus proximo() { return this; }
    @Override public String rotuloAvancar() { return "-"; }
    @Override public boolean permiteEdicao() { return false; }
    @Override public boolean permiteExclusao() { return false; }
}
