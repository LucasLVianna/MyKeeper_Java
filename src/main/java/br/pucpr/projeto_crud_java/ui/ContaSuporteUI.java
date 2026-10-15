package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

public class ContaSuporteUI extends CrudUI<ContaSuporte> {

    private final TextField txtNome = campo("Nome");
    private final TextField txtCep = campo("CEP (8 números)");
    private final TextField txtEmail = campo("E-mail");
    private final TextField txtSenha = campo("Senha");
    private final TextField txtCpf = campo("CPF (11 números)");

    public ContaSuporteUI() { montar(); }

    @Override protected String titulo() { return "Conta de Suporte"; }
    @Override protected ArquivoContaSuporte repositorio() { return ArquivoContaSuporte.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<ContaSuporte> t) {
        t.getColumns().addAll(
                CrudUI.<ContaSuporte, Integer>coluna("ID", "id", 50),
                CrudUI.<ContaSuporte, String>coluna("Nome", "nome", 130),
                CrudUI.<ContaSuporte, String>coluna("CEP", "cep", 90),
                CrudUI.<ContaSuporte, String>coluna("E-mail", "email", 170),
                CrudUI.<ContaSuporte, String>coluna("Senha", "senha", 120),
                CrudUI.<ContaSuporte, String>coluna("CPF", "cpf", 110));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtNome, txtCep, txtEmail, txtSenha, txtCpf); }

    @Override
    protected ContaSuporte lerFormulario(int id) {
        exigirPreenchidos(txtNome, txtCep, txtEmail, txtSenha, txtCpf);
        if (!texto(txtCep).matches("\\d{8}")) throw new IllegalArgumentException("CEP inválido. Digite 8 números.");
        if (!(texto(txtEmail).contains("@") && texto(txtEmail).contains("."))) throw new IllegalArgumentException("E-mail inválido.");
        if (!texto(txtCpf).matches("\\d{11}")) throw new IllegalArgumentException("CPF inválido. Digite 11 números.");
        return new ContaSuporte(id, texto(txtNome), texto(txtCep), texto(txtEmail), texto(txtSenha), texto(txtCpf));
    }

    @Override
    protected void preencherFormulario(ContaSuporte c) {
        txtNome.setText(c.getNome());
        txtCep.setText(c.getCep());
        txtEmail.setText(c.getEmail());
        txtSenha.setText(c.getSenha());
        txtCpf.setText(c.getCpf());
    }

    @Override
    protected void limparFormulario() {
        txtNome.clear(); txtCep.clear(); txtEmail.clear(); txtSenha.clear(); txtCpf.clear();
    }

    @Override
    protected List<EstrategiaOrdenacao<ContaSuporte>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<ContaSuporte>("Nome", ContaSuporte::getNome, true),
                new OrdenacaoPorTexto<ContaSuporte>("Nome", ContaSuporte::getNome, false),
                new OrdenacaoPorTexto<ContaSuporte>("E-mail", ContaSuporte::getEmail, true));
    }
}
