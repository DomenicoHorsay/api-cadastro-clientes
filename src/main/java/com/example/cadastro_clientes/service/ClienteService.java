package com.example.cadastro_clientes.service;

import com.example.cadastro_clientes.model.Cliente;
import com.example.cadastro_clientes.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;  // Injeção do JwtTokenProvider

    public Cliente cadastrarCliente(Cliente cliente) {
        // Criptografa a senha antes de salvar no banco
        String senhaCriptografada = passwordEncoder.encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);

        // Verifica se o email já está cadastrado
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este email!");
        }

        return clienteRepository.save(cliente);  // Salva o cliente
    }

    public String validarLogin(String email, String senha) {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            if (passwordEncoder.matches(senha, cliente.getSenha())) {
                // Agora, utilizamos o JwtTokenProvider para gerar o token
                return jwtTokenProvider.gerarToken(cliente.getEmail());
            } else {
                throw new BadCredentialsException("Senha inválida.");
            }
        } else {
            throw new BadCredentialsException("Email não encontrado.");
        }
    }
}
