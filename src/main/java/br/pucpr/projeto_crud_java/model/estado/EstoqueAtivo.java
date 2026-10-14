package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState: estoque em uso. Pode ser editado, mas não excluído (precisa ser desativado antes). */
public class EstoqueAtivo implements EstadoStatus {
    @Override public String getNome() { return "Ativo"; }
    @Override public EstadoStatus proximo() { return new EstoqueInativo(); }
    @Override public String rotuloAvancar() { return "Desativar estoque"; }
    @Override public boolean permiteExclusao() { return false; }
}
