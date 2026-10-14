package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState: lista pronta para ir ao mercado. Não pode ser editada, mas pode ser reaberta. */
public class ListaFinalizada implements EstadoStatus {
    @Override public String getNome() { return "Finalizada"; }
    @Override public EstadoStatus proximo() { return new ListaComprada(); }
    @Override public EstadoStatus anterior() { return new ListaEmElaboracao(); }
    @Override public String rotuloAvancar() { return "Registrar compra"; }
    @Override public String rotuloVoltar() { return "Reabrir lista"; }
    @Override public boolean permiteEdicao() { return false; }
}
