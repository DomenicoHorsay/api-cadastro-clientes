package com.example.cadastro_clientes.service;

import com.example.cadastro_clientes.model.Cliente;
import com.example.cadastro_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        // Verifica se o email já existe no banco
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este email!");
        }
        // Salva o cliente se o email for único
        return clienteRepository.save(cliente);
    }

    public boolean validarLogin(String email, String senha) {
        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Email e senha são obrigatórios.");
        }
        return clienteRepository.findByEmailAndSenha(email, senha).isPresent();
    }
}
