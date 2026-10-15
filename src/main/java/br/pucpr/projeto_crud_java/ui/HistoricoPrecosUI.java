package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.observer.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

// Registra os alertas de preço ao abrir a tela e remove ao fechar
public class HistoricoPrecosUI extends CrudUI<HistoricoPreco> {

    private static final double LIMITE_AUMENTO_PERCENTUAL = 10.0;

    private final TextField txtIdProduto = campo("ID do Produto");
    private final TextField txtPreco = campo("Preço");
    private final DatePicker txtData = new DatePicker();

    private final ObservadorPreco alertaAumento =
            new AlertaAumentoPreco(LIMITE_AUMENTO_PERCENTUAL, this::exibirAlerta);
    private final ObservadorPreco alertaMenorPreco = new AlertaMenorPreco(this::exibirAlerta);

    public HistoricoPrecosUI() {
        txtData.setPromptText("Data");
        montar();
        MonitorPrecos.INSTANCE.registrar(alertaAumento);
        MonitorPrecos.INSTANCE.registrar(alertaMenorPreco);
        setOnHidden(e -> {
            MonitorPrecos.INSTANCE.remover(alertaAumento);
            MonitorPrecos.INSTANCE.remover(alertaMenorPreco);
        });
    }

    private void exibirAlerta(String texto) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION, texto, ButtonType.OK);
            a.setTitle("Alerta de preço");
            a.setHeaderText(null);
            a.initOwner(this);
            a.show();
        });
    }

    @Override protected String titulo() { return "Histórico de Preços"; }
    @Override protected ArquivoHistoricoPreco repositorio() { return ArquivoHistoricoPreco.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<HistoricoPreco> t) {
        t.getColumns().addAll(
                CrudUI.<HistoricoPreco, Integer>coluna("ID", "id", 50),
                CrudUI.<HistoricoPreco, Integer>coluna("ID Produto", "idProduto", 90),
                CrudUI.<HistoricoPreco, Double>coluna("Preço", "preco", 100),
                CrudUI.<HistoricoPreco, String>coluna("Data", "data", 110));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtIdProduto, txtPreco, txtData); }

    @Override
    protected HistoricoPreco lerFormulario(int id) {
        exigirPreenchidos(txtIdProduto, txtPreco);
        LocalDate data = txtData.getValue();
        if (data == null) throw new IllegalArgumentException("Preencha todos os campos.");
        try {
            return new HistoricoPreco(id, Integer.parseInt(texto(txtIdProduto)),
                    Double.parseDouble(texto(txtPreco).replace(",", ".")), data);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID do Produto e Preço devem ser numéricos.");
        }
    }

    @Override
    protected void preencherFormulario(HistoricoPreco h) {
        txtIdProduto.setText(String.valueOf(h.getIdProduto()));
        txtPreco.setText(String.valueOf(h.getPreco()));
        txtData.setValue(h.getData());
    }

    @Override protected void limparFormulario() { txtIdProduto.clear(); txtPreco.clear(); txtData.setValue(null); }

    @Override
    protected List<EstrategiaOrdenacao<HistoricoPreco>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorData<HistoricoPreco>("Data", HistoricoPreco::getData, false),
                new OrdenacaoPorNumero<HistoricoPreco>("Preço", HistoricoPreco::getPreco, true),
                new OrdenacaoPorNumero<HistoricoPreco>("Preço", HistoricoPreco::getPreco, false));
    }
}
