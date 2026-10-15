package br.pucpr.projeto_crud_java.model;

import br.pucpr.projeto_crud_java.model.estado.ComEstado;
import br.pucpr.projeto_crud_java.model.estado.EstadoStatus;
import br.pucpr.projeto_crud_java.model.estado.EstadosLista;
import java.time.LocalDate;

public class ListaCompras implements ComEstado {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private LocalDate data_criacao;
    private String status;
    // não é salvo no arquivo, é recriado a partir do status
    private transient EstadoStatus estado;

    public ListaCompras(int id, String nome, LocalDate data_criacao, String status) {
        this.id = id;
        this.nome = nome;
        this.data_criacao = data_criacao;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.estado = null;
    }

    @Override
    public EstadoStatus getEstado() {
        if (estado == null) {
            estado = EstadosLista.de(status);
        }
        return estado;
    }

    @Override
    public void avancarStatus() {
        estado = getEstado().proximo();
        status = estado.getNome();
    }

    @Override
    public void voltarStatus() {
        estado = getEstado().anterior();
        status = estado.getNome();
    }

    @Override
    public String toString() {
        return "ListaCompras [ID: " + id + "] " + nome +
                " | Data de Criação: " + data_criacao +
                " | Status: " + status;
    }
}