package com.example.user;

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

@RestController
public class UserController {
  @Autowired
  private UserRepo userRepo;

  @GetMapping("/")
  public ResponseEntity<List<User>> listUsers() {
    return ResponseEntity.ok(userRepo.findAll());
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
        .email(dto.getEmail())
        .username(dto.getUsername())
        .password(dto.getPassword())
        .build());
    return ResponseEntity.ok(user.getId());
  }
}
