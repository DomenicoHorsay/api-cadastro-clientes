package com.example.cadastro_clientes.repository;

import com.example.cadastro_clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByEmailAndSenha(String email, String senha);
}
