package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState final: estoque arquivado. Somente leitura. */
public class EstoqueArquivado implements EstadoStatus {
    @Override public String getNome() { return "Arquivado"; }
    @Override public EstadoStatus proximo() { return this; }
    @Override public String rotuloAvancar() { return "-"; }
    @Override public boolean permiteEdicao() { return false; }
    @Override public boolean permiteExclusao() { return false; }
}
