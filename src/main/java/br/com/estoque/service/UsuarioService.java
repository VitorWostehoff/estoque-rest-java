package br.com.estoque.service;

import br.com.estoque.dao.UsuarioDAO;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
}
