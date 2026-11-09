package br.com.estoque.service;

import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.dto.UsuarioDTO;
import br.com.estoque.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO salvar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // Criptografando a senha
        usuario.setRole(dto.getRole() != null ? dto.getRole() : "USER");

        Usuario salvo = usuarioDAO.save(usuario);
        return toDTO(salvo);
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioDAO.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setRole(usuario.getRole());
        return dto;
    }
}