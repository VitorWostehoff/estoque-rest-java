package br.com.estoque.service;

import br.com.estoque.dao.EstoqueDAO;
import br.com.estoque.dao.ProdutoDAO;
import br.com.estoque.dto.ProdutoDTO;
import br.com.estoque.exception.BusinessException;
import br.com.estoque.exception.ResourceNotFoundException;
import br.com.estoque.model.Estoque;
import br.com.estoque.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoDAO produtoDAO;
    private final EstoqueDAO estoqueDAO;

    public ProdutoService(ProdutoDAO produtoDAO, EstoqueDAO estoqueDAO) {
        this.produtoDAO = produtoDAO;
        this.estoqueDAO = estoqueDAO;
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoDAO.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(int id) {
        return produtoDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto com ID " + id + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new BusinessException("Informe um nome para buscar os produtos.");
        }
        return produtoDAO.findByNomeContainingIgnoreCase(nome.trim());
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorFaixaDePreco(double minimo, double maximo) {
        if (minimo < 0 || maximo < 0) {
            throw new BusinessException("Os preços não podem ser negativos.");
        }
        if (minimo > maximo) {
            throw new BusinessException("O preço mínimo não pode ser maior que o preço máximo.");
        }
        return produtoDAO.findByPrecoBetween(minimo, maximo);
    }

    @Transactional
    public Produto cadastrar(ProdutoDTO dados) {
        Produto produto = new Produto();
        preencherProduto(produto, dados);
        return produtoDAO.save(produto);
    }

    @Transactional
    public Produto atualizar(int id, ProdutoDTO dados) {
        Produto produto = buscarPorId(id);
        preencherProduto(produto, dados);
        return produtoDAO.save(produto);
    }

    @Transactional
    public void excluir(int id) {
        Produto produto = buscarPorId(id);
        produtoDAO.delete(produto);
    }

    private void preencherProduto(Produto produto, ProdutoDTO dados) {
        Estoque estoque = estoqueDAO.findById(dados.getEstoqueId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Estoque com ID " + dados.getEstoqueId() + " não encontrado."));

        produto.setNome(dados.getNome().trim());
        produto.setDescricao(dados.getDescricao());
        produto.setPreco(dados.getPreco());
        produto.setQuantidade(dados.getQuantidade());
        produto.setQuantidadeMinima(dados.getQuantidadeMinima());
        produto.setEstoque(estoque);
    }
}