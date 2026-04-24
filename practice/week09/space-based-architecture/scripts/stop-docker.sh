#!/bin/bash

# Script único para parar o cluster SBA no Docker

set -euo pipefail

echo "⏹️  Parando cluster SBA..."

docker compose down

echo "✅ Cluster parado"
