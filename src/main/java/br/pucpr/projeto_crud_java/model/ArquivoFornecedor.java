package br.pucpr.projeto_crud_java.model;

public class ArquivoFornecedor extends RepositorioArquivo<Fornecedor> {

    public static final ArquivoFornecedor INSTANCE = new ArquivoFornecedor();

    private ArquivoFornecedor() { }

    @Override
    protected String caminhoArquivo() {
        return "fornecedor.dat";
    }
}
