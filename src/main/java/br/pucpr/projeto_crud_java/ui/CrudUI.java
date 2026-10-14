package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.Identificavel;
import br.pucpr.projeto_crud_java.model.RepositorioArquivo;
import br.pucpr.projeto_crud_java.ordenacao.EstrategiaOrdenacao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * PADRÃO: Template Method (GoF, comportamental) - AbstractClass das telas CRUD.
 *
 * Os fluxos adicionar / atualizar / deletar / limpar / carregar são definidos
 * aqui uma única vez (métodos finais). Cada tela concreta só informa o que
 * varia: título, colunas, campos do formulário, como ler/preencher o formulário
 * e qual repositório usar.
 *
 * PADRÃO: Strategy - esta classe é o Context: guarda a EstrategiaOrdenacao
 * escolhida no ComboBox e a aplica ao listar os registros.
 *
 * ATENÇÃO: a subclasse deve chamar {@link #montar()} como última instrução do
 * seu construtor (os campos da subclasse só existem depois do super()).
 */
public abstract class CrudUI<T extends Identificavel> extends Stage {

    // ---------- passos variáveis (abstratos) ----------
    protected abstract String titulo();
    protected abstract RepositorioArquivo<T> repositorio();
    protected abstract void configurarColunas(TableView<T> tabela);
    protected abstract List<Node> camposFormulario();
    /** Lê o formulário e devolve a entidade; lança IllegalArgumentException com a mensagem de erro de validação. */
    protected abstract T lerFormulario(int id);
    protected abstract void preencherFormulario(T item);
    protected abstract void limparFormulario();
    protected abstract List<EstrategiaOrdenacao<T>> estrategiasOrdenacao();

    // ---------- ganchos opcionais ----------
    protected boolean podeEditar(T item) { return true; }
    protected boolean podeExcluir(T item) { return true; }
    protected List<Button> botoesExtras() { return List.of(); }
    /** Chamado quando a seleção muda (item = null quando não há seleção). */
    protected void aoMudarSelecao(T item) { }
    protected String nomeEntidade() { return titulo(); }

    // ---------- estado da tela ----------
    protected final TableView<T> tabela = new TableView<>();
    protected final ObservableList<T> dados = FXCollections.observableArrayList();
    protected final Label lblMensagem = new Label();

    private final Button btnAdicionar = new Button("Adicionar");
    private final Button btnAtualizar = new Button("Atualizar");
    private final Button btnDeletar = new Button("Deletar");
    private final Button btnLimpar = new Button("Limpar");
    private final Button btnCancelar = new Button("Cancelar");
    private final Button btnVoltar = new Button("Voltar");
    private final ComboBox<EstrategiaOrdenacao<T>> cmbOrdenacao = new ComboBox<>();

    protected final TextField txtId = new TextField();

    protected T getSelecionado() {
        return tabela.getSelectionModel().getSelectedItem();
    }

    protected void mensagem(String texto) {
        lblMensagem.setText(texto);
    }

    protected static TextField campo(String prompt) {
        TextField t = new TextField();
        t.setPromptText(prompt);
        return t;
    }

    protected static String texto(TextField t) {
        return t.getText().trim();
    }

    /** Validação comum: todos os campos de texto informados precisam estar preenchidos. */
    protected static void exigirPreenchidos(TextField... campos) {
        for (TextField c : campos) {
            if (c.getText().trim().isEmpty()) throw new IllegalArgumentException("Preencha todos os campos.");
        }
    }

    protected static <S, V> TableColumn<S, V> coluna(String titulo, String propriedade, double largura) {
        TableColumn<S, V> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(propriedade));
        col.setPrefWidth(largura);
        return col;
    }

    // ---------- template: montagem da tela ----------
    protected final void montar() {
        setTitle(titulo());

        txtId.setPromptText("ID");
        txtId.setEditable(false);
        configurarColunas(tabela);
        tabela.setItems(dados);

        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);

        cmbOrdenacao.getItems().addAll(estrategiasOrdenacao());
        cmbOrdenacao.getSelectionModel().selectFirst();
        cmbOrdenacao.setOnAction(e -> carregarTabela());   // troca de Strategy em tempo de execução

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, ant, sel) -> aplicarSelecao(sel));

        btnAdicionar.setOnAction(e -> adicionar());
        btnAtualizar.setOnAction(e -> atualizar());
        btnDeletar.setOnAction(e -> deletar());
        btnLimpar.setOnAction(e -> { limpar(); mensagem(""); });
        btnCancelar.setOnAction(e -> { limpar(); mensagem(""); });
        btnVoltar.setOnAction(e -> close());

        FlowPane campos = new FlowPane(8, 8);
        campos.getChildren().add(txtId);
        campos.getChildren().addAll(camposFormulario());
        HBox botoes = new HBox(8, btnAdicionar, btnAtualizar, btnDeletar, btnLimpar, btnCancelar, btnVoltar);
        HBox extras = new HBox(8);
        extras.getChildren().addAll(botoesExtras());
        HBox ordem = new HBox(8, new Label("Ordenar por:"), cmbOrdenacao);

        VBox root = new VBox(8, campos, botoes, extras, ordem, lblMensagem, tabela);
        root.setStyle("-fx-padding: 12;");
        setScene(new Scene(root, 780, 520));

        carregarTabela();
    }

    // ---------- template: fluxos CRUD ----------
    private void aplicarSelecao(T sel) {
        if (sel != null) {
            txtId.setText(String.valueOf(sel.getId()));
            preencherFormulario(sel);
            btnAtualizar.setDisable(!podeEditar(sel));
            btnDeletar.setDisable(!podeExcluir(sel));
            btnAdicionar.setDisable(true);
            btnCancelar.setDisable(false);
        }
        aoMudarSelecao(sel);
    }

    private void adicionar() {
        try {
            T novo = lerFormulario(repositorio().gerarId());
            repositorio().adicionar(novo);
            mensagem(nomeEntidade() + ": registro adicionado.");
            limpar();
            carregarTabela();
        } catch (RuntimeException ex) {
            mensagem(ex.getMessage());
        }
    }

    private void atualizar() {
        T sel = getSelecionado();
        if (sel == null) { mensagem("Selecione um registro."); return; }
        try {
            T atualizado = lerFormulario(sel.getId());
            repositorio().atualizar(atualizado);
            mensagem(nomeEntidade() + ": registro atualizado.");
            limpar();
            carregarTabela();
        } catch (RuntimeException ex) {
            mensagem(ex.getMessage());
        }
    }

    private void deletar() {
        T sel = getSelecionado();
        if (sel == null) { mensagem("Selecione um registro."); return; }
        try {
            repositorio().deletar(sel.getId());
            mensagem(nomeEntidade() + ": registro deletado.");
            limpar();
            carregarTabela();
        } catch (RuntimeException ex) {
            mensagem(ex.getMessage());
        }
    }

    protected final void limpar() {
        txtId.clear();
        limparFormulario();
        tabela.getSelectionModel().clearSelection();
        btnAdicionar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
        btnCancelar.setDisable(true);
        aoMudarSelecao(null);
    }

    /** Recarrega a tabela aplicando a estratégia de ordenação escolhida. */
    protected final void carregarTabela() {
        ArrayList<T> lista = repositorio().lerLista();
        EstrategiaOrdenacao<T> estrategia = cmbOrdenacao.getValue();
        dados.setAll(estrategia == null ? lista : estrategia.ordenar(lista));
    }
}
