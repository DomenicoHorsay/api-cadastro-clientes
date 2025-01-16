package com.example.cadastro_clientes.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // A chave secreta deve ser gerada corretamente
    private static final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Chave de 256 bits

    public static final long EXPIRATION_TIME = 86400000;  // 1 dia em milissegundos

    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }
}
