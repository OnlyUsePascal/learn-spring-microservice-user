package com.example.user_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record CreateUserDto(String username, String password, String email) {
}
