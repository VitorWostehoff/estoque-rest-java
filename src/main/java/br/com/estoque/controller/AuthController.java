package br.com.estoque.controller;

import br.com.estoque.dto.LoginDTO;
import br.com.estoque.dto.TokenDTO;
import br.com.estoque.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto) {
        TokenDTO tokenDTO = authService.logar(dto);
        return ResponseEntity.ok(tokenDTO);
    }
}