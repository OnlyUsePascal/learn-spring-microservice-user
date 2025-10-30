package com.example.user_server;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.user_client.CreateUserDto;
import com.example.user_client.GetUserDto;

@RestController
public class UserController {
  @Autowired
  private UserRepo userRepo;

  @GetMapping("/")
  public ResponseEntity<List<GetUserDto>> listUsers() {
    var users = userRepo.findAll().stream().map(u -> new GetUserDto(u.getId(), u.getUsername(), u.getEmail())).toList();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{username}")
  public ResponseEntity<User> findUserWithUsername(@PathVariable String username) {
    var u = userRepo.findByUsername(username);
    if (u.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username:" + username);

    return ResponseEntity.ok(u.get());
  }

  @PostMapping("/")
  public ResponseEntity<UUID> createUser(@RequestBody CreateUserDto dto) {
    var user = userRepo.save(User.builder()
        .email(dto.email())
        .username(dto.username())
        .password(dto.password())
        .build());

    return ResponseEntity.ok(user.getId());
  }
}
