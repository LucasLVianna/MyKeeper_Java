package br.pucpr.projeto_crud_java.model;

import br.pucpr.projeto_crud_java.model.estado.ComEstado;

// Repositório das entidades com status: bloqueia edição, exclusão e transições que o estado atual não permite
public abstract class RepositorioComEstado<T extends ComEstado> extends RepositorioArquivo<T> {

    @Override
    protected void validarAtualizacao(T atual, T novo) {
        String nomeAtual = atual.getEstado().getNome();
        String nomeNovo = novo.getStatus();
        if (nomeAtual.equalsIgnoreCase(nomeNovo)) {
            // status igual: é só edição dos dados
            if (!atual.getEstado().permiteEdicao()) {
                throw new IllegalStateException("Registro no estado \"" + nomeAtual + "\" não pode ser editado.");
            }
            return;
        }
        // status mudou: só aceita ir para o próximo ou voltar para o anterior
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
