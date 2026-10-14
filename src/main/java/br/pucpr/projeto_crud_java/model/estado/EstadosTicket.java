package br.pucpr.projeto_crud_java.model.estado;

/** Reconstrói o objeto de estado a partir do nome gravado no arquivo. */
public final class EstadosTicket {
    private EstadosTicket() { }

    public static EstadoStatus inicial() { return new TicketAberto(); }

    public static EstadoStatus de(String nome) {
        if (nome != null) {
            switch (nome.trim().toLowerCase()) {
                case "em andamento": return new TicketEmAndamento();
                case "resolvido":    return new TicketResolvido();
                case "fechado":      return new TicketFechado();
                default: break;
            }
        }
        return inicial();   // dados antigos (texto livre) voltam ao estado inicial
    }
}
