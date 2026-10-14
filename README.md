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
`PadroesTest` cobre os quatro padrões (13 testes).

## Onde está cada padrão
| Padrão | Pacote / classes principais |
|---|---|
| Template Method | `model.RepositorioArquivo`, `model.RepositorioComEstado`, `ui.CrudUI`, `ui.CrudComEstadoUI` |
| State | `model.estado.*` (`EstadoStatus`, `Ticket*`, `Lista*`, `Estoque*`), entidades `TicketSuporte`, `ListaCompras`, `Estoque` |
| Strategy | `ordenacao.*` (`EstrategiaOrdenacao`, `OrdenacaoPorId/Texto/Numero/Data`), contexto `ui.CrudUI` |
| Observer | `observer.*` (`MonitorPrecos`, `ObservadorPreco`, `AlertaAumentoPreco`, `AlertaMenorPreco`, `RegistroAlertasLog`) |

Diagramas de classes em `docs/diagramas/` (PNG, SVG e PDF; fonte `.dot`).
