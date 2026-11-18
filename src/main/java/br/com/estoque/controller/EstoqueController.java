package br.com.estoque.controller;
import br.com.estoque.dto.EstoqueDTO;
import br.com.estoque.model.Estoque;
import br.com.estoque.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/estoques")
public class EstoqueController {
    private final EstoqueService estoqueService;
    public EstoqueController(EstoqueService estoqueService) { this.estoqueService = estoqueService; }
    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() { return ResponseEntity.ok(estoqueService.listarTodos()); }
    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable int id) { return ResponseEntity.ok(estoqueService.buscarPorId(id)); }
    @GetMapping("/busca")
    public ResponseEntity<List<Estoque>> buscarPorNome(@RequestParam String nome) { return ResponseEntity.ok(estoqueService.buscarPorNome(nome)); }
    @GetMapping("/localizacao")
    public ResponseEntity<List<Estoque>> buscarPorLocalizacao(@RequestParam String localizacao) { return ResponseEntity.ok(estoqueService.buscarPorLocalizacao(localizacao)); }
    @PostMapping
    public ResponseEntity<Estoque> cadastrar(@Valid @RequestBody EstoqueDTO dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.cadastrar(dados));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Estoque> atualizar(@PathVariable int id, @Valid @RequestBody EstoqueDTO dados) {
        return ResponseEntity.ok(estoqueService.atualizar(id, dados));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        estoqueService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
