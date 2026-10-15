package br.pucpr.projeto_crud_java.model.estado;

public class EstoqueArquivado implements EstadoStatus {
    @Override public String getNome() { return "Arquivado"; }
    @Override public EstadoStatus proximo() { return this; }
    @Override public String rotuloAvancar() { return "-"; }
    @Override public boolean permiteEdicao() { return false; }
    @Override public boolean permiteExclusao() { return false; }
}
