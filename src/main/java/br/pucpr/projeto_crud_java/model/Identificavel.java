package br.pucpr.projeto_crud_java.model;

import java.io.Serializable;

// Toda entidade salva em arquivo tem um id
public interface Identificavel extends Serializable {
    int getId();
}
