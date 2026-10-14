package br.pucpr.projeto_crud_java.model;

import br.pucpr.projeto_crud_java.model.estado.ComEstado;

/**
 * Especialização do Template Method para entidades que usam o padrão State.
 * Sobrescreve os ganchos de validação para que as regras de cada estado
 * (editar, excluir, transições legais) sejam garantidas na camada de dados,
 * e não apenas na interface.
 */
public abstract class RepositorioComEstado<T extends ComEstado> extends RepositorioArquivo<T> {

    @Override
    protected void validarAtualizacao(T atual, T novo) {
        String nomeAtual = atual.getEstado().getNome();
        String nomeNovo = novo.getStatus();
        if (nomeAtual.equalsIgnoreCase(nomeNovo)) {
            // mesmo estado: é uma edição de dados
            if (!atual.getEstado().permiteEdicao()) {
                throw new IllegalStateException("Registro no estado \"" + nomeAtual + "\" não pode ser editado.");
            }
            return;
        }
        // estado diferente: só são aceitas as transições previstas pelo próprio estado
        boolean avancou = atual.getEstado().permiteAvancar()
                && atual.getEstado().proximo().getNome().equalsIgnoreCase(nomeNovo);
        boolean voltou = atual.getEstado().permiteVoltar()
                && atual.getEstado().anterior().getNome().equalsIgnoreCase(nomeNovo);
        if (!avancou && !voltou) {
            throw new IllegalStateException("Transição inválida de \"" + nomeAtual + "\" para \"" + nomeNovo + "\".");
        }
    }

    @Override
    protected void validarRemocao(T atual) {
        if (!atual.getEstado().permiteExclusao()) {
            throw new IllegalStateException("Registro no estado \"" + atual.getEstado().getNome() + "\" não pode ser excluído.");
        }
    }
}
