package br.com.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EstoqueDTO {
    @NotBlank(message = "O nome do estoque é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "A localização do estoque é obrigatória.")
    @Size(max = 150, message = "A localização deve ter no máximo 150 caracteres.")
    private String localizacao;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}
