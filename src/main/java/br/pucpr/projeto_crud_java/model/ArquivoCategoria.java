package br.pucpr.projeto_crud_java.model;

/** Repositório de Categoria (Template Method: só define o caminho do arquivo). */
public class ArquivoCategoria extends RepositorioArquivo<Categoria> {

    public static final ArquivoCategoria INSTANCE = new ArquivoCategoria();

    private ArquivoCategoria() { }

    @Override
    protected String caminhoArquivo() {
        return "categoria.dat";
    }
}
