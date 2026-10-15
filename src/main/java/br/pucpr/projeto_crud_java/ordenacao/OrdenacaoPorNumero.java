package br.pucpr.projeto_crud_java.ordenacao;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public class OrdenacaoPorNumero<T> extends OrdenacaoPorComparador<T> {

    private final ToDoubleFunction<T> campo;
    private final boolean crescente;

    public OrdenacaoPorNumero(String rotulo, ToDoubleFunction<T> campo, boolean crescente) {
        super(rotulo + (crescente ? " (menor primeiro)" : " (maior primeiro)"));
        this.campo = campo;
        this.crescente = crescente;
    }

    @Override
    protected Comparator<T> comparador() {
        Comparator<T> c = Comparator.comparingDouble(campo);
        return crescente ? c : c.reversed();
    }
}
