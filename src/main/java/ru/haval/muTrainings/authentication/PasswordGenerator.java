package ru.haval.muTrainings.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
  public static void main2(String[] args) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String rawPassword = "Haval_123";
    System.out.println(bCryptPasswordEncoder.encode(rawPassword));
  }
}
