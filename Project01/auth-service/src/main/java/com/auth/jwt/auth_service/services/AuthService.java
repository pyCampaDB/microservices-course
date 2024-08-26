package com.auth.jwt.auth_service.services;

import com.auth.jwt.auth_service.dto.AuthUserDto;
import com.auth.jwt.auth_service.dto.NewUserDto;
import com.auth.jwt.auth_service.dto.RequestDto;
import com.auth.jwt.auth_service.dto.TokenDto;
import com.auth.jwt.auth_service.entities.AuthUser;
import com.auth.jwt.auth_service.repositories.AuthUserRepository;
import com.auth.jwt.auth_service.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public AuthUser save(/*AuthUserDto*/NewUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
        if (user.isPresent()){
            return null;
        }
        String password = passwordEncoder.encode(dto.getPassword());
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .password(password)
                .role(dto.getRole())
                .build();
        return authUserRepository.save(authUser);
    }

    public TokenDto login(AuthUserDto dto){
        Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
        if (user.isEmpty()){
            return null;
        }
        // if (dto.getPassword().equals(user.get().getPassword())) ... implicit method
        if (passwordEncoder.matches(dto.getPassword(), user.get().getPassword())){
            return new TokenDto(jwtProvider.createToken(user.get()));
        }
        return null;
    }

    public TokenDto validate(String token, RequestDto requestDto){
        if(!jwtProvider.validate(token, requestDto))
            return null;
        String username = jwtProvider.getUsernameFromToken(token);
        if (authUserRepository.findByUsername(username).isEmpty())
            return null;
        return new TokenDto(token);
    }
}
