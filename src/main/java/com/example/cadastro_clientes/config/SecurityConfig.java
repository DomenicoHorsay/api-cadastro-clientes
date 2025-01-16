package com.example.cadastro_clientes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Configura o BCryptPasswordEncoder
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite acesso a todos os endpoints
                .allowedOrigins("http://localhost:8080") // Permite requisições de origem http://localhost:3000 (onde o React está rodando)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos HTTP permitidos
                .allowedHeaders("*"); // Permite qualquer cabeçalho
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/clientes/cadastro", "/clientes/login").permitAll()  // Permite acesso sem autenticação
                        .anyRequest().authenticated())  // Requer autenticação para outras requisições
                .formLogin(withDefaults())  // Habilita o login de formulários se necessário
                .httpBasic(withDefaults())  // Configura autenticação básica se necessário
                .csrf(csrf -> csrf.disable());  // Desabilita CSRF de forma moderna

        return http.build();  // Retorna a configuração do SecurityFilterChain
    }
}
