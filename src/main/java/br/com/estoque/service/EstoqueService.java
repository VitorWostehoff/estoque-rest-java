package br.com.estoque.service;
import br.com.estoque.dao.EstoqueDAO;
import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.dto.EstoqueDTO;
import br.com.estoque.exception.BusinessException;
import br.com.estoque.exception.ConflictException;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.model.Estoque;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class EstoqueService {
    private final EstoqueDAO estoqueDAO;
    private final ProdutoDAO produtoDAO;
    public EstoqueService(EstoqueDAO estoqueDAO, ProdutoDAO produtoDAO) {
        this.estoqueDAO = estoqueDAO;
        this.produtoDAO = produtoDAO;
    }
    @Transactional(readOnly = true)
    public List<Estoque> listarTodos() { return estoqueDAO.findAll(); }
    @Transactional(readOnly = true)
    public Estoque buscarPorId(int id) {
        return estoqueDAO.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Estoque com ID " + id + " não encontrado."));
    }
    @Transactional(readOnly = true)
    public List<Estoque> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) throw new BusinessException("Informe um nome para a busca.");
        return estoqueDAO.findByNomeContainingIgnoreCase(nome.trim());
    }
    @Transactional(readOnly = true)
    public List<Estoque> buscarPorLocalizacao(String localizacao) {
        if (localizacao == null || localizacao.isBlank()) throw new BusinessException("Informe uma localização para a busca.");
        return estoqueDAO.findByLocalizacaoContainingIgnoreCase(localizacao.trim());
    }
    @Transactional
    public Estoque cadastrar(EstoqueDTO dados) {
        Estoque estoque = new Estoque();
        preencher(estoque, dados);
        return estoqueDAO.save(estoque);
    }
    @Transactional
    public Estoque atualizar(int id, EstoqueDTO dados) {
        Estoque estoque = buscarPorId(id);
        preencher(estoque, dados);
        return estoqueDAO.save(estoque);
    }
    @Transactional
    public void excluir(int id) {
        Estoque estoque = buscarPorId(id);
        if (produtoDAO.existsByEstoqueId(id)) {
            throw new ConflictException("Não é possível excluir um estoque com produtos cadastrados.");
        }
        estoqueDAO.delete(estoque);
    }
    private void preencher(Estoque estoque, EstoqueDTO dados) {
        estoque.setNome(dados.getNome().trim());
        estoque.setLocalizacao(dados.getLocalizacao().trim());
    }
}
