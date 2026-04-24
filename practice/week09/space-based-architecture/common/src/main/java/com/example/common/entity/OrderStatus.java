package com.example.common.entity;

/**
 * Enum que representa os possíveis status de um pedido.
 */
public enum OrderStatus {
    PENDENTE("Pendente"),
    PROCESSANDO("Processando"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");
    
    private final String descricao;
    
    OrderStatus(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
