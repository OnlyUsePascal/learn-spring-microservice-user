package com.example.user_client;

import java.util.UUID;

public record GetUserWithUsernameDto(UUID id, String username, String email, String password) {
  
}
