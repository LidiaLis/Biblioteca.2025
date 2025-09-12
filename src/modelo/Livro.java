package modelo;

import java.util.Date;

public class Livro{
    private int id_livro;
    private Usuario id_doador;
    private String titulo;
    private String autor;
    private String genero;
    private Date data_doacao;
    private boolean disponivel;

     public int getId_livro() {
        return id_livro;
    }

    public void setId_livro(int id_livro) {
        this.id_livro=id_livro;
    }
    
    public Usuario getId_doador() {
        return id_doador;
    }

    public void setId_doador(Usuario id_doador) {
        this.id_doador =id_doador;
    }
    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }
    
    public Date getData_doacao() {
        return data_doacao;
    }

    public void setData_doacao(Date data_doacao){
        this.data_doacao = data_doacao;
    }
    
    public boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    

    public String getDescricaoCompleta() {
        return "Livro{" + "id=" + id_livro + ", id_doador=" + id_doador + ", titulo=" + titulo + ", autor=" + autor + ", genero=" + genero + ", data_doacao=" + data_doacao + ", disponivel=" + disponivel +  '}';
    }
    
    @Override
    public String toString(){
        return this.titulo;
    }
}