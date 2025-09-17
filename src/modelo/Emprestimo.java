
package modelo;

import java.util.Date;



public class Emprestimo{
    private int id_emprestimo;
    private Livro id_livro;
    private Usuario id_usuario;
    private Date data_emprestimo;
    private Date data_prevista;
    private Date data_devolucao;

    public void setLivro(Livro l) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

     public int getId_emprestimo() {
        return id_emprestimo;
    }

    public void setId_emprestimo(int id_emprestimo) {
        this.id_emprestimo=id_emprestimo;
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
    
    public Date getData_emprestimo() {
        return data_emprestimo;
    }
    public void setData_emprestimo(Date data_emprestimo){
        this.data_emprestimo = data_emprestimo;
    }
  
     public Date getData_prevista() {
        return data_prevista;
    }
    public void setData_prevista(Date data_prevista){
        this.data_prevista = data_prevista;
    }
    
    public Date getData_devolucao() {
        return data_devolucao;
    }
    public void setData_devolucao(Date data_devolucao){
        this.data_devolucao = data_devolucao;
    }

    public void setUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public enum StatusEmprestimo {
        EMPRESTADO,
        DEVOLVIDO,
        ATRASADO;

        // Método utilitário seguro para converter String para Enum
        public static StatusEmprestimo fromString(String s) {
            if (s == null) return null;
            try {
                return StatusEmprestimo.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    private StatusEmprestimo status;

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }
    
    public String getToString() {
        return "Emprestimo{" + "id=" + id_emprestimo + ", id_livro=" + id_livro +", id_usuario=" + id_usuario + ","
        + " data_emprestimo=" + data_emprestimo + ", data_prevista=" + data_prevista + ", data_devolucao=" + data_devolucao + ", status=" + status +  '}';
    }
    
}