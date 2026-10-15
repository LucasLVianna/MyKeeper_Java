package br.pucpr.projeto_crud_java.model.estado;

// Converte o status salvo no arquivo no objeto de estado
public final class EstadosEstoque {
    private EstadosEstoque() { }

    public static EstadoStatus inicial() { return new EstoqueAtivo(); }

    public static EstadoStatus de(String nome) {
        if (nome != null) {
            switch (nome.trim().toLowerCase()) {
                case "inativo":   return new EstoqueInativo();
                case "arquivado": return new EstoqueArquivado();
                default: break;
            }
        }
        return inicial();
    }
}
