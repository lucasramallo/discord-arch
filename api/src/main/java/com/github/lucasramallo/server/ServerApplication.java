package com.github.lucasramallo.server;

import com.github.lucasramallo.server.core.PhoenixWebSocketClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}

@Component
class StartupRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

		URI serverUri = new URI("ws://localhost:4000/socket/websocket?token=userToken");
		PhoenixWebSocketClient client = new PhoenixWebSocketClient(serverUri);
		client.connectBlocking(); // Espera a conexão ser estabelecida

		String serverId = "abc123"; // ID do servidor
		client.joinChannel(serverId);
		client.sendMessage(serverId, "Olá, este é o servidor!");
	}
}