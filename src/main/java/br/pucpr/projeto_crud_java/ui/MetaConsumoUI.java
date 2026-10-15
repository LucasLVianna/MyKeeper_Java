package br.pucpr.projeto_crud_java.ui;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.ordenacao.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import java.util.List;

public class MetaConsumoUI extends CrudUI<MetaConsumo> {

    private final TextField txtCategoria = campo("Categoria");
    private final TextField txtLimiteMensal = campo("Limite mensal");
    private final TextField txtUnidadeMedida = campo("Unidade de medida");

    public MetaConsumoUI() { montar(); }

    @Override protected String titulo() { return "Meta de Consumo"; }
    @Override protected ArquivoMetaConsumo repositorio() { return ArquivoMetaConsumo.INSTANCE; }

    @Override
    protected void configurarColunas(TableView<MetaConsumo> t) {
        t.getColumns().addAll(
                CrudUI.<MetaConsumo, Integer>coluna("ID", "id", 50),
                CrudUI.<MetaConsumo, String>coluna("Categoria", "categoria", 150),
                CrudUI.<MetaConsumo, Double>coluna("Limite mensal", "limiteMensal", 120),
                CrudUI.<MetaConsumo, String>coluna("Unidade", "unidadeMedida", 100));
    }

    @Override protected List<Node> camposFormulario() { return List.of(txtCategoria, txtLimiteMensal, txtUnidadeMedida); }

    @Override
    protected MetaConsumo lerFormulario(int id) {
        exigirPreenchidos(txtCategoria, txtLimiteMensal, txtUnidadeMedida);
        double limite;
        try {
            limite = Double.parseDouble(texto(txtLimiteMensal).replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite um limite mensal numérico válido.");
        }
        return new MetaConsumo(id, texto(txtCategoria), limite, texto(txtUnidadeMedida));
    }

    @Override
    protected void preencherFormulario(MetaConsumo m) {
        txtCategoria.setText(m.getCategoria());
        txtLimiteMensal.setText(String.valueOf(m.getLimiteMensal()));
        txtUnidadeMedida.setText(m.getUnidadeMedida());
    }

    @Override protected void limparFormulario() { txtCategoria.clear(); txtLimiteMensal.clear(); txtUnidadeMedida.clear(); }

    @Override
    protected List<EstrategiaOrdenacao<MetaConsumo>> estrategiasOrdenacao() {
        return List.of(
                new OrdenacaoPorId<>(true), new OrdenacaoPorId<>(false),
                new OrdenacaoPorTexto<MetaConsumo>("Categoria", MetaConsumo::getCategoria, true),
                new OrdenacaoPorNumero<MetaConsumo>("Limite mensal", MetaConsumo::getLimiteMensal, false),
                new OrdenacaoPorNumero<MetaConsumo>("Limite mensal", MetaConsumo::getLimiteMensal, true));
    }
}
