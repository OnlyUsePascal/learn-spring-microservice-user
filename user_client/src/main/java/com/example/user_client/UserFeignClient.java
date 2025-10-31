package com.example.user_client;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user")
public interface UserFeignClient {
  @GetMapping("/")
  public ResponseEntity<List<GetUserDto>> listUsers();

  @GetMapping("/{username}")
  public ResponseEntity<GetUserWithUsernameDto> findUserWithUsername(@PathVariable String username);

  @PostMapping("/")
  public ResponseEntity<UUID> createUser(@RequestBody CreateUserDto dto);
}
