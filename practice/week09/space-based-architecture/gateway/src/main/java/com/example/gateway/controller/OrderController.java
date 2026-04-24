package com.example.gateway.controller;

import com.example.common.dto.CreateOrderRequest;
import com.example.common.dto.OrderResponse;
import com.example.common.entity.OrderStatus;
import com.example.gateway.service.OrderGatewayService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST do Gateway para gerenciamento de pedidos.
 * Expõe endpoints para interação com o sistema distribuído.
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    
    @Autowired
    private OrderGatewayService orderGatewayService;
    
    /**
     * Cria um novo pedido.
     * 
     * @param request Dados do pedido
     * @return Pedido criado
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        try {
            logger.info("Recebendo requisição para criar pedido: {}", request);
            
            OrderResponse order = orderGatewayService.createOrder(request);
            
            logger.info("Pedido criado com sucesso: {}", order.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
            
        } catch (Exception e) {
            logger.error("Erro ao criar pedido: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Recupera um pedido pelo ID.
     * 
     * @param orderId ID do pedido
     * @return Pedido encontrado
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        try {
            logger.info("Buscando pedido: {}", orderId);
            
            OrderResponse order = orderGatewayService.getOrder(orderId);
            
            if (order != null) {
                logger.info("Pedido encontrado: {}", orderId);
                return ResponseEntity.ok(order);
            } else {
                logger.warn("Pedido não encontrado: {}", orderId);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            logger.error("Erro ao buscar pedido {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Lista todos os pedidos.
     * 
     * @return Lista de pedidos
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        try {
            logger.info("Listando todos os pedidos");
            
            List<OrderResponse> orders = orderGatewayService.getAllOrders();
            
            logger.info("Encontrados {} pedidos", orders.size());
            return ResponseEntity.ok(orders);
            
        } catch (Exception e) {
            logger.error("Erro ao listar pedidos: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Atualiza o status de um pedido.
     * 
     * @param orderId ID do pedido
     * @param status Novo status
     * @return Pedido atualizado
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String orderId, 
            @RequestParam OrderStatus status) {
        try {
            logger.info("Atualizando status do pedido {} para {}", orderId, status);
            
            OrderResponse order = orderGatewayService.updateOrderStatus(orderId, status);
            
            if (order != null) {
                logger.info("Status do pedido {} atualizado com sucesso", orderId);
                return ResponseEntity.ok(order);
            } else {
                logger.warn("Pedido não encontrado para atualização: {}", orderId);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            logger.error("Erro ao atualizar status do pedido {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Obtém estatísticas do sistema.
     * 
     * @return Estatísticas do sistema
     */
    @GetMapping("/statistics")
    public ResponseEntity<String> getStatistics() {
        try {
            logger.info("Obtendo estatísticas do sistema");
            
            String statistics = orderGatewayService.getSystemStatistics();
            
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            logger.error("Erro ao obter estatísticas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Endpoint de health check.
     * 
     * @return Status do serviço
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Gateway está funcionando");
    }
}
