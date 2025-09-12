package modelo;

public class Usuario{
    private int id_usuario;
    private String nome;
    private String telefone;
    private String email;
    private String tipo_usuario;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario =id_usuario;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    
    public String getDescricaoCompleta() {
    return "Usuario{" + "id=" + id_usuario + ", nome=" + nome + ", telefone=" + telefone +", email=" + email + '}';
    }
    @Override
    public String toString() {
        return nome;
    }
        
}