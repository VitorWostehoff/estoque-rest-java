package br.com.estoque.service;
import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.dto.UsuarioAtualizacaoDTO;
import br.com.estoque.dto.UsuarioCadastroDTO;
import br.com.estoque.dto.UsuarioDTO;
import br.com.estoque.exception.ConflictException;
import br.com.estoque.exception.BusinessException;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UsuarioDTO cadastrar(UsuarioCadastroDTO dados) {
        String email = dados.getEmail().trim().toLowerCase();
        if (usuarioDAO.findByEmail(email).isPresent()) throw new ConflictException("E-mail já cadastrado.");
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        usuario.setRole(dados.getRole() == null ? "USER" : dados.getRole());
        return toDTO(usuarioDAO.save(usuario));
    }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioDAO.findAll().stream().map(this::toDTO).toList();
    }
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) { return toDTO(encontrar(id)); }
    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BusinessException("Informe um e-mail para a busca.");
        }
        return usuarioDAO.findByEmailContainingIgnoreCase(email.trim())
                .stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscarPorPerfil(String perfil) {
        if (perfil == null || (!perfil.equalsIgnoreCase("ADMIN") && !perfil.equalsIgnoreCase("USER"))) {
            throw new BusinessException("O perfil deve ser ADMIN ou USER.");
        }
        return usuarioDAO.findByRoleIgnoreCase(perfil)
                .stream().map(this::toDTO).toList();
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioAtualizacaoDTO dados) {
        Usuario usuario = encontrar(id);
        String email = dados.getEmail().trim().toLowerCase();
        usuarioDAO.findByEmail(email).ifPresent(outro -> {
            if (!outro.getId().equals(id)) throw new ConflictException("E-mail já cadastrado.");
        });
        usuario.setEmail(email);
        if (dados.getSenha() != null) {
            usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        }
        return toDTO(usuarioDAO.save(usuario));
    }
    @Transactional
    public void excluir(Long id) { usuarioDAO.delete(encontrar(id)); }
    private Usuario encontrar(Long id) {
        return usuarioDAO.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
    }
    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getRole());
    }
}
