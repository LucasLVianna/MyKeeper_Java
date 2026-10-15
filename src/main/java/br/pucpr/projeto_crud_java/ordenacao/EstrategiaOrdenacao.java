package br.pucpr.projeto_crud_java.ordenacao;

import java.util.List;

// Strategy: cada critério de ordenação é uma classe. A CrudUI troca a estratégia
// quando o usuário escolhe outro critério no ComboBox.
public interface EstrategiaOrdenacao<T> {

    String getNome();

    // retorna uma lista nova, sem mexer na original
    List<T> ordenar(List<T> itens);
}
