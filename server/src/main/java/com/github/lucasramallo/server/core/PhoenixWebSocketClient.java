package com.github.lucasramallo.server.core;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class PhoenixWebSocketClient extends WebSocketClient {

    public PhoenixWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Conectado ao WebSocket.");
        joinChannel("abc123");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Mensagem recebida: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Conexão fechada: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Erro: " + ex.getMessage());
    }

    public void joinChannel(String serverId) {
        String joinMessage = String.format(
                "{\"topic\": \"servers:%s\", \"event\": \"phx_join\", \"payload\": {}, \"ref\": \"1\"}",
                serverId
        );
        send(joinMessage);
        System.out.println("Tentando ingressar no canal servidores:" + serverId);
    }

    public void sendMessage(String serverId, String conteudo) {
        String message = String.format(
                "{\"topic\": \"servers:%s\", \"event\": \"mensagem\", \"payload\": {\"conteudo\": \"%s\"}, \"ref\": \"2\"}",
                serverId, conteudo
        );
        send(message);
        System.out.println("Mensagem enviada para servidores:" + serverId);
    }
}
