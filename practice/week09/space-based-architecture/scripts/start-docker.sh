#!/bin/bash

# Script único para iniciar o cluster SBA com Docker (com escala)

set -euo pipefail

SCALE=${1:-3}

echo "🐳 Iniciando cluster SBA com ${SCALE} Processing Units"

if ! docker info > /dev/null 2>&1; then
  echo "❌ Docker não está rodando. Inicie o Docker Desktop."
  exit 1
fi

# Build dos jars
echo "📦 Construindo artefatos Maven..."
mvn -q -e -DskipTests clean package

# Subir serviços
echo "🔨 Subindo serviços com escala = ${SCALE}..."
docker compose up --build -d --scale processing-unit=${SCALE}

# Aguardar inicialização
sleep 8

echo "📊 Status dos containers:"
docker compose ps

echo ""
echo "✅ Cluster iniciado"
echo "- Gateway: http://localhost:8080"
echo "- Escala atual de PUs: ${SCALE}"
echo ""
echo "🧪 Teste rápido:"
echo "curl -X POST http://localhost:8080/api/orders -H 'Content-Type: application/json' -d '{"cliente":"João","produto":"Notebook","quantidade":1}'"
