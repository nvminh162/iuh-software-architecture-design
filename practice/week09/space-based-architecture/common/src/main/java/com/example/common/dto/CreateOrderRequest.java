package com.example.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para criação de pedidos.
 */
public class CreateOrderRequest {
    
    @NotBlank(message = "Cliente é obrigatório")
    private String cliente;
    
    @NotBlank(message = "Produto é obrigatório")
    private String produto;
    
    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    private Integer quantidade;
    
    // Construtores
    public CreateOrderRequest() {}
    
    public CreateOrderRequest(String cliente, String produto, Integer quantidade) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
    // Getters e Setters
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
    
    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "cliente='" + cliente + '\'' +
                ", produto='" + produto + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
