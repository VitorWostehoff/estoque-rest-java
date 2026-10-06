package br.com.estoque.dto;

import jakarta.validation.constraints.NotBlank;

public class EstoqueDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String localizacao;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}
