package com.example.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
  @Autowired
  private UserRepo userRepo;

  @Override
  public void run(String... args) {
    userRepo.deleteAll();

    var users = Arrays.asList(
        User.builder()
            .email("admin@mail.com")
            .username("admin")
            .password("123")
            .build(),

        User.builder()
            .email("user@mail.com")
            .username("user")
            .password("123")
            .build());

    userRepo.saveAll(users);
  }

  
}
