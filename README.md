# Discord Arch

![Elixir](https://img.shields.io/badge/Elixir-4B275F?style=for-the-badge&logo=elixir&logoColor=white)
![Phoenix](https://img.shields.io/badge/Phoenix%20Framework-FD4F00?style=for-the-badge&logo=phoenixframework&logoColor=fff)
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=ffffff)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=ffffff)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=ffffff)
![Redis](https://img.shields.io/badge/Redis-D92D2A?style=for-the-badge&logo=redis&logoColor=ffffff)
![Node.js](https://img.shields.io/badge/Node.js-339933?style=for-the-badge&logo=node.js&logoColor=ffffff)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=000000)
![Socket.IO](https://img.shields.io/badge/Socket.IO-010001?style=for-the-badge&logo=socket-dot-io&logoColor=ffffff)

Este projeto é uma simulação da arquitetura de comunicação em tempo real do Discord, focado no envio de mensagens entre usuários em canais de texto e servidores. Ele implementa uma solução de backend utilizando Elixir para gerenciar conexões WebSocket em tempo real, Java para a API e a lógica de negócios, além de bancos de dados como PostgreSQL e MongoDB para armazenamento de dados estruturados e históricos de mensagens.

## Arquitetura

A plataforma é composta pelos seguintes modulos:

### [1. Hermes (Elixir)](https://github.com/lucasramallo/discord-arch/tree/main/hermes)

O módulo **Hermes** é responsável por gerenciar as conexões em tempo real utilizando **Phoenix Channels** e **WebSockets**. Ele permite a comunicação bidirecional entre o frontend e o servidor.

### [2. Server (Java)](https://github.com/lucasramallo/discord-arch/tree/main/server)

O módulo **Server** implementa a lógica de negócios, gestão de usuários, servidores e canais. Ele processa as requisições do frontend, valida permissões e interage com os bancos de dados **PostgreSQL** (para dados estruturados) e **MongoDB** (para histórico de mensagens). Também é responsável por cache de dados utilizando **Redis** e pela autenticação e autorização de usuários.

### **3. Front (Node.js)**

O módulo **Front** é o frontend da aplicação, responsável por fornecer a interface do usuário.

## Techs

A plataforma é composta pelos seguintes componentes:

# Tecnologias Utilizadas

- **Elixir**: Gerencia conexões em tempo real (WebSockets).
- **Java**: Implementa a API e a lógica de negócios.
- **Redis**: Cache para operações rápidas e temporárias.
- **PostgreSQL**: Armazena dados estruturados, como informações sobre usuários, servidores e canais.
- **MongoDB**: Armazena históricos de mensagens, aproveitando a flexibilidade para lidar com grandes volumes de dados não estruturados.
- **WebSockets**: Permite comunicação bidirecional em tempo real entre o cliente e o servidor.

## Funcionalidade

### Fluxo: Criação de Servidor

1. O cliente (frontend) envia uma requisição HTTP para a API Java com os detalhes do servidor (nome, membros iniciais, etc.).
2. A API Java valida os dados recebidos e cria um registro no PostgreSQL com informações do servidor.
3. O servidor é registrado e está pronto para aceitar conexões.
4. A API retorna os detalhes do servidor recém-criado.

### Fluxo: Gerenciamento de Conexões com Elixir

1. Quando um usuário entra em um servidor, o frontend (Node.js) inicia uma conexão WebSocket com o backend, que é gerenciada pelo Elixir.
2. O Elixir distribui conexões entre shards (grupos) para garantir escalabilidade horizontal.
3. O Elixir associa o usuário ao servidor no qual ele está participando, usando identificadores armazenados no PostgreSQL.

### Fluxo: Envio de Mensagens

1. O cliente envia uma mensagem por WebSocket.
2. O Elixir recebe a mensagem e valida o destinatário (canal, servidor ou usuário específico).
3. A mensagem é encaminhada para a API Java para validação adicional e persistência.
4. A API Java grava a mensagem no MongoDB para armazenar o histórico.
5. O Elixir recebe a confirmação e distribui a mensagem para os usuários conectados ao mesmo canal.
6. Mensagens recentes podem ser armazenadas no Redis para acesso rápido.

## Componentes Elixir

- **Gerenciador de Conexões**: Usando Phoenix Channels para criar e gerenciar conexões WebSocket.
- **Shard Manager**: Distribui usuários conectados entre diferentes shards. (Feature)
- **Router de Mensagens**: Roteia as mensagens para a API Java e distribui para os usuários conectados. (Feature)
- **Heartbeat**: Mantém a conexão ativa, verificando periodicamente se o cliente ainda está online. (Feature)
