package br.pucpr.projeto_crud_java.model;

/** Repositório de Estoque (Template Method + regras do State herdadas de RepositorioComEstado). */
public class ArquivoEstoque extends RepositorioComEstado<Estoque> {

    public static final ArquivoEstoque INSTANCE = new ArquivoEstoque();

    private ArquivoEstoque() { }

    @Override
    protected String caminhoArquivo() {
        return "estoque.dat";
    }
}
