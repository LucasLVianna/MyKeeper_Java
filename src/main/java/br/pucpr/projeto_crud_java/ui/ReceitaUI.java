package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

public class ReceitaUI extends CrudUI<Receita> {

    private final TextField txtNome = campo("Nome");
    private final TextField txtIngredientes = campo("Ingredientes");
    private final TextField txtModoPreparo = campo("Modo de preparo");
    private final TextField txtTempoPreparo = campo("Tempo (min)");

    public ReceitaUI() { montar(); }

    @Override protected String titulo() { return "Receitas"; }
    @Override protected ArquivoReceita repositorio() { return ArquivoReceita.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<Receita> t) {
        t.getColumns().addAll(
                CrudUI.<Receita, Integer>coluna("ID", "id", 50),
                CrudUI.<Receita, String>coluna("Nome", "nome", 140),
                CrudUI.<Receita, String>coluna("Ingredientes", "ingredientes", 180),
                CrudUI.<Receita, String>coluna("Modo de Preparo", "modoPreparo", 180),
                CrudUI.<Receita, Integer>coluna("Tempo (min)", "tempoPreparo", 90));
    }

    @Override
    protected List<Node> camposFormulario() { return List.of(txtNome, txtIngredientes, txtModoPreparo, txtTempoPreparo); }

    @Override
    protected Receita lerFormulario(int id) {
        exigirPreenchidos(txtNome, txtIngredientes, txtModoPreparo, txtTempoPreparo);
        if (!texto(txtTempoPreparo).matches("\\d+"))
            throw new IllegalArgumentException("Tempo de preparo inválido. Use apenas números.");
        return new Receita(id, texto(txtNome), texto(txtIngredientes), texto(txtModoPreparo),
                Integer.parseInt(texto(txtTempoPreparo)));
    }

    @Override
    protected void preencherFormulario(Receita r) {
        txtNome.setText(r.getNome());
        txtIngredientes.setText(r.getIngredientes());
        txtModoPreparo.setText(r.getModoPreparo());
        txtTempoPreparo.setText(String.valueOf(r.getTempoPreparo()));
    }

    @Override
    protected void limparFormulario() {
        txtNome.clear(); txtIngredientes.clear(); txtModoPreparo.clear(); txtTempoPreparo.clear();
    }

    @Override
    protected List<EstrategiaOrdenacao<Receita>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<Receita>("Nome", Receita::getNome, true),
                new OrdenacaoPorNumero<Receita>("Tempo de preparo", Receita::getTempoPreparo, true),
                new OrdenacaoPorNumero<Receita>("Tempo de preparo", Receita::getTempoPreparo, false));
    }
}
