# MyKeeper (Java/JavaFX) - RA2 Design de Software

Gerenciador doméstico de estoque e compras, refatorado para aplicar quatro padrões GoF:
**Template Method, State, Strategy e Observer**.

## Requisitos
- JDK 21
- Maven (ou o `mvnw` incluso)

## Executar
```
./mvnw clean javafx:run      # Linux/macOS
mvnw.cmd clean javafx:run    # Windows
```
Os dados ficam em arquivos `.dat` na pasta de execução; os alertas de preço, em `alertas_preco.log`.

## Testar
```
./mvnw test
```
`PadroesTest` cobre os quatro padrões (14 testes).

## Onde está cada padrão
| Padrão | Pacote / classes principais |
|---|---|
| Template Method | `model.RepositorioArquivo`, `model.RepositorioComEstado`, `ui.CrudUI`, `ui.CrudComEstadoUI` |
| State | `model.estado.*` (`EstadoStatus`, `Ticket*`, `Lista*`, `Estoque*`), entidades `TicketSuporte`, `ListaCompras`, `Estoque` |
| Strategy | `ordenacao.*` (`EstrategiaOrdenacao`, `OrdenacaoPorId/Texto/Numero/Data`), contexto `ui.CrudUI` |
| Observer | `observer.*` (`MonitorPrecos`, `ObservadorPreco`, `AlertaAumentoPreco`, `AlertaMenorPreco`, `RegistroAlertasLog`) |

## Fluxos de status (State)
| Entidade | Fluxo | Regras |
|---|---|---|
| Ticket de Suporte | Aberto → Em andamento → Resolvido → Fechado | Reabertura de Resolvido/Em andamento; só exclui quando Aberto; Resolvido e Fechado não podem ser editados |
| Lista de Compras | Em elaboração → Finalizada → Comprada | Finalizada pode ser reaberta; Comprada é somente leitura |
| Estoque | Ativo → Inativo → Arquivado | Inativo pode ser reativado; só exclui quando Inativo; Arquivado é somente leitura |

As regras valem tanto na tela quanto no repositório (`RepositorioComEstado`).

## Documentação
- `docs/Relatorio_RA2_MyKeeper.pdf`: relatório técnico da entrega.
- `docs/diagramas/` (PNG):

| Arquivo | Conteúdo |
|---|---|
| `diagrama_pacotes.png` | Dependências entre pacotes |
| `diagrama_geral.png` | Diagrama de classes geral, agrupado por pacote |
| `model.png` | Entidades e repositórios (Template Method) |
| `model_estados.png` | Estados (State) |
| `ordenacao.png` | Estratégias de ordenação (Strategy) |
| `observer.png` | Alertas de preço (Observer) |
| `ui.png` | Telas CRUD (Template Method / contexto do Strategy) |
