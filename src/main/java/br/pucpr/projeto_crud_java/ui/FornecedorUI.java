package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

/** Tela CRUD de Fornecedor (com validação de contato, CEP e PIX/CPF). */
public class FornecedorUI extends CrudUI<Fornecedor> {

    private final TextField txtNome = campo("Nome");
    private final TextField txtContato = campo("Contato (XX) XXXXX-XXXX");
    private final TextField txtCep = campo("CEP");
    private final TextField txtPix = campo("PIX (CPF)");

    public FornecedorUI() { montar(); }

    @Override protected String titulo() { return "Fornecedores"; }
    @Override protected ArquivoFornecedor repositorio() { return ArquivoFornecedor.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<Fornecedor> t) {
        t.getColumns().addAll(
                CrudUI.<Fornecedor, Integer>coluna("ID", "id", 50),
                CrudUI.<Fornecedor, String>coluna("Nome", "nome", 150),
                CrudUI.<Fornecedor, String>coluna("Contato", "contato", 140),
                CrudUI.<Fornecedor, String>coluna("CEP", "cep", 100),
                CrudUI.<Fornecedor, String>coluna("PIX", "pix", 130));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtNome, txtContato, txtCep, txtPix); }

    @Override
    protected Fornecedor lerFormulario(int id) {
        exigirPreenchidos(txtNome, txtContato, txtCep, txtPix);
        if (!texto(txtContato).matches("\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}"))
            throw new IllegalArgumentException("Contato inválido. Use (XX) XXXXX-XXXX.");
        if (!texto(txtCep).matches("\\d{5}-?\\d{3}"))
            throw new IllegalArgumentException("CEP inválido. Use XXXXX-XXX.");
        if (!texto(txtPix).matches("\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}"))
            throw new IllegalArgumentException("PIX/CPF inválido. Use XXX.XXX.XXX-XX.");
        return new Fornecedor(id, texto(txtNome), texto(txtContato), texto(txtCep), texto(txtPix));
    }

    @Override
    protected void preencherFormulario(Fornecedor f) {
        txtNome.setText(f.getNome());
        txtContato.setText(f.getContato());
        txtCep.setText(f.getCep());
        txtPix.setText(f.getPix());
    }

    @Override protected void limparFormulario() { txtNome.clear(); txtContato.clear(); txtCep.clear(); txtPix.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<Fornecedor>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<Fornecedor>("Nome", Fornecedor::getNome, true),
                new OrdenacaoPorTexto<Fornecedor>("Nome", Fornecedor::getNome, false));
    }
}
