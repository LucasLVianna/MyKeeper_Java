package br.pucpr.projeto_crud_java.model.estado;

// Converte o status salvo no arquivo no objeto de estado
public final class EstadosLista {
    private EstadosLista() { }

    public static EstadoStatus inicial() { return new ListaEmElaboracao(); }

    public static EstadoStatus de(String nome) {
        if (nome != null) {
            switch (nome.trim().toLowerCase()) {
                case "finalizada": return new ListaFinalizada();
                case "comprada":   return new ListaComprada();
                default: break;
            }
        }
        return inicial();
    }
}
