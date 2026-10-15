package br.pucpr.projeto_crud_java.model;

public class ArquivoCategoria extends RepositorioArquivo<Categoria> {

    public static final ArquivoCategoria INSTANCE = new ArquivoCategoria();

    private ArquivoCategoria() { }

    @Override
    protected String caminhoArquivo() {
        return "categoria.dat";
    }
}
