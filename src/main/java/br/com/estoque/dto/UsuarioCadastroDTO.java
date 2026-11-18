package br.com.estoque.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
public class UsuarioCadastroDTO {
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;
    @Pattern(regexp = "ADMIN|USER", message = "A permissão deve ser ADMIN ou USER.")
    private String role;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
