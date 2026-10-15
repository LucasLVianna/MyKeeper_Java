package br.pucpr.projeto_crud_java.model;

public class ArquivoMetaConsumo extends RepositorioArquivo<MetaConsumo> {

    public static final ArquivoMetaConsumo INSTANCE = new ArquivoMetaConsumo();

    private ArquivoMetaConsumo() { }

    @Override
    protected String caminhoArquivo() {
        return "metas_consumo.dat";
    }
}
