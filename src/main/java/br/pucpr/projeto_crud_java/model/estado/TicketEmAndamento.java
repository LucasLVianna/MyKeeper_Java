package br.pucpr.projeto_crud_java.model.estado;

// não pode excluir um ticket em atendimento
public class TicketEmAndamento implements EstadoStatus {
    @Override public String getNome() { return "Em andamento"; }
    @Override public EstadoStatus proximo() { return new TicketResolvido(); }
    @Override public EstadoStatus anterior() { return new TicketAberto(); }
    @Override public String rotuloAvancar() { return "Marcar como resolvido"; }
    @Override public String rotuloVoltar() { return "Devolver para a fila"; }
    @Override public boolean permiteExclusao() { return false; }
}
