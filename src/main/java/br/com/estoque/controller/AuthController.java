package br.com.estoque.controller;
import br.com.estoque.dto.LoginDTO;
import br.com.estoque.dto.TokenDTO;
import br.com.estoque.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dados) {
        return ResponseEntity.ok(authService.logar(dados));
    }
}
