# Arquitetura e Tecnologias do Discord

## 1. Abordagem Monolítica Moderna

O Discord utiliza um modelo **monolítico otimizado** em vez de uma arquitetura baseada em microsserviços. Essa decisão permite simplificar a comunicação entre componentes, reduzindo latências e problemas de consistência.

### Por que Monólito?

- **Baixa latência**: O Discord prioriza a experiência do usuário em tempo real. Com um monólito, evita a latência adicional associada à comunicação entre microsserviços.
- **Manutenção simplificada**: Embora um monólito possa ser difícil de escalar, ele facilita a depuração e coordenação de diferentes partes do sistema.

## 2. Linguagens e Frameworks

- **Elixir**: Usado para gerenciamento de conexões em tempo real, lidando com milhares de processos simultâneos.
- **Rust**: Escolhido para componentes de alta performance, como o processamento de eventos.
- **Python**: Usado para lógica de negócios, permitindo uma implementação rápida de novas funcionalidades.

## 3. Servidor de Eventos

O **Event Gateway** gerencia eventos em tempo real, como mensagens e atualizações de status.

- **WebSockets**: Mantém conexões persistentes com os clientes para eventos em tempo real.
- **Sharding**: Divide a carga entre múltiplos nós, evitando sobrecarga de um único servidor.

## 4. Escalabilidade Horizontal

Apesar do monólito, o Discord escala horizontalmente:

- **WebSockets distribuídos** entre shards.
- Cada shard gerencia uma fração dos usuários conectados, permitindo escalabilidade enquanto mantém a simplicidade.

## 5. Bancos de Dados

- **PostgreSQL**: Usado para dados transacionais, otimizado para as cargas específicas do Discord.
- **Redis**: Utilizado para caching e armazenamento temporário, acelerando operações em tempo real.
- **Bigtable**: Para armazenamento de dados históricos e análises.

## 6. Entrega de Mensagens

- **Enfileiramento de mensagens**: Garante a ordem correta.
- **Redis**: Armazena temporariamente mensagens antes de serem gravadas no banco de dados principal.

## 7. Ferramentas de Observabilidade

- **Grafana e Prometheus**: Para métricas e alertas.
- **ELK Stack (Elasticsearch, Logstash, Kibana)**: Para gerenciamento de logs.

## 8. Otimizações Específicas

- **Zlib (Compressão)**: Reduz a largura de banda necessária para transmissão de eventos.
- **Streaming Incremental**: Envia apenas as mudanças incrementais para os clientes, minimizando os dados transferidos.
