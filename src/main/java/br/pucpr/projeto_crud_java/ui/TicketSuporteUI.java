package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.model.estado.EstadoStatus;
import br.pucpr.projeto_crud_java.model.estado.EstadosTicket;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

public class TicketSuporteUI extends CrudComEstadoUI<TicketSuporte> {

    private final TextField txtTitulo = campo("Título");
    private final TextField txtPrioridade = campo("Prioridade");

    public TicketSuporteUI() { montar(); }

    @Override protected String titulo() { return "Tickets de Suporte"; }
    @Override protected ArquivoTicketSuporte repositorio() { return ArquivoTicketSuporte.INSTANCE; }
    @Override protected EstadoStatus estadoInicial() { return EstadosTicket.inicial(); }

    @Override
    protected void configurarColunas(TableView<TicketSuporte> t) {
        t.getColumns().addAll(
                CrudUI.<TicketSuporte, Integer>coluna("ID", "id", 50),
                CrudUI.<TicketSuporte, String>coluna("Título", "titulo", 220),
                CrudUI.<TicketSuporte, String>coluna("Prioridade", "prioridade", 100),
                CrudUI.<TicketSuporte, String>coluna("Status", "status", 120));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtTitulo, txtPrioridade, txtStatus); }

    @Override
    protected TicketSuporte lerFormulario(int id) {
        exigirPreenchidos(txtTitulo, txtPrioridade);
        return new TicketSuporte(id, texto(txtTitulo), texto(txtPrioridade), statusParaGravar());
    }

    @Override
    protected void preencherFormulario(TicketSuporte t) {
        txtTitulo.setText(t.getTitulo());
        txtPrioridade.setText(t.getPrioridade());
        txtStatus.setText(t.getStatus());
    }

    @Override protected void limparFormulario() { txtTitulo.clear(); txtPrioridade.clear(); txtStatus.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<TicketSuporte>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<TicketSuporte>("Título", TicketSuporte::getTitulo, true),
                new OrdenacaoPorTexto<TicketSuporte>("Prioridade", TicketSuporte::getPrioridade, true),
                new OrdenacaoPorTexto<TicketSuporte>("Status", TicketSuporte::getStatus, true));
    }
}
