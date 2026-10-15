package br.pucpr.projeto_crud_java.model;

// as regras de status ficam em RepositorioComEstado
public class ArquivoListaCompras extends RepositorioComEstado<ListaCompras> {

    public static final ArquivoListaCompras INSTANCE = new ArquivoListaCompras();

    private ArquivoListaCompras() { }

    @Override
    protected String caminhoArquivo() {
        return "listaCompras.dat";
    }
}
