package com.example.cadastro_clientes.controller;

import com.example.cadastro_clientes.config.JwtUtil;
import com.example.cadastro_clientes.dto.LoginRequest;
import com.example.cadastro_clientes.model.Cliente;
import com.example.cadastro_clientes.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.cadastrarCliente(cliente);
            return ResponseEntity.ok(novoCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> validarLogin(@RequestBody LoginRequest loginRequest) {
        // Verificar as credenciais e gerar o token
        String token = clienteService.validarLogin(loginRequest.getEmail(), loginRequest.getSenha());

        if (token != null) {
            return ResponseEntity.ok(token);  // Retorna o token JWT se o login for válido
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas");  // Retorna erro 401 se as credenciais forem inválidas
        }
    }



}
