package com.github.lucasramallo.server.core.dto.request;

import java.util.UUID;

public record CreateServerRequestDTO(String name, UUID owner_id) {
}
