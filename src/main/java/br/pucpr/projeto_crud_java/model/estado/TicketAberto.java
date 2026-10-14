package br.pucpr.projeto_crud_java.model.estado;

/** ConcreteState: ticket recém-criado, aguardando atendimento. */
public class TicketAberto implements EstadoStatus {
    @Override public String getNome() { return "Aberto"; }
    @Override public EstadoStatus proximo() { return new TicketEmAndamento(); }
    @Override public String rotuloAvancar() { return "Iniciar atendimento"; }
}
