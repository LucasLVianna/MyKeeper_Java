package br.pucpr.projeto_crud_java.ordenacao;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Function;

/** ConcreteStrategy: ordena alfabeticamente por um campo de texto (ignora acentos e maiúsculas). */
public class OrdenacaoPorTexto<T> extends OrdenacaoPorComparador<T> {

    private final Function<T, String> campo;
    private final boolean crescente;

    public OrdenacaoPorTexto(String rotulo, Function<T, String> campo, boolean crescente) {
        super(rotulo + (crescente ? " (A-Z)" : " (Z-A)"));
        this.campo = campo;
        this.crescente = crescente;
    }

    @Override
    protected Comparator<T> comparador() {
        Collator collator = Collator.getInstance(Locale.forLanguageTag("pt-BR"));
        collator.setStrength(Collator.PRIMARY);
        Comparator<T> c = (a, b) -> collator.compare(valor(a), valor(b));
        return crescente ? c : c.reversed();
    }

    private String valor(T item) {
        String v = campo.apply(item);
        return v == null ? "" : v;
    }
}
