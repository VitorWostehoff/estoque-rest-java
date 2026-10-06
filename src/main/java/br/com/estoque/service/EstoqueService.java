package br.com.estoque.service;

import br.com.estoque.dao.EstoqueDAO;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {
    private final EstoqueDAO estoqueDAO;

    public EstoqueService(EstoqueDAO estoqueDAO) {
        this.estoqueDAO = estoqueDAO;
    }
}
