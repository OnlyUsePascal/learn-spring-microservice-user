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
import com.example.user_client.GetUserWithUsernameDto;
import com.example.user_client.UserFeignClient;

@RestController
public class UserController implements UserFeignClient {
  @Autowired
  private UserRepo userRepo;

  @GetMapping("/")
  public ResponseEntity<List<GetUserDto>> listUsers() {
    var users = userRepo.findAll().stream().map(u -> new GetUserDto(u.getId(), u.getUsername(), u.getEmail())).toList();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{username}")
  public ResponseEntity<GetUserWithUsernameDto> findUserWithUsername(@PathVariable String username) {
    var u_ = userRepo.findByUsername(username);
    if (u_.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username:" + username);

    var u = u_.get();
    return ResponseEntity.ok(new GetUserWithUsernameDto(u.getId(), u.getUsername(), u.getEmail(), u.getPassword()));
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
