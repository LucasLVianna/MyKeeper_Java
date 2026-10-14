package br.pucpr.projeto_crud_java.model;

/** Repositório de Produto (Template Method: só define o caminho do arquivo). */
public class ArquivoProduto extends RepositorioArquivo<Produto> {

    public static final ArquivoProduto INSTANCE = new ArquivoProduto();

    private ArquivoProduto() { }

    @Override
    protected String caminhoArquivo() {
        return "produtos.dat";
    }
}
