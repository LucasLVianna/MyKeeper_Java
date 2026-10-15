package br.pucpr.projeto_crud_java.model.estado;

// para excluir, o estoque precisa ser desativado antes
public class EstoqueAtivo implements EstadoStatus {
    @Override public String getNome() { return "Ativo"; }
    @Override public EstadoStatus proximo() { return new EstoqueInativo(); }
    @Override public String rotuloAvancar() { return "Desativar estoque"; }
    @Override public boolean permiteExclusao() { return false; }
}
