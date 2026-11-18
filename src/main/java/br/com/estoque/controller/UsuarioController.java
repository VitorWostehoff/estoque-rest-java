package br.com.estoque.controller;
import br.com.estoque.dto.UsuarioAtualizacaoDTO;
import br.com.estoque.dto.UsuarioCadastroDTO;
import br.com.estoque.dto.UsuarioDTO;
import br.com.estoque.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioCadastroDTO dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrar(dados));
    }
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    @GetMapping("/busca")
    public ResponseEntity<List<UsuarioDTO>> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }

    @GetMapping("/perfil")
    public ResponseEntity<List<UsuarioDTO>> buscarPorPerfil(@RequestParam String perfil) {
        return ResponseEntity.ok(usuarioService.buscarPorPerfil(perfil));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioAtualizacaoDTO dados) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dados));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
