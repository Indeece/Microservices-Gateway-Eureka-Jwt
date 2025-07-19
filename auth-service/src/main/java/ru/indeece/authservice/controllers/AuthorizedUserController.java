package ru.indeece.authservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthorizedUserController {

    @GetMapping("/info")
    public ResponseEntity<String> getAuthorizedUser() {
        return ResponseEntity.ok("This is authorized user controller");
    }
}
