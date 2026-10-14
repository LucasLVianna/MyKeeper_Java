package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState: lista sendo montada. Pode ser editada e excluída. */
public class ListaEmElaboracao implements EstadoStatus {
    @Override public String getNome() { return "Em elaboração"; }
    @Override public EstadoStatus proximo() { return new ListaFinalizada(); }
    @Override public String rotuloAvancar() { return "Finalizar lista"; }
}
