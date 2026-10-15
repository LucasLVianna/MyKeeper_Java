package br.pucpr.projeto_crud_java.model;

public class ArquivoReceita extends RepositorioArquivo<Receita> {

    public static final ArquivoReceita INSTANCE = new ArquivoReceita();

    private ArquivoReceita() { }

    @Override
    protected String caminhoArquivo() {
        return "receita.dat";
    }
}
