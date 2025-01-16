package com.github.lucasramallo.server.core.service;

import com.github.lucasramallo.server.core.domain.Server;
import com.github.lucasramallo.server.core.dto.request.CreateServerRequestDTO;
import com.github.lucasramallo.server.data.ServerRepository;

import java.util.UUID;

public class ServerService {
    private final ServerRepository serverRepository;

    public ServerService(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public void createServer(CreateServerRequestDTO requestDTO) {
        UUID uuid = UUID.randomUUID();

        Server newServer = new Server();
        newServer.setId(uuid);
        newServer.setName(requestDTO.name());
        newServer.setOwner(null);

        serverRepository.save(newServer);
    }
}
