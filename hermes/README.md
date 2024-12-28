# Hermes - Gerenciador de Conexões em Tempo Real

![Elixir](https://img.shields.io/badge/Elixir-4B275F?style=for-the-badge&logo=elixir&logoColor=white)
![Phoenix](https://img.shields.io/badge/Phoenix%20Framework-FD4F00?style=for-the-badge&logo=phoenixframework&logoColor=fff)

**Hermes** é o módulo responsável pelo gerenciamento de conexões em tempo real no sistema, utilizando **Elixir** e o framework **Phoenix**. Ele gerencia as conexões WebSocket entre os clientes (frontend) e o servidor, permitindo a comunicação bidirecional e escalável entre os usuários da plataforma.

A escolha do Elixir foi motivada pela sua capacidade única de gerenciar um grande número de conexões simultâneas de forma altamente eficiente. Isso é possível devido ao seu modelo de concorrência baseado no Erlang VM (BEAM), que permite a execução de milhares, ou até milhões, de processos leves e independentes, sem impactar o desempenho do sistema.

## Funcionalidade Principal

O principal objetivo do **Hermes** é fornecer uma infraestrutura robusta para:

- Gerenciar conexões WebSocket entre os usuários.
- Dividir as conexões em **shards** (grupos), garantindo escalabilidade horizontal.
- Roteamento eficiente de mensagens entre os usuários conectados a canais específicos.
- Garantir a comunicação em tempo real, com baixo tempo de latência.

To start your Phoenix server:

  * Run `mix setup` to install and setup dependencies
  * Start Phoenix endpoint with `mix phx.server` or inside IEx with `iex -S mix phx.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.
