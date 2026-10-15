package br.pucpr.projeto_crud_java.model;

public class ArquivoContaSuporte extends RepositorioArquivo<ContaSuporte> {

    public static final ArquivoContaSuporte INSTANCE = new ArquivoContaSuporte();

    private ArquivoContaSuporte() { }

    @Override
    protected String caminhoArquivo() {
        return "contaSuporte.dat";
    }
}
