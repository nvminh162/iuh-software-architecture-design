#!/bin/bash

# Script para testar a API do Space-Based Architecture

echo "🧪 Testando API do Space-Based Architecture"
echo "=========================================="

BASE_URL="http://localhost:8080/api/orders"

# Verificar se o gateway está rodando
echo "🔍 Verificando se o Gateway está rodando..."
if ! curl -s "$BASE_URL/health" > /dev/null; then
    echo "❌ Gateway não está rodando. Inicie o sistema primeiro."
    echo "Execute: ./scripts/start-local.sh"
    exit 1
fi

echo "✅ Gateway está rodando"

# Teste 1: Health Check
echo ""
echo "1️⃣ Testando Health Check..."
curl -s "$BASE_URL/health"
echo ""

# Teste 2: Criar pedido
echo ""
echo "2️⃣ Criando pedido..."
ORDER_RESPONSE=$(curl -s -X POST "$BASE_URL" \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": "João Silva",
    "produto": "Notebook Dell Inspiron 15",
    "quantidade": 1
  }')

echo "Resposta: $ORDER_RESPONSE"

# Extrair ID do pedido
ORDER_ID=$(echo $ORDER_RESPONSE | grep -o '"id":"[^"]*"' | cut -d'"' -f4)

if [ ! -z "$ORDER_ID" ]; then
    echo "✅ Pedido criado com ID: $ORDER_ID"
    
    # Teste 3: Buscar pedido
    echo ""
    echo "3️⃣ Buscando pedido criado..."
    curl -s "$BASE_URL/$ORDER_ID" | jq '.' 2>/dev/null || curl -s "$BASE_URL/$ORDER_ID"
    echo ""
    
    # Teste 4: Aguardar processamento
    echo ""
    echo "4️⃣ Aguardando processamento (5 segundos)..."
    sleep 5
    
    # Teste 5: Verificar status atualizado
    echo "Verificando status atualizado..."
    curl -s "$BASE_URL/$ORDER_ID" | jq '.' 2>/dev/null || curl -s "$BASE_URL/$ORDER_ID"
    echo ""
    
else
    echo "❌ Erro ao criar pedido"
fi

# Teste 6: Criar mais pedidos
echo ""
echo "5️⃣ Criando mais pedidos para testar distribuição..."

for i in {1..3}; do
    echo "Criando pedido $i..."
    curl -s -X POST "$BASE_URL" \
      -H "Content-Type: application/json" \
      -d "{
        \"cliente\": \"Cliente $i\",
        \"produto\": \"Produto $i\",
        \"quantidade\": $i
      }" > /dev/null
    sleep 1
done

echo "✅ Pedidos adicionais criados"

# Teste 7: Listar todos os pedidos
echo ""
echo "6️⃣ Listando todos os pedidos..."
curl -s "$BASE_URL" | jq '.' 2>/dev/null || curl -s "$BASE_URL"
echo ""

# Teste 8: Estatísticas
echo ""
echo "7️⃣ Obtendo estatísticas do sistema..."
curl -s "$BASE_URL/statistics"
echo ""

echo ""
echo "🎯 Testes concluídos!"
echo "📊 Verifique os logs das Processing Units para ver o processamento distribuído"
echo "📝 Logs disponíveis em: logs/"
