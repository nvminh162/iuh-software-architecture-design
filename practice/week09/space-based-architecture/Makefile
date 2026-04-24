SCALE ?= 3
ADDRS ?= localhost:5701,localhost:5702,localhost:5703
GATEWAY_PORT ?= 8080

.PHONY: help build docker-up docker-down docker-logs gateway-run pu-run test-api start-all stop-all clean status

help:
	@echo "🎯 Space-Based Architecture - Comandos Disponíveis:"
	@echo ""
	@echo "📦 Build e Setup:"
	@echo "  make build            - build Maven (todos os módulos)"
	@echo "  make clean            - limpa containers e artefatos"
	@echo ""
	@echo "🐳 Cluster Hazelcast (Docker):"
	@echo "  make docker-up        - sobe cluster Hazelcast"
	@echo "  make docker-down      - para cluster Hazelcast"
	@echo "  make docker-logs      - logs do cluster"
	@echo "  make status           - status dos containers"
	@echo ""
	@echo "🚀 Aplicações Java (Local):"
	@echo "  make gateway-run      - roda Gateway local"
	@echo "  make pu-run           - roda Processing Unit local"
	@echo ""
	@echo "🧪 Testes e Demonstração:"
	@echo "  make test-api         - testa a API com pedidos"
	@echo "  make start-all        - sobe cluster + apps (background)"
	@echo "  make stop-all         - para tudo"
	@echo ""
	@echo "📊 Variáveis:"
	@echo "  ADDRS=$(ADDRS)"
	@echo "  GATEWAY_PORT=$(GATEWAY_PORT)"

build:
	@echo "📦 Building Maven artifacts..."
	mvn -q -DskipTests clean install
	@echo "✅ Build completed"

# Cluster Hazelcast no Docker

docker-up: build
	@echo "🐳 Starting Hazelcast cluster..."
	docker compose up -d
	@echo "⏳ Waiting for cluster to be ready..."
	@sleep 5
	@echo "✅ Hazelcast cluster started on $(ADDRS)"

docker-down:
	@echo "⏹️  Stopping Hazelcast cluster..."
	docker compose down
	@echo "✅ Cluster stopped"

docker-logs:
	docker compose logs -f

status:
	@echo "🔍 Verificando status do sistema..."
	@echo ""
	@echo "📊 Status dos Containers Docker:"
	@docker compose ps
	@echo ""
	@echo "🌐 Verificando Gateway (porta $(GATEWAY_PORT)):"
	@curl -s http://localhost:$(GATEWAY_PORT)/api/orders/health > /dev/null && echo "✅ Gateway respondendo" || echo "❌ Gateway não está acessível"
	@echo ""
	@echo "💾 Verificando Cluster Hazelcast:"
	@for port in 5701 5702 5703; do \
		nc -z localhost $$port > /dev/null 2>&1 && echo "✅ Nó Hazelcast na porta $$port está ativo" || echo "❌ Nó Hazelcast na porta $$port não está respondendo"; \
	done

# Apps Java locais (sem docker), conectando ao cluster via cliente Hazelcast

gateway-run:
	@echo "🌐 Starting Gateway on port $(GATEWAY_PORT)..."
	cd gateway && \
	mvn spring-boot:run \
		-Dspring-boot.run.arguments="--spring.profiles.active=local --hazelcast.client.addresses=$(ADDRS) --server.port=$(GATEWAY_PORT)"

pu-run:
	@echo "⚙️  Starting Processing Unit..."
	cd processing-unit && \
	mvn spring-boot:run \
		-Dspring-boot.run.arguments="--spring.profiles.active=local --hazelcast.client.addresses=$(ADDRS)"

# Testes e demonstração

test-api:
	@echo "🧪 Testing API..."
	@if ! curl -s http://localhost:$(GATEWAY_PORT)/api/orders/health > /dev/null; then \
		echo "❌ Gateway not running. Start with: make gateway-run"; \
		exit 1; \
	fi
	@echo "✅ Gateway is running"
	@echo "📦 Creating test orders..."
	@for i in {1..5}; do \
		echo "Creating order $$i..."; \
		curl -s -X POST http://localhost:$(GATEWAY_PORT)/api/orders \
			-H "Content-Type: application/json" \
			-d "{\"cliente\": \"Cliente $$i\", \"produto\": \"Produto $$i\", \"quantidade\": $$i}" > /dev/null; \
		sleep 1; \
	done
	@echo "📊 Getting statistics..."
	@curl -s http://localhost:$(GATEWAY_PORT)/api/orders/statistics
	@echo ""
	@echo "📋 Listing all orders..."
	@curl -s http://localhost:$(GATEWAY_PORT)/api/orders | jq '.[] | {id, cliente, produto, status, processingUnitId}' 2>/dev/null || \
		curl -s http://localhost:$(GATEWAY_PORT)/api/orders

# Fluxo completo para demonstração

start-all: docker-up
	@echo "🚀 Starting complete architecture..."
	@echo "📋 Next steps:"
	@echo "1. Open terminal 1: make gateway-run"
	@echo "2. Open terminal 2: make pu-run"
	@echo "3. Test with: make test-api"
	@echo ""
	@echo "🌐 Gateway will be available at: http://localhost:$(GATEWAY_PORT)"

stop-all: docker-down
	@echo "⏹️  All services stopped"

clean:
	@echo "🧹 Cleaning up..."
	docker compose down || true
	rm -rf **/target
	@echo "✅ Cleanup completed"
