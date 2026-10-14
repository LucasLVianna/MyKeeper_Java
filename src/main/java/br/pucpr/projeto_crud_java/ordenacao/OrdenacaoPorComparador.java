package br.pucpr.projeto_crud_java.ordenacao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Base comum das estratégias concretas: o algoritmo de ordenar é o mesmo,
 * a variação está somente no comparador que cada subclasse fornece.
 */
public abstract class OrdenacaoPorComparador<T> implements EstrategiaOrdenacao<T> {

    private final String nome;

    protected OrdenacaoPorComparador(String nome) {
        this.nome = nome;
    }

    /** Critério de comparação da estratégia concreta. */
    protected abstract Comparator<T> comparador();

    @Override
    public List<T> ordenar(List<T> itens) {
        List<T> copia = new ArrayList<>(itens);
        copia.sort(comparador());
        return copia;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;   // usado pelo ComboBox
    }
}
