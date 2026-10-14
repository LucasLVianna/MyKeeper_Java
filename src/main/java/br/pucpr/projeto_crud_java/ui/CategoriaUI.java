package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

/** Tela CRUD de Categoria. */
public class CategoriaUI extends CrudUI<Categoria> {

    private final TextField txtNome = campo("Nome");
    private final TextField txtDescricao = campo("Descrição");
    private final TextField txtSetor = campo("Setor");

    public CategoriaUI() { montar(); }

    @Override protected String titulo() { return "Categorias"; }
    @Override protected ArquivoCategoria repositorio() { return ArquivoCategoria.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<Categoria> t) {
        t.getColumns().addAll(
                CrudUI.<Categoria, Integer>coluna("ID", "id", 50),
                CrudUI.<Categoria, String>coluna("Nome", "nome", 150),
                CrudUI.<Categoria, String>coluna("Descrição", "descricao", 220),
                CrudUI.<Categoria, String>coluna("Setor", "setor", 120));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtNome, txtDescricao, txtSetor); }

    @Override
    protected Categoria lerFormulario(int id) {
        exigirPreenchidos(txtNome, txtDescricao, txtSetor);
        return new Categoria(id, texto(txtNome), texto(txtDescricao), texto(txtSetor));
    }

    @Override
    protected void preencherFormulario(Categoria c) {
        txtNome.setText(c.getNome());
        txtDescricao.setText(c.getDescricao());
        txtSetor.setText(c.getSetor());
    }

    @Override protected void limparFormulario() { txtNome.clear(); txtDescricao.clear(); txtSetor.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<Categoria>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<Categoria>("Nome", Categoria::getNome, true),
                new OrdenacaoPorTexto<Categoria>("Nome", Categoria::getNome, false),
                new OrdenacaoPorTexto<Categoria>("Setor", Categoria::getSetor, true));
    }
}
