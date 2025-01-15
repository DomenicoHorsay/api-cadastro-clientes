package com.example.cadastro_clientes.controller;

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
        boolean valido = clienteService.validarLogin(loginRequest.getEmail(), loginRequest.getSenha());
        return valido ? ResponseEntity.ok("Login válido") : ResponseEntity.status(401).body("Login inválido");
    }
}

