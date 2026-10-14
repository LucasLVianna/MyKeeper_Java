package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.model.estado.EstadoStatus;
import br.pucpr.projeto_crud_java.model.estado.EstadosLista;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;

/** Tela CRUD de Lista de Compras; o fluxo de status vem do padrão State. */
public class ListaComprasUI extends CrudComEstadoUI<ListaCompras> {

    private final TextField txtNome = campo("Nome");
    private final DatePicker txtData = new DatePicker();

    public ListaComprasUI() { txtData.setPromptText("Data de criação"); montar(); }

    @Override protected String titulo() { return "Lista de Compras"; }
    @Override protected ArquivoListaCompras repositorio() { return ArquivoListaCompras.INSTANCE; }
    @Override protected EstadoStatus estadoInicial() { return EstadosLista.inicial(); }

    @Override
    protected void configurarColunas(TableView<ListaCompras> t) {
        t.getColumns().addAll(
                CrudUI.<ListaCompras, Integer>coluna("ID", "id", 50),
                CrudUI.<ListaCompras, String>coluna("Nome", "nome", 180),
                CrudUI.<ListaCompras, String>coluna("Data de criação", "data_criacao", 130),
                CrudUI.<ListaCompras, String>coluna("Status", "status", 120));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtNome, txtData, txtStatus); }

    @Override
    protected ListaCompras lerFormulario(int id) {
        exigirPreenchidos(txtNome);
        LocalDate data = txtData.getValue();
        if (data == null) throw new IllegalArgumentException("Preencha todos os campos.");
        return new ListaCompras(id, texto(txtNome), data, statusParaGravar());
    }

    @Override
    protected void preencherFormulario(ListaCompras l) {
        txtNome.setText(l.getNome());
        txtData.setValue(l.getData_criacao());
        txtStatus.setText(l.getStatus());
    }

    @Override protected void limparFormulario() { txtNome.clear(); txtData.setValue(null); txtStatus.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<ListaCompras>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<ListaCompras>("Nome", ListaCompras::getNome, true),
                new OrdenacaoPorData<ListaCompras>("Data de criação", ListaCompras::getData_criacao, false),
                new OrdenacaoPorData<ListaCompras>("Data de criação", ListaCompras::getData_criacao, true));
    }
}
