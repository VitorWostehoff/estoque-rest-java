package br.com.estoque.security;
import br.com.estoque.dao.UsuarioDAO;
import br.com.estoque.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class AdminBootstrap implements ApplicationRunner {
    private final UsuarioDAO usuarioDAO;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;
    public AdminBootstrap(UsuarioDAO usuarioDAO, PasswordEncoder passwordEncoder,
                          @Value("${app.admin.email:}") String adminEmail,
                          @Value("${app.admin.password:}") String adminPassword) {
        this.usuarioDAO = usuarioDAO;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }
    @Override
    public void run(ApplicationArguments args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) return;
        if (adminPassword.length() < 8) throw new IllegalArgumentException("ADMIN_PASSWORD deve ter no mínimo 8 caracteres.");
        String email = adminEmail.trim().toLowerCase();
        if (usuarioDAO.findByEmail(email).isPresent()) return;
        Usuario admin = new Usuario();
        admin.setEmail(email);
        admin.setSenha(passwordEncoder.encode(adminPassword));
        admin.setRole("ADMIN");
        usuarioDAO.save(admin);
    }
}
