package com.ssafy.trip.auth.controller;


import com.ssafy.trip.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @GetMapping("/login")
//    public ResponseEntity<Void> login(HttpSession httpSession) throws URISyntaxException {
//        return authService.authorize();
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<Object> logout() throws URISyntaxException {
//        return null;
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Object> delete() throws URISyntaxException {
//        return null;
//    }

//    @GetMapping("/redirect")
//    public ResponseEntity<Object> redirect(@RequestParam(required = false) String code, @RequestParam String state, @RequestParam(required = false) String error, @RequestParam(required = false) String error_description) throws URISyntaxException {
//        System.out.println(code + " " + state + " " + error + " " + error_description);
//        return authService.getUserInfo(code, state);
//    }

}
