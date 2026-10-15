package br.pucpr.projeto_crud_java.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Template Method: adicionar, atualizar e deletar são final e seguem sempre a mesma ordem.
// As subclasses só informam o nome do arquivo e, se precisarem, sobrescrevem os métodos
// de validação ou o aposAdicionar.
public abstract class RepositorioArquivo<T extends Identificavel> {

    private static File diretorioBase = new File(".");

    // usado nos testes para gravar os .dat numa pasta temporária
    public static void setDiretorioBase(File diretorio) {
        diretorioBase = diretorio;
    }

    private File arquivo() {
        return new File(diretorioBase, caminhoArquivo());
    }

    protected abstract String caminhoArquivo();

    // métodos que as subclasses podem sobrescrever (por padrão não fazem nada)
    protected void validar(T novo) { }

    protected void validarAtualizacao(T atual, T novo) { }

    protected void validarRemocao(T atual) { }

    protected void aposAdicionar(T adicionado) { }

    public final void adicionar(T novo) {
        validar(novo);
        ArrayList<T> lista = lerLista();
        lista.add(novo);
        salvarLista(lista);
        aposAdicionar(novo);
    }

    public final void atualizar(T atualizado) {
        validar(atualizado);
        ArrayList<T> lista = lerLista();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == atualizado.getId()) {
                validarAtualizacao(lista.get(i), atualizado);
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
                validarRemocao(lista.get(i));
                lista.remove(i);
                break;
            }
        }
        salvarLista(lista);
    }

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
