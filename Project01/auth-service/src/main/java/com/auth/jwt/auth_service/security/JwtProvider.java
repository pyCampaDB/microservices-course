package com.auth.jwt.auth_service.security;

import com.auth.jwt.auth_service.dto.RequestDto;
import com.auth.jwt.auth_service.entities.AuthUser;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
//import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    //Deprecated
    /*@Value("${jwt.secret}")
    private String secret;*/
    private Key secret;

    @Autowired
    private RouteValidator routeValidator;

    @PostConstruct
    protected void init(){
        // Deprecated
        /*secret = Base64.getEncoder().encodeToString(secret.getBytes());*/
        byte[] apiKeySecretBytes = new byte[64];
        new SecureRandom().nextBytes(apiKeySecretBytes);
        secret = Keys.hmacShaKeyFor(apiKeySecretBytes);
    }
    public String createToken(AuthUser authUser){
        Map<String, Object> claims = new HashMap<>();
        //Deprecated
        /*claims = Jwts.claims().setSubject(authUser.getUsername());*/
        claims.put("id", authUser.getId());
        claims.put("role", authUser.getRole());
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .claims(claims)
                .subject(authUser.getUsername())
                .issuedAt(now)
                .expiration(exp)
                .signWith(Keys.hmacShaKeyFor(secret.getEncoded()))
                .compact();
        //Deprecated
        /*return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();*/

    }

    public boolean validate (String token, RequestDto requestDto){
        try{
            //Deprecated
            /*Jwts.parser().setSigningKey(secret).parseClaimsJws(token);*/
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getEncoded()))
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception exception) {
            return false;
        }
        return isAdmin(token) || !routeValidator.isAdmin(requestDto);
    }

    public String getUsernameFromToken (String token){
        try{
            //Deprecated
            /* return Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody().getSubject();*/
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getEncoded()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }catch (Exception e){
            return "Bad token";
        }
    }

    private boolean isAdmin(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getEncoded()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role").equals("admin");
    }
}
