
package modelo;

import java.util.Date;

public class Devolucao{
    private int id_devolucao;
    private Emprestimo id_emprestimo;
    private Livro id_livro;
    private Usuario id_usuario;
    private Date data_devolucao;

    public int getId_devolucao() {
        return id_devolucao;
    }

    public void setId_devolucao(int id_devolucao) {
        this.id_devolucao=id_devolucao;
    }
    
    public Emprestimo getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(Emprestimo id_emprestimo) {
        this.id_emprestimo =id_emprestimo;
    }
    
    public Livro getId_livro() {
        return id_livro;
    }

    public void setId_livro(Livro id_livro) {
        this.id_livro =id_livro;
    }
    
    public Usuario getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Usuario id_usuario) {
        this.id_usuario =id_usuario;
    }
    
     public Date getData_devolucao() {
        return data_devolucao;
    }
    public void setData_devolucao(Date data_devolucao){
        this.data_devolucao =  data_devolucao;
    }

    @Override
    public String toString() {
        return "Devolucao{" + "id=" + id_devolucao + ", id_emprestimo=" + id_emprestimo +", id_livro=" + id_livro +", id_usuario=" + id_usuario +  ", data_devolucao=" + data_devolucao + '}';
    }
    
    
}