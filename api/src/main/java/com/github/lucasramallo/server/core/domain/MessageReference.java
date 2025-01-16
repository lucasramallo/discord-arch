package com.github.lucasramallo.server.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "message_references")
@Data
public class MessageReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String message_id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @Column(name = "created_at")
    private Instant createdAt;
}

