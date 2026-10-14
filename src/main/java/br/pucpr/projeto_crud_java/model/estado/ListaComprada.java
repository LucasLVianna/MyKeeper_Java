package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState final: compra realizada. Somente leitura, preservada como histórico. */
public class ListaComprada implements EstadoStatus {
    @Override public String getNome() { return "Comprada"; }
    @Override public EstadoStatus proximo() { return this; }
    @Override public String rotuloAvancar() { return "-"; }
    @Override public boolean permiteEdicao() { return false; }
    @Override public boolean permiteExclusao() { return false; }
}
