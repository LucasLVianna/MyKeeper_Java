package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

public class ProdutoUI extends CrudUI<Produto> {

    private final TextField txtNome = campo("Nome");
    private final TextField txtCategoria = campo("Categoria");
    private final TextField txtUnidade = campo("Unidade de medida");

    public ProdutoUI() { montar(); }

    @Override protected String titulo() { return "Produtos"; }
    @Override protected ArquivoProduto repositorio() { return ArquivoProduto.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<Produto> t) {
        t.getColumns().addAll(
                CrudUI.<Produto, Integer>coluna("ID", "id", 50),
                CrudUI.<Produto, String>coluna("Nome", "nome", 150),
                CrudUI.<Produto, String>coluna("Categoria", "categoria", 150),
                CrudUI.<Produto, String>coluna("Unidade", "unidade_medida", 100));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtNome, txtCategoria, txtUnidade); }

    @Override
    protected Produto lerFormulario(int id) {
        exigirPreenchidos(txtNome, txtCategoria, txtUnidade);
        return new Produto(id, texto(txtNome), texto(txtCategoria), texto(txtUnidade));
    }

    @Override
    protected void preencherFormulario(Produto p) {
        txtNome.setText(p.getNome());
        txtCategoria.setText(p.getCategoria());
        txtUnidade.setText(p.getUnidade_medida());
    }

    @Override protected void limparFormulario() { txtNome.clear(); txtCategoria.clear(); txtUnidade.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<Produto>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<Produto>("Nome", Produto::getNome, true),
                new OrdenacaoPorTexto<Produto>("Nome", Produto::getNome, false),
                new OrdenacaoPorTexto<Produto>("Categoria", Produto::getCategoria, true));
    }
}
