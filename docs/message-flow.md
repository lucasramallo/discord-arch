## Fluxo de uma Mensagem

### 1. Envio da Mensagem pelo Cliente
O cliente (navegador ou aplicativo) utiliza **WebSockets** para enviar uma mensagem ao servidor Phoenix. A mensagem contém informações como:
- **ID do remetente**
- **ID do canal/destinatário**
- **Conteúdo da mensagem**
- **Timestamp**

### 2. Gerenciamento em Tempo Real (Elixir/Phoenix)
- O Phoenix recebe a mensagem através de WebSockets.
- Valida o formato básico da mensagem.
- Envia a mensagem para o **RabbitMQ**, utilizando uma fila de mensagens para comunicação assíncrona com o backend Java.

### 3. Processamento no Backend (Java/Spring Boot)
- O Spring Boot consome a mensagem da fila RabbitMQ.
- Valida os dados recebidos, incluindo permissões do usuário e integridade dos dados.
- Atualiza o banco de dados:
  - **PostgreSQL**: Atualiza metadados, como o último ID da mensagem enviada em um canal.
  - **MongoDB**: Salva o conteúdo completo da mensagem no histórico.
  - Opcionalmente, adiciona a mensagem ao **Redis** para acesso rápido.

### 4. Distribuição da Mensagem para Outros Clientes
- O backend notifica o Phoenix que a mensagem foi processada e pode ser entregue.
- O Phoenix utiliza WebSockets para distribuir a mensagem aos outros clientes conectados ao mesmo canal.

### 5. Entrega e Feedback ao Remetente
- O Phoenix confirma ao remetente que a mensagem foi entregue e processada com sucesso.
- Notificações são enviadas aos destinatários, se configuradas, indicando que uma nova mensagem foi recebida.

### 6. Integração com RabbitMQ (Fila de Mensagens)
#### Publicação da Mensagem (Phoenix)
A mensagem é enviada à fila RabbitMQ no formato JSON. Exemplo de JSON:
{
"sender_id": "123",
"channel_id": "456",
"message": "Olá, mundo!",
"timestamp": "2025-01-03T12:00:00Z"
}
text

#### Consumo da Mensagem (Spring Boot)
O Spring Boot consome a mensagem da fila e segue o fluxo descrito para processamento e armazenamento.

### Vantagens desse Design
- **Escalabilidade**: O uso do RabbitMQ desacopla os serviços, permitindo que o sistema escale independentemente.
- **Resiliência**: Mensagens em fila evitam perda de dados caso um dos serviços falhe temporariamente.
- **Desempenho**: Redis fornece acesso rápido para mensagens recentes; MongoDB lida eficientemente com históricos de mensagens.
- **Flexibilidade**: PostgreSQL e MongoDB combinam o melhor de bancos de dados relacionais e não relacionais.

### Exemplo de Arquitetura do Sistema
| Componente       | Função                                                                 |
|------------------|------------------------------------------------------------------------|
| Front-end        | Clientes se conectam ao Phoenix via WebSockets.                        |
| Real-time        | O Phoenix gerencia conexões WebSocket e publica mensagens no RabbitMQ. |
| Backend          | O Spring Boot consome as mensagens, processa e salva nos bancos de dados. |
| Cache            | Redis armazena mensagens recentes para acesso rápido.                  |
| Bancos de Dados  | PostgreSQL para informações estruturadas; MongoDB para históricos de mensagens. |
| RabbitMQ         | Fila de mensagens para comunicação entre Phoenix e Spring Boot.        |

Se precisar de detalhes sobre como configurar RabbitMQ, integrar Phoenix com RabbitMQ ou configurar WebSockets no Phoenix e no cliente, posso ajudar a detalhar!
