package br.pucpr.projeto_crud_java.model;

// as regras de status ficam em RepositorioComEstado
public class ArquivoEstoque extends RepositorioComEstado<Estoque> {

    public static final ArquivoEstoque INSTANCE = new ArquivoEstoque();

    private ArquivoEstoque() { }

    @Override
    protected String caminhoArquivo() {
        return "estoque.dat";
    }
}
