package br.com.estoque.dao;

import br.com.estoque.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EstoqueDAO extends JpaRepository<Estoque, Long> {
    List<Estoque> findByNomeContainingIgnoreCase(String nome);
    List<Estoque> findByLocalizacaoContainingIgnoreCase(String localizacao);
}
