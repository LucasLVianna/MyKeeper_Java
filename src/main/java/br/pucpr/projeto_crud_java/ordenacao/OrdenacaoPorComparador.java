package br.pucpr.projeto_crud_java.ordenacao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// As estratégias só mudam o Comparator, o resto é igual
public abstract class OrdenacaoPorComparador<T> implements EstrategiaOrdenacao<T> {

    private final String nome;

    protected OrdenacaoPorComparador(String nome) {
        this.nome = nome;
    }

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
