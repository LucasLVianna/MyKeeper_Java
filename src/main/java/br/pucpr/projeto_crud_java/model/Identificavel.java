package br.pucpr.projeto_crud_java.model;

import java.io.Serializable;

/**
 * Contrato mínimo de uma entidade persistível: possui identificador inteiro
 * e pode ser serializada. Permite que o repositório genérico trabalhe com
 * qualquer entidade do sistema.
 */
public interface Identificavel extends Serializable {
    int getId();
}
