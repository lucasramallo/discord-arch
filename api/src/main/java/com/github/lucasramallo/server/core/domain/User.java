package com.github.lucasramallo.server.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String status;

    @Column(name = "created_at")
    private Instant createdAt;
}
