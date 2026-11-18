package br.com.estoque.dto;
public class UsuarioDTO {
    private Long id;
    private String email;
    private String role;
    public UsuarioDTO() {}
    public UsuarioDTO(Long id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
