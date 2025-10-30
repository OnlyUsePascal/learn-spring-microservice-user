package com.example.user_client;

import java.util.UUID;

public record GetUserDto(UUID id, String username, String email) {
};