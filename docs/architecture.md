# Arquitetura e Fluxos do Sistema

## 1. Visão Geral da Arquitetura

- **Elixir**: Gerencia conexões em tempo real (WebSockets).
- **Java**: Implementa a API e a lógica de negócios.
- **Redis**: Cache para operações rápidas e temporárias.
- **PostgreSQL**: Armazena dados estruturados como usuários, servidores e canais.
- **MongoDB**: Armazena históricos de mensagens devido à flexibilidade com grandes volumes de dados não estruturados.
- **WebSockets**: Permitem comunicação bidirecional em tempo real.

## 2. Fluxo: Criação de um Servidor

1. **Usuário envia uma requisição para criar um servidor**:
    - O cliente (frontend) envia uma requisição HTTP para a API Java com os detalhes do servidor (nome, membros iniciais, etc.).
    
2. **API Java processa a requisição**:
    - Valida os dados recebidos.
    - Cria um registro no PostgreSQL com informações do servidor, como ID, nome e dono.

3. **Resposta ao cliente**:
    - A API retorna ao cliente os detalhes do servidor recém-criado.
    - O servidor está pronto para aceitar conexões.

## 3. Fluxo: Gerenciamento de Conexões com Elixir

O **Elixir** gerencia conexões em tempo real, essenciais para a comunicação de mensagens.

1. **Conexão WebSocket**:
    - Quando um usuário entra em um servidor, o cliente inicia uma conexão WebSocket com o backend, gerenciado pelo Elixir.
    
2. **Shardings e Processos**:
    - Cada conexão é gerenciada como um processo isolado no Elixir.
    - O Elixir usa o framework **Phoenix** e **Phoenix Channels** para organizar as conexões.
    - As conexões são divididas entre múltiplos **shards**, garantindo escalabilidade.

3. **Associação ao Servidor**:
    - Após a conexão, o usuário é associado ao servidor no qual está participando, utilizando identificadores armazenados no PostgreSQL.

## 4. Fluxo: Envio de Mensagens

1. **Usuário envia mensagem**:
    - O cliente envia a mensagem por WebSocket.
    - O Elixir recebe a mensagem e valida o destinatário (canal, servidor ou usuário específico).

2. **Encaminhamento para API Java**:
    - O Elixir encaminha a mensagem para a API Java para validação adicional (como permissão ou moderação) e persistência.

3. **Armazenamento no MongoDB**:
    - A API Java grava a mensagem no MongoDB, armazenando o histórico do canal.

4. **Entrega da mensagem**:
    - O Elixir recebe a confirmação e encaminha a mensagem para os usuários conectados no mesmo canal, utilizando os processos WebSocket ativos.

5. **Cache para mensagens recentes**:
    - As mensagens recentes podem ser armazenadas no Redis para permitir consultas rápidas sem acessar o MongoDB.

## 5. Componentes para Desenvolver no Elixir

Para implementar o gerenciamento de conexões, você precisará dos seguintes componentes:

- **Gerenciador de Conexões**: Utiliza Phoenix Channels para criar e gerenciar conexões WebSocket.
- **Shard Manager**: Distribui usuários conectados entre diferentes shards para escalabilidade horizontal.
- **Router de Mensagens**: Roteia mensagens para a API Java e distribui para os usuários no mesmo canal.
- **Heartbeat**: Mantém a conexão ativa verificando periodicamente se o cliente ainda está online.

## 6. API Java

A **API Java** é responsável pela lógica de negócios e comunicação com os bancos de dados. Os principais endpoints incluem:

- **POST /servers**: Criação de servidores no PostgreSQL.
- **POST /messages**: Validação e gravação de mensagens no MongoDB.
- **GET /history**: Busca de mensagens antigas no MongoDB ou Redis.

## 7. Fluxo de Dados

1. **Cliente inicia uma conexão WebSocket** → Elixir gerencia a conexão.
2. **Cliente envia uma mensagem** → Elixir valida e encaminha para a API Java.
3. **API Java valida e persiste os dados** → MongoDB/Redis.
4. **Mensagem é entregue em tempo real** → Elixir distribui para os WebSockets ativos.
