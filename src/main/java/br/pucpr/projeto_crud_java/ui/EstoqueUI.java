package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.model.estado.EstadoStatus;
import br.pucpr.projeto_crud_java.model.estado.EstadosEstoque;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

public class EstoqueUI extends CrudComEstadoUI<Estoque> {

    private final TextField txtNome = campo("Nome");
    private final DatePicker txtData = new DatePicker();

    public EstoqueUI() { txtData.setPromptText("Data de criação"); montar(); }

    @Override protected String titulo() { return "Estoque"; }
    @Override protected ArquivoEstoque repositorio() { return ArquivoEstoque.INSTANCE; }
    @Override protected EstadoStatus estadoInicial() { return EstadosEstoque.inicial(); }

    @Override
    protected void configurarColunas(TableView<Estoque> t) {
        t.getColumns().addAll(
                CrudUI.<Estoque, Integer>coluna("ID", "id", 50),
                CrudUI.<Estoque, String>coluna("Nome", "nome", 180),
                CrudUI.<Estoque, String>coluna("Data de criação", "data_criacao", 130),
                CrudUI.<Estoque, String>coluna("Status", "status", 120));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtNome, txtData, txtStatus); }

    @Override
    protected Estoque lerFormulario(int id) {
        exigirPreenchidos(txtNome);
        LocalDate data = txtData.getValue();
        if (data == null) throw new IllegalArgumentException("Preencha todos os campos.");
        return new Estoque(id, texto(txtNome), data, statusParaGravar());
    }

    @Override
    protected void preencherFormulario(Estoque e) {
        txtNome.setText(e.getNome());
        txtData.setValue(e.getData_criacao());
        txtStatus.setText(e.getStatus());
    }

    @Override protected void limparFormulario() { txtNome.clear(); txtData.setValue(null); txtStatus.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<Estoque>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<Estoque>("Nome", Estoque::getNome, true),
                new OrdenacaoPorData<Estoque>("Data de criação", Estoque::getData_criacao, false),
                new OrdenacaoPorTexto<Estoque>("Status", Estoque::getStatus, true));
    }
}
