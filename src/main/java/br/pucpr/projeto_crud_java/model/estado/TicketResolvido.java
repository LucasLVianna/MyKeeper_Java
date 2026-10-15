package br.pucpr.projeto_crud_java.model.estado;

public class TicketResolvido implements EstadoStatus {
    @Override public String getNome() { return "Resolvido"; }
    @Override public EstadoStatus proximo() { return new TicketFechado(); }
    @Override public EstadoStatus anterior() { return new TicketEmAndamento(); }
    @Override public String rotuloAvancar() { return "Fechar ticket"; }
    @Override public String rotuloVoltar() { return "Reabrir ticket"; }
    @Override public boolean permiteEdicao() { return false; }
    @Override public boolean permiteExclusao() { return false; }
}
