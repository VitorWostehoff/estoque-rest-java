package br.com.estoque.service;

import br.com.estoque.dao.ProdutoDAO;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private final ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }
}
