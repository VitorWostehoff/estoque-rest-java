package br.com.estoque.service;

import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.dto.LoginDTO;
import br.com.estoque.dto.TokenDTO;
import br.com.estoque.model.Usuario;
import br.com.estoque.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private JwtService jwtService;

    public TokenDTO logar(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        Usuario usuario = usuarioDAO.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken(usuario);
        return new TokenDTO(token, "Bearer");
    }
}