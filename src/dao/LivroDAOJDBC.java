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

/**
 *
 * @author Usuario
 */
public class LivroDAOJDBC implements LivroDAO{    
    @Override
    public int inserir(Livro livro) {
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder
            .append("insert into livro(id_doador, titulo, autor, genero, data_doacao, disponivel) ")
            .append("VALUES (?, ?, ?, ?, ?, ?)");
 
    String insert = sqlBuilder.toString();
    int linha = 0;
    try {  
        // Convertendo java.util.Date para java.sql.Date para o banco
        java.sql.Date sqlDate = new java.sql.Date(livro.getData_doacao().getTime());

        linha = DAOGenerico.executarComando(insert,
            livro.getId_doador().getId_usuario(),
            livro.getTitulo(),
            livro.getAutor(),
            livro.getGenero(),
            sqlDate,
            livro.getDisponivel()
                
        );
    } catch (Exception e) {
        e.printStackTrace();
    }

    return linha;
}


    @Override
    public int editar(Livro livro) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE livro SET ")
                .append("id_doador = ?, ")
                .append("titulo= ?, ")
                .append("autor = ?,  ")
                .append("genero = ?, ")
                .append("data_doacao = ?,  ")
                .append("disponivel c")
                .append("WHERE id_livro = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, livro.getId_doador().getId_usuario(),
                                                        livro.getTitulo(),
                                                        livro.getAutor(),
                                                        livro.getGenero(),
                                                        livro.getData_doacao(),
                                                        livro.getDisponivel());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return linha; 
    }

    @Override
    public int apagar(int id_livro) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM livro ")
                .append("WHERE id_livro = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id_livro);
        return linha;
    }

    @Override
    public List<Livro> listar() {
        ResultSet rset;
        String select = "SELECT l.id_livro, l.id_doador, l.titulo, l.autor, l.genero, l.disponivel, l.data_doacao, u.id_usuario, u.nome, u.telefone, u.email \n" +
                        "FROM livro l\n" +
                        "JOIN usuario u ON l.id_doador = u.id_usuario\n" +
                        "ORDER BY l.id_livro";
        List<Livro> livros = new ArrayList<Livro>();

        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rset.getInt("id_doador"));
                usuario.setNome(rset.getString("nome"));
                
                Livro livro = new Livro();            
                livro.setId_livro(rset.getInt("id_livro"));
                livro.setTitulo(rset.getString("titulo"));
                livro.setAutor(rset.getString("autor"));
                livro.setGenero(rset.getString("genero"));
                livro.setData_doacao(rset.getDate("data_doacao"));
                livro.setDisponivel(rset.getBoolean("disponivel"));
                livro.setId_doador(usuario);
                livros.add(livro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return livros;
    }


    @Override
    public Livro listar(int id_livro) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
}
