# 🏗️ Arquitetura Space-Based Architecture

## Visão Geral

Este projeto implementa uma **Space-Based Architecture (SBA)** que utiliza um **espaço de dados compartilhado em memória** para comunicação entre componentes distribuídos.

## Componentes Principais

### 1. Gateway (API REST)
- **Função**: Ponto de entrada para requisições externas
- **Tecnologia**: Spring Boot + REST Controllers
- **Responsabilidades**:
  - Receber requisições HTTP
  - Validar dados de entrada
  - Interagir com o espaço distribuído
  - Retornar respostas para clientes

### 2. Processing Units (PUs)
- **Função**: Processamento de dados e lógica de negócio
- **Tecnologia**: Spring Boot + Serviços assíncronos
- **Responsabilidades**:
  - Processar pedidos de forma assíncrona
  - Armazenar dados no espaço distribuído
  - Executar lógica de negócio
  - Manter estatísticas de processamento

### 3. Data Grid (Hazelcast)
- **Função**: Espaço compartilhado em memória
- **Tecnologia**: Hazelcast In-Memory Data Grid
- **Responsabilidades**:
  - Armazenar dados distribuídos
  - Sincronizar dados entre nós
  - Garantir alta disponibilidade
  - Fornecer acesso rápido aos dados

## Fluxo de Dados

```
Cliente → Gateway → Data Grid ← Processing Units
   ↑         ↓         ↓              ↓
   └─────────┴─────────┴──────────────┘
```

### 1. Criação de Pedido
1. Cliente envia requisição POST para Gateway
2. Gateway valida dados e cria entidade Order
3. Gateway salva pedido no Data Grid
4. Processing Units detectam novo pedido
5. PU processa pedido de forma assíncrona
6. PU atualiza status no Data Grid

### 2. Consulta de Pedido
1. Cliente envia requisição GET para Gateway
2. Gateway busca pedido no Data Grid
3. Gateway retorna dados para cliente

## Características da Arquitetura

### ✅ Vantagens
- **Escalabilidade**: Fácil adição de novas Processing Units
- **Tolerância a Falhas**: Dados replicados no cluster
- **Performance**: Acesso rápido a dados em memória
- **Flexibilidade**: Componentes independentes
- **Simplicidade**: Comunicação via espaço compartilhado

### ⚠️ Considerações
- **Consistência**: Eventual consistency entre nós
- **Memória**: Dados limitados pela RAM disponível
- **Complexidade**: Gerenciamento de cluster distribuído
- **Debugging**: Mais complexo em ambientes distribuídos

## Configuração do Cluster

### Hazelcast Configuration
```yaml
cluster-name: space-based-cluster
port: 5701
backup-count: 1
time-to-live: 3600 seconds
max-idle: 1800 seconds
```

### Processamento Assíncrono
- Pedidos são processados em background
- Status atualizado automaticamente
- Logs detalhados para monitoramento
- Estatísticas em tempo real

## Monitoramento e Observabilidade

### Logs Estruturados
- Conexão ao cluster
- Processamento de pedidos
- Estatísticas de sistema
- Erros e exceções

### Métricas Disponíveis
- Total de pedidos
- Pedidos por status
- Processing Units ativas
- Performance do cluster

### Health Checks
- Gateway: `/api/orders/health`
- Processing Units: Logs de status
- Cluster: Estatísticas via API

## Cenários de Teste

### 1. Criação de Pedidos
- Múltiplos pedidos simultâneos
- Validação de dados
- Distribuição entre PUs

### 2. Tolerância a Falhas
- Parada de Processing Unit
- Recuperação automática
- Continuidade do serviço

### 3. Escalabilidade
- Adição de novas PUs
- Distribuição de carga
- Performance sob carga

## Próximos Passos

### Melhorias Possíveis
1. **Persistência**: Adicionar banco de dados para backup
2. **Cache**: Implementar cache distribuído
3. **Métricas**: Integrar com Prometheus/Grafana
4. **Segurança**: Adicionar autenticação/autorização
5. **Load Balancing**: Implementar balanceamento de carga

### Extensões
1. **Event Sourcing**: Histórico completo de eventos
2. **CQRS**: Separação de comandos e consultas
3. **Saga Pattern**: Transações distribuídas
4. **Circuit Breaker**: Proteção contra falhas

## Conclusão

Esta implementação demonstra os conceitos fundamentais da Space-Based Architecture:
- **Espaço compartilhado** para comunicação
- **Processamento distribuído** de dados
- **Tolerância a falhas** e alta disponibilidade
- **Escalabilidade horizontal** simples

O sistema é funcional e pode ser usado como base para implementações mais complexas em produção.
