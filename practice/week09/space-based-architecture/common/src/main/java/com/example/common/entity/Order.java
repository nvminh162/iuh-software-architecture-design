package com.example.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade Order que representa um pedido no sistema distribuído.
 * Esta entidade é compartilhada entre o Gateway e as Processing Units.
 */
public class Order {
    
    private String id;
    
    @NotBlank(message = "Cliente é obrigatório")
    private String cliente;
    
    @NotBlank(message = "Produto é obrigatório")
    private String produto;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade;
    
    private OrderStatus status;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataAtualizacao;
    
    private String processingUnitId;
    
    // Construtores
    public Order() {
        this.status = OrderStatus.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public Order(String cliente, String produto, Integer quantidade) {
        this();
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
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
        this.dataAtualizacao = LocalDateTime.now();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Order{" +
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
