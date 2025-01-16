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
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este email!");
        }
    }

            }
    }
}
