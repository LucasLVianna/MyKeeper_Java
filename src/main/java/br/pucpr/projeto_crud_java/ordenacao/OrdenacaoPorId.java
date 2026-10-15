package br.pucpr.projeto_crud_java.ordenacao;

import br.pucpr.projeto_crud_java.model.Identificavel;
import java.util.Comparator;

public class OrdenacaoPorId<T extends Identificavel> extends OrdenacaoPorComparador<T> {

    private final boolean crescente;

    public OrdenacaoPorId(boolean crescente) {
        super(crescente ? "ID (mais antigos)" : "ID (mais recentes)");
        this.crescente = crescente;
    }

    @Override
    protected Comparator<T> comparador() {
        Comparator<T> c = Comparator.comparingInt(Identificavel::getId);
        return crescente ? c : c.reversed();
    }
}
