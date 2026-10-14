package br.pucpr.projeto_crud_java.model;

/** Repositório de MetaConsumo (Template Method: só define o caminho do arquivo). */
public class ArquivoMetaConsumo extends RepositorioArquivo<MetaConsumo> {

    public static final ArquivoMetaConsumo INSTANCE = new ArquivoMetaConsumo();

    private ArquivoMetaConsumo() { }

    @Override
    protected String caminhoArquivo() {
        return "metas_consumo.dat";
    }
}
