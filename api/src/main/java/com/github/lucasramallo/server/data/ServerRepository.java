package com.github.lucasramallo.server.data;

import com.github.lucasramallo.server.core.domain.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {
}
