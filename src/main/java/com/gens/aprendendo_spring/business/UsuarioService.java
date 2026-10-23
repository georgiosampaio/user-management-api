package com.gens.aprendendo_spring.business;

import com.gens.aprendendo_spring.infrastructure.Exceptions.ConflictException;
import com.gens.aprendendo_spring.infrastructure.Exceptions.ResourceNotFoundException;
import com.gens.aprendendo_spring.infrastructure.entity.Usuario;
import com.gens.aprendendo_spring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;



    public Usuario salvarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        emailExiste(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    public void emailExiste(String email){
        if (verificaEmailExistente(email)) {
            throw new ConflictException("Email ja cadastrado" + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEemail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);

    }
}
