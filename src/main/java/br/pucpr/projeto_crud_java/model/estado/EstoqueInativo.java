package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState: estoque desativado. Não pode ser editado; pode ser reativado, arquivado ou excluído. */
public class EstoqueInativo implements EstadoStatus {
    @Override public String getNome() { return "Inativo"; }
    @Override public EstadoStatus proximo() { return new EstoqueArquivado(); }
    @Override public EstadoStatus anterior() { return new EstoqueAtivo(); }
    @Override public String rotuloAvancar() { return "Arquivar estoque"; }
    @Override public String rotuloVoltar() { return "Reativar estoque"; }
    @Override public boolean permiteEdicao() { return false; }
}
