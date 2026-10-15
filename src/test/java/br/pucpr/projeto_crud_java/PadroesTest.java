package br.pucpr.projeto_crud_java;

import br.pucpr.projeto_crud_java.model.*;
import br.pucpr.projeto_crud_java.model.estado.*;
import br.pucpr.projeto_crud_java.observer.*;
import br.pucpr.projeto_crud_java.ordenacao.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PadroesTest {

    @TempDir
    File pasta;

    @BeforeEach
    void isolarArquivos() {
        RepositorioArquivo.setDiretorioBase(pasta);
    }

    // Template Method

    @Test
    void templateMethod_crudCompletoNoRepositorioGenerico() {
        ArquivoProduto repo = ArquivoProduto.INSTANCE;
        repo.adicionar(new Produto(repo.gerarId(), "Arroz", "Grãos", "kg"));
        repo.adicionar(new Produto(repo.gerarId(), "Leite", "Laticínios", "L"));
        assertEquals(2, repo.lerLista().size());
        assertEquals(3, repo.gerarId());

        repo.atualizar(new Produto(1, "Arroz Integral", "Grãos", "kg"));
        assertEquals("Arroz Integral", repo.buscarPorId(1).getNome());

        repo.deletar(2);
        assertNull(repo.buscarPorId(2));
        assertEquals(1, repo.lerLista().size());
    }

    @Test
    void templateMethod_validacaoAoAdicionar() {
        assertThrows(IllegalArgumentException.class,
                () -> ArquivoHistoricoPreco.INSTANCE.adicionar(new HistoricoPreco(1, 1, 0, LocalDate.now())));
        assertEquals(0, ArquivoHistoricoPreco.INSTANCE.lerLista().size());
    }

    @Test
    void templateMethod_validacaoAoAtualizar() {
        ArquivoHistoricoPreco repo = ArquivoHistoricoPreco.INSTANCE;
        repo.adicionar(new HistoricoPreco(1, 1, 10.0, LocalDate.now()));
        assertThrows(IllegalArgumentException.class,
                () -> repo.atualizar(new HistoricoPreco(1, 1, -5.0, LocalDate.now())));
        assertEquals(10.0, repo.buscarPorId(1).getPreco());
    }

    // State

    @Test
    void state_fluxoCompletoDoTicket() {
        TicketSuporte t = new TicketSuporte(1, "Erro no login", "Alta", EstadosTicket.inicial().getNome());
        assertEquals("Aberto", t.getStatus());
        t.avancarStatus();
        assertEquals("Em andamento", t.getStatus());
        t.avancarStatus();
        assertEquals("Resolvido", t.getStatus());
        t.voltarStatus();                       // reabertura
        assertEquals("Em andamento", t.getStatus());
        t.avancarStatus();
        t.avancarStatus();
        assertEquals("Fechado", t.getStatus());
        t.avancarStatus();                      // estado final: continua Fechado
        assertEquals("Fechado", t.getStatus());
    }

    @Test
    void state_regrasDeEdicaoEExclusaoVemDoEstado() {
        TicketSuporte t = new TicketSuporte(1, "x", "Baixa", "Aberto");
        assertTrue(t.getEstado().permiteEdicao());
        assertTrue(t.getEstado().permiteExclusao());
        t.avancarStatus();                      // Em andamento
        assertTrue(t.getEstado().permiteEdicao());
        assertFalse(t.getEstado().permiteExclusao());
        t.avancarStatus();                      // Resolvido
        assertFalse(t.getEstado().permiteEdicao());
    }

    @Test
    void state_repositorioImpedeEdicaoEExclusaoForaDoEstadoPermitido() {
        ArquivoTicketSuporte repo = ArquivoTicketSuporte.INSTANCE;
        repo.adicionar(new TicketSuporte(1, "x", "Alta", "Aberto"));

        TicketSuporte t = repo.buscarPorId(1);
        t.avancarStatus();                      // Em andamento
        repo.atualizar(t);
        assertThrows(IllegalStateException.class, () -> repo.deletar(1));

        // transição inválida (pular de Em andamento direto para Fechado) é recusada
        assertThrows(IllegalStateException.class,
                () -> repo.atualizar(new TicketSuporte(1, "x", "Alta", "Fechado")));

        TicketSuporte r = repo.buscarPorId(1);
        r.avancarStatus();                      // Resolvido
        repo.atualizar(r);
        assertThrows(IllegalStateException.class,
                () -> repo.atualizar(new TicketSuporte(1, "título novo", "Alta", "Resolvido")));
    }

    @Test
    void state_estadoSobreviveASerializacao() {
        ArquivoListaCompras repo = ArquivoListaCompras.INSTANCE;
        repo.adicionar(new ListaCompras(1, "Mês", LocalDate.of(2026, 10, 1), "Em elaboração"));
        ListaCompras l = repo.buscarPorId(1);
        l.avancarStatus();
        repo.atualizar(l);
        assertEquals("Finalizada", repo.buscarPorId(1).getEstado().getNome());
        assertFalse(repo.buscarPorId(1).getEstado().permiteEdicao());
    }

    @Test
    void state_estoqueSoPodeSerExcluidoQuandoInativo() {
        ArquivoEstoque repo = ArquivoEstoque.INSTANCE;
        repo.adicionar(new Estoque(1, "Despensa", LocalDate.now(), "Ativo"));
        assertThrows(IllegalStateException.class, () -> repo.deletar(1));
        Estoque e = repo.buscarPorId(1);
        e.avancarStatus();                      // Inativo
        repo.atualizar(e);
        repo.deletar(1);
        assertNull(repo.buscarPorId(1));
    }

    @Test
    void state_statusDesconhecidoDeDadosAntigosVoltaAoEstadoInicial() {
        assertEquals("Aberto", new TicketSuporte(1, "x", "Alta", "qualquer texto").getEstado().getNome());
    }

    // Strategy

    @Test
    void strategy_trocaDeCriterioMudaAOrdem() {
        List<Produto> itens = List.of(
                new Produto(1, "banana", "Frutas", "kg"),
                new Produto(2, "Ábaco", "Outros", "un"),
                new Produto(3, "cenoura", "Legumes", "kg"));

        EstrategiaOrdenacao<Produto> az = new OrdenacaoPorTexto<>("Nome", Produto::getNome, true);
        EstrategiaOrdenacao<Produto> za = new OrdenacaoPorTexto<>("Nome", Produto::getNome, false);
        EstrategiaOrdenacao<Produto> idDesc = new OrdenacaoPorId<>(false);

        assertEquals(List.of(2, 1, 3), ids(az.ordenar(itens)));
        assertEquals(List.of(3, 1, 2), ids(za.ordenar(itens)));
        assertEquals(List.of(3, 2, 1), ids(idDesc.ordenar(itens)));
        assertEquals(List.of(1, 2, 3), ids(itens));   // a original não muda
    }

    @Test
    void strategy_numeroEData() {
        List<HistoricoPreco> itens = List.of(
                new HistoricoPreco(1, 1, 9.5, LocalDate.of(2026, 3, 1)),
                new HistoricoPreco(2, 1, 4.0, LocalDate.of(2026, 5, 1)),
                new HistoricoPreco(3, 1, 7.0, LocalDate.of(2026, 1, 1)));
        assertEquals(List.of(2, 3, 1),
                ids(new OrdenacaoPorNumero<HistoricoPreco>("Preço", HistoricoPreco::getPreco, true).ordenar(itens)));
        assertEquals(List.of(2, 1, 3),
                ids(new OrdenacaoPorData<HistoricoPreco>("Data", HistoricoPreco::getData, false).ordenar(itens)));
    }

    private static List<Integer> ids(List<? extends Identificavel> l) {
        List<Integer> r = new ArrayList<>();
        for (Identificavel i : l) r.add(i.getId());
        return r;
    }

    // Observer

    @Test
    void observer_alertaDeAumentoENotificadoPeloRepositorio() {
        List<String> alertas = new ArrayList<>();
        ObservadorPreco o = new AlertaAumentoPreco(10.0, alertas::add);
        MonitorPrecos.INSTANCE.registrar(o);
        try {
            ArquivoHistoricoPreco repo = ArquivoHistoricoPreco.INSTANCE;
            repo.adicionar(new HistoricoPreco(1, 7, 10.00, LocalDate.of(2026, 1, 1)));
            assertTrue(alertas.isEmpty());                                  // primeiro preço: sem alerta
            repo.adicionar(new HistoricoPreco(2, 7, 10.50, LocalDate.of(2026, 2, 1)));
            assertTrue(alertas.isEmpty());                                  // +5%: abaixo do limite
            repo.adicionar(new HistoricoPreco(3, 7, 12.60, LocalDate.of(2026, 3, 1)));
            assertEquals(1, alertas.size());                                // +20%: alerta
            assertTrue(alertas.get(0).contains("20.0%"));
        } finally {
            MonitorPrecos.INSTANCE.remover(o);
        }
    }

    @Test
    void observer_menorPrecoEPorProduto() {
        List<String> alertas = new ArrayList<>();
        ObservadorPreco o = new AlertaMenorPreco(alertas::add);
        MonitorPrecos.INSTANCE.registrar(o);
        try {
            ArquivoHistoricoPreco repo = ArquivoHistoricoPreco.INSTANCE;
            repo.adicionar(new HistoricoPreco(1, 1, 10.0, LocalDate.of(2026, 1, 1)));
            repo.adicionar(new HistoricoPreco(2, 2, 3.0, LocalDate.of(2026, 1, 2)));   // outro produto
            repo.adicionar(new HistoricoPreco(3, 1, 8.0, LocalDate.of(2026, 2, 1)));   // novo mínimo do produto 1
            repo.adicionar(new HistoricoPreco(4, 1, 9.0, LocalDate.of(2026, 3, 1)));   // não é mínimo
            assertEquals(1, alertas.size());
            assertTrue(alertas.get(0).contains("produto 1"));
        } finally {
            MonitorPrecos.INSTANCE.remover(o);
        }
    }

    @Test
    void observer_removerObservadorInterrompeNotificacoes_eFalhaNaoQuebraOsDemais() {
        List<String> alertas = new ArrayList<>();
        ObservadorPreco defeituoso = v -> { throw new RuntimeException("falha proposital"); };
        ObservadorPreco ok = new AlertaAumentoPreco(1.0, alertas::add);
        MonitorPrecos.INSTANCE.registrar(defeituoso);
        MonitorPrecos.INSTANCE.registrar(ok);
        try {
            ArquivoHistoricoPreco repo = ArquivoHistoricoPreco.INSTANCE;
            repo.adicionar(new HistoricoPreco(1, 1, 10.0, LocalDate.of(2026, 1, 1)));
            repo.adicionar(new HistoricoPreco(2, 1, 20.0, LocalDate.of(2026, 2, 1)));
            assertEquals(1, alertas.size());          // o outro observador falhou, mas este foi avisado

            MonitorPrecos.INSTANCE.remover(ok);
            repo.adicionar(new HistoricoPreco(3, 1, 40.0, LocalDate.of(2026, 3, 1)));
            assertEquals(1, alertas.size());          // removido: não recebe mais
        } finally {
            MonitorPrecos.INSTANCE.remover(defeituoso);
            MonitorPrecos.INSTANCE.remover(ok);
        }
    }
}
