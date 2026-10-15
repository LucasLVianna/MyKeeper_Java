package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.estado.ComEstado;
import br.pucpr.projeto_crud_java.model.estado.EstadoStatus;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

// Tela base para entidades com status: os botões de transição e o que pode ser editado/excluído vêm do estado atual
public abstract class CrudComEstadoUI<T extends ComEstado> extends CrudUI<T> {

    // o status não é digitado, só muda pelos botões
    protected final TextField txtStatus = new TextField();
    private final Button btnAvancar = new Button("Avançar status");
    private final Button btnRetroceder = new Button("Voltar status");

    protected abstract EstadoStatus estadoInicial();

    protected String statusParaGravar() {
        T sel = getSelecionado();
        return sel != null ? sel.getStatus() : estadoInicial().getNome();
    }

    @Override
    protected boolean podeEditar(T item) { return item.getEstado().permiteEdicao(); }

    @Override
    protected boolean podeExcluir(T item) { return item.getEstado().permiteExclusao(); }

    @Override
    protected List<Button> botoesExtras() {
        txtStatus.setPromptText("Status");
        txtStatus.setEditable(false);
        btnAvancar.setDisable(true);
        btnRetroceder.setDisable(true);
        btnAvancar.setOnAction(e -> transicionar(true));
        btnRetroceder.setOnAction(e -> transicionar(false));
        return List.of(btnAvancar, btnRetroceder);
    }

    @Override
    protected void aoMudarSelecao(T item) {
        if (item == null) {
            btnAvancar.setText("Avançar status");
            btnRetroceder.setText("Voltar status");
            btnAvancar.setDisable(true);
            btnRetroceder.setDisable(true);
            return;
        }
        EstadoStatus estado = item.getEstado();
        btnAvancar.setText(estado.rotuloAvancar());
        btnRetroceder.setText(estado.rotuloVoltar());
        btnAvancar.setDisable(!estado.permiteAvancar());
        btnRetroceder.setDisable(!estado.permiteVoltar());
    }

    private void transicionar(boolean avancar) {
        T sel = getSelecionado();
        if (sel == null) return;
        try {
            if (avancar) sel.avancarStatus(); else sel.voltarStatus();
            repositorio().atualizar(sel);
            mensagem("Status alterado para: " + sel.getStatus());
        } catch (RuntimeException ex) {
            mensagem(ex.getMessage());
        }
        limpar();
        carregarTabela();
    }
}
