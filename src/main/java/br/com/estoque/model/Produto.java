package br.com.estoque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private int id;

    @Column(name = "nm_produto", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_produto", length = 500)
    private String descricao;

    @Column(name = "vl_preco", nullable = false)
    private double preco;

    @Column(name = "qt_produto", nullable = false)
    private int quantidade;

    @Column(name= "qt_minima", nullable = false)
    private int quantidadeMinima;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("produtos")
    @JoinColumn(name = "id_estoque", nullable = false)
    private Estoque estoque;

    public Produto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }
}
