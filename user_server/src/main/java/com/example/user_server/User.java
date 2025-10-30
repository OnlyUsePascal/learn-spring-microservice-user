package com.example.user_server;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
  @Id
  @UuidGenerator
  @Column(nullable = false, name = "id")
  private UUID id;

  @Column(nullable = false, name = "username", unique = true)
  private String username;

  @Column(nullable = false, name = "password")
  private String password;

  @Column(nullable = false, name = "email", unique = true)
  private String email;
}
