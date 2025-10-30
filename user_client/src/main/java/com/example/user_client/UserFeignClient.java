package com.example.user_client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user")
public interface UserFeignClient {
  @GetMapping("/")
  public ResponseEntity<List<GetUserDto>> listUsers();
}
