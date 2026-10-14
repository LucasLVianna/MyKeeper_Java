package br.pucpr.projeto_crud_java.ordenacao;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Function;

/** ConcreteStrategy: ordena por um campo de data (datas nulas ficam por último). */
public class OrdenacaoPorData<T> extends OrdenacaoPorComparador<T> {

    private final Function<T, LocalDate> campo;
    private final boolean crescente;

    public OrdenacaoPorData(String rotulo, Function<T, LocalDate> campo, boolean crescente) {
        super(rotulo + (crescente ? " (mais antiga)" : " (mais recente)"));
        this.campo = campo;
        this.crescente = crescente;
    }

    @Override
    protected Comparator<T> comparador() {
        Comparator<T> c = Comparator.comparing(campo, Comparator.nullsLast(Comparator.naturalOrder()));
        return crescente ? c : c.reversed();
    }
}
