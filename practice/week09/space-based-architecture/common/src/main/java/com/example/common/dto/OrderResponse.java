package com.example.common.dto;

import com.example.common.entity.Order;
import com.example.common.entity.OrderStatus;

import java.time.LocalDateTime;

/**
 * DTO para resposta de pedidos.
 */
public class OrderResponse {
    
    private String id;
    private String cliente;
    private String produto;
    private Integer quantidade;
    private OrderStatus status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private String processingUnitId;
    
    // Construtores
    public OrderResponse() {}
    
    public OrderResponse(Order order) {
        this.id = order.getId();
        this.cliente = order.getCliente();
        this.produto = order.getProduto();
        this.quantidade = order.getQuantidade();
        this.status = order.getStatus();
        this.dataCriacao = order.getDataCriacao();
        this.dataAtualizacao = order.getDataAtualizacao();
        this.processingUnitId = order.getProcessingUnitId();
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCliente() {
        return cliente;
    }
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    public String getProduto() {
        return produto;
    }
    
    public void setProduto(String produto) {
        this.produto = produto;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    public String getProcessingUnitId() {
        return processingUnitId;
    }
    
    public void setProcessingUnitId(String processingUnitId) {
        this.processingUnitId = processingUnitId;
    }
    
    @Override
    public String toString() {
        return "OrderResponse{" +
                "id='" + id + '\'' +
                ", cliente='" + cliente + '\'' +
                ", produto='" + produto + '\'' +
                ", quantidade=" + quantidade +
                ", status=" + status +
                ", dataCriacao=" + dataCriacao +
                ", dataAtualizacao=" + dataAtualizacao +
                ", processingUnitId='" + processingUnitId + '\'' +
                '}';
    }
}
