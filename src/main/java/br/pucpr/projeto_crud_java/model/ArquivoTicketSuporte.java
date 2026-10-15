package br.pucpr.projeto_crud_java.model;

// as regras de status ficam em RepositorioComEstado
public class ArquivoTicketSuporte extends RepositorioComEstado<TicketSuporte> {

    public static final ArquivoTicketSuporte INSTANCE = new ArquivoTicketSuporte();

    private ArquivoTicketSuporte() { }

    @Override
    protected String caminhoArquivo() {
        return "tickets_suporte.dat";
    }
}
