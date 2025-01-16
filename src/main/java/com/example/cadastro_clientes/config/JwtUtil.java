package com.example.cadastro_clientes.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")  // Agora a chave secreta pode ser definida no arquivo application.properties
    private String secretKey;

    @Value("${jwt.expirationTime}")  // Tempo de expiração configurável (em milissegundos)
    private long expirationTime;

    // Método para gerar o token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Método para validar o token
    public boolean validateToken(String token, String email) {
        String tokenEmail = extractUsername(token);
        return (tokenEmail.equals(email) && !isTokenExpired(token));
    }

    // Método para extrair o email (subject) do token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Método para extrair as claims do token
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para verificar se o token está expirado
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Método para extrair a data de expiração do token
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }
}
