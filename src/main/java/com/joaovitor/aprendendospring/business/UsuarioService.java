package com.joaovitor.aprendendospring.business;

import com.joaovitor.aprendendospring.infrastructure.entity.Usuario;
import com.joaovitor.aprendendospring.infrastructure.exceptions.ConflitExceptions;
import com.joaovitor.aprendendospring.infrastructure.exceptions.ResourceNotFoundException;
import com.joaovitor.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario) {
        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (ConflitExceptions e) {
            throw new ConflitExceptions("E-mail informado já cadastrado", e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflitExceptions("E-mail já cadastrado" + email);
            }
        } catch (ConflitExceptions e) {
            throw new ConflitExceptions("E-mail já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email)    {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("E-mail não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}


