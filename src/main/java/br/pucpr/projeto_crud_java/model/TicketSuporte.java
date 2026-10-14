package br.pucpr.projeto_crud_java.model;

import br.pucpr.projeto_crud_java.model.estado.ComEstado;
import br.pucpr.projeto_crud_java.model.estado.EstadoStatus;
import br.pucpr.projeto_crud_java.model.estado.EstadosTicket;


public class TicketSuporte implements ComEstado {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titulo;
    private String prioridade;
    private String status;
    /** Estado atual (padrão State). Não é serializado: é reconstruído a partir de {@code status}. */
    private transient EstadoStatus estado;

    public TicketSuporte(int id, String titulo, String prioridade, String status) {
        this.id = id;
        this.titulo = titulo;
        this.prioridade = prioridade;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
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
            estado = EstadosTicket.de(status);
        }
        return estado;
    }

    @Override
    public void avancarStatus() {
        estado = getEstado().proximo();   // delegação ao objeto de estado
        status = estado.getNome();
    }

    @Override
    public void voltarStatus() {
        estado = getEstado().anterior();
        status = estado.getNome();
    }

    @Override
    public String toString() {
        return "TicketSuporte [ID: " + id + "] " + titulo +
                " | Prioridade: " + prioridade +
                " | Status: " + status;
    }
}
