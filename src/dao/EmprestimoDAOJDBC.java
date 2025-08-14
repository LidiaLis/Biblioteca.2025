/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import modelo.Livro;
import modelo.Emprestimo;


/**
 *
 * @author Usuario
 */
public class EmprestimoDAOJDBC implements EmprestimoDAO{    
    @Override
    public int inserir(Emprestimo emprestimo) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into emprestimo(id_livro, id_usuario, data_emprestimo, data_prevista, data_devoucao) ")
                .append("VALUES (?, ?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, emprestimo.getId_livro().getId_livro(),
                                                        emprestimo.getId_usuario().getId_usuario(),
                                                        emprestimo.getData_emprestimo(),
                                                        emprestimo.getData_prevista(),
                                                        emprestimo.getData_devolucao()
                                                        );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return linha;
    }

    @Override
    public int editar(Emprestimo emprestimo) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE emprestimo SET ")
                .append("id_livro = ?, ")
                .append("id_usuario = ?, ")
                .append("data_doacao = ?,  ")
                .append("data_prevista = ?,  ")
                .append("data_devolucao = ?,  ")
                .append("WHERE id_emprestimo = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, emprestimo.getId_livro().getId_livro(),
                                                        emprestimo.getId_usuario().getId_usuario(),
                                                        emprestimo.getData_emprestimo(),
                                                        emprestimo.getData_prevista(),
                                                        emprestimo.getData_devolucao()
                                                        );
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return linha; 
    }

    @Override
    public int apagar(int id_emprestimo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM emprestimo ")
                .append("WHERE id_emprestimo = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id_emprestimo);
        return linha;
    }

    @Override
    public List<Emprestimo> listar() {
        ResultSet rset;
        String select = "SELECT * FROM emprestimo ORDER BY id_emprestimo";
        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Emprestimo emprestimo = new Emprestimo();            
                emprestimo.setId_emprestimo(rset.getInt("id_emprestimo"));
                emprestimo.setData_emprestimo(rset.getDate("data_emprestimo"));
                emprestimo.setData_prevista(rset.getDate("data_prevista"));
                emprestimo.setData_devolucao(rset.getDate("data_devolucao"));
                
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rset.getInt("id_usuario"));
                emprestimo.setId_usuario(usuario);
                
                Livro livro = new Livro();
                livro.setId_livro(rset.getInt("id_livro"));
                emprestimo.setId_livro(livro);
                emprestimos.add(emprestimo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return emprestimos;
    }


    @Override
    public Emprestimo listar(int id_emprestimo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
}
