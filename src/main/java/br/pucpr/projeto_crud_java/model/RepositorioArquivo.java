package br.pucpr.projeto_crud_java.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * PADRÃO: Template Method (GoF, comportamental) - AbstractClass.
 *
 * Define o esqueleto das operações de persistência em arquivo (adicionar,
 * atualizar, deletar). A sequência de passos de cada operação é fixa
 * (métodos finais), enquanto os passos variáveis ficam em métodos gancho
 * ("hooks") que as subclasses concretas sobrescrevem:
 *
 *   - caminhoArquivo()           : passo abstrato (obrigatório)
 *   - validar / validarAtualizacao / validarRemocao : ganchos de regra de negócio
 *   - aposAdicionar              : gancho de pós-processamento (ex.: notificar observadores)
 *
 * @param <T> tipo da entidade persistida
 */
public abstract class RepositorioArquivo<T extends Identificavel> {

    private static File diretorioBase = new File(".");

    /** Define a pasta onde os arquivos .dat são gravados (usado pelos testes; padrão: pasta atual). */
    public static void setDiretorioBase(File diretorio) {
        diretorioBase = diretorio;
    }

    private File arquivo() {
        return new File(diretorioBase, caminhoArquivo());
    }

    // ---------- passos variáveis (hooks) ----------

    /** Passo obrigatório: nome do arquivo .dat da entidade. */
    protected abstract String caminhoArquivo();

    /** Gancho: valida uma entidade antes de ser adicionada. Padrão: não faz nada. */
    protected void validar(T novo) { }

    /** Gancho: valida a troca de {@code atual} por {@code novo}. Padrão: não faz nada. */
    protected void validarAtualizacao(T atual, T novo) { }

    /** Gancho: valida a remoção da entidade. Padrão: não faz nada. */
    protected void validarRemocao(T atual) { }

    /** Gancho: executado depois que a entidade foi gravada. Padrão: não faz nada. */
    protected void aposAdicionar(T adicionado) { }

    // ---------- esqueleto do algoritmo (template methods) ----------

    public final void adicionar(T novo) {
        validar(novo);                       // 1. gancho
        ArrayList<T> lista = lerLista();     // 2. passo fixo
        lista.add(novo);                     // 3. passo fixo
        salvarLista(lista);                  // 4. passo fixo
        aposAdicionar(novo);                 // 5. gancho
    }

    public final void atualizar(T atualizado) {
        ArrayList<T> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                validarAtualizacao(lista.get(i), atualizado);   // gancho
                lista.set(i, atualizado);
                break;
            }
        }
        salvarLista(lista);
    }

    public final void deletar(int id) {
        ArrayList<T> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                validarRemocao(lista.get(i));                   // gancho
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }

    // ---------- passos fixos de infraestrutura ----------

    public final void salvarLista(ArrayList<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo()))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public final ArrayList<T> lerLista() {
        ArrayList<T> lista = new ArrayList<>();
        File arq = arquivo();
        if (arq.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
                lista = (ArrayList<T>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao ler: " + e.getMessage());
            }
        }
        return lista;
    }

    public final int gerarId() {
        int maiorId = 0;
        for (T item : lerLista()) {
            if (item.getId() > maiorId) maiorId = item.getId();
        }
        return maiorId + 1;
    }

    public final T buscarPorId(int id) {
        for (T item : lerLista()) {
            if (item.getId() == id) return item;
        }
        return null;
    }
}
