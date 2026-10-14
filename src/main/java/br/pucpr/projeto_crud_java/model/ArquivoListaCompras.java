package br.pucpr.projeto_crud_java.model;

/** Repositório de ListaCompras (Template Method + regras do State herdadas de RepositorioComEstado). */
public class ArquivoListaCompras extends RepositorioComEstado<ListaCompras> {

    public static final ArquivoListaCompras INSTANCE = new ArquivoListaCompras();

    private ArquivoListaCompras() { }

    @Override
    protected String caminhoArquivo() {
        return "listaCompras.dat";
    }
}
