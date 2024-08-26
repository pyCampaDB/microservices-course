package com.auth.jwt.auth_service.controllers;

import com.auth.jwt.auth_service.dto.AuthUserDto;
import com.auth.jwt.auth_service.dto.NewUserDto;
import com.auth.jwt.auth_service.dto.RequestDto;
import com.auth.jwt.auth_service.dto.TokenDto;
import com.auth.jwt.auth_service.entities.AuthUser;
import com.auth.jwt.auth_service.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login (@RequestBody AuthUserDto authUserDto){
        TokenDto tokenDto = authService.login(authUserDto);
        /*if (authUserDto == null){
            return ResponseEntity.badRequest().build();
        }*/
        return  ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token, @RequestBody RequestDto requestDto){
        TokenDto tokenDto = authService.validate(token, requestDto);
        try{
            return ResponseEntity.ok(tokenDto);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create (@RequestBody /*AuthUserDto*/NewUserDto dto){
        AuthUser authUser = authService.save((dto));
        return ResponseEntity.ok(authUser);
    }
}
