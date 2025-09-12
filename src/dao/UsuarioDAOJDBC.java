/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import static dao.DAOGenerico.getConexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Usuario;

/**
 *
 * @author Lídia Lis
 */
public class UsuarioDAOJDBC implements UsuarioDAO{  
    
    public boolean telefoneExiste(String telefone) {
    String sql = "SELECT COUNT(*) FROM usuario WHERE telefone = ?";
    try (PreparedStatement stmt = getConexao().prepareStatement(sql)) {
        stmt.setString(1, telefone);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
    
    @Override
    public Usuario buscaPorId(int idUsuario) {
    Usuario usuario = null;

    String sql = "SELECT * FROM usuario WHERE id_usuario = ?";

    try (Connection conn = getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setEmail(rs.getString("email"));      
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar usuário por ID: " + e.getMessage());
    }

    return usuario;
}

    @Override
    public int inserir(Usuario usuario) {
        if (telefoneExiste(usuario.getTelefone())) {
        JOptionPane.showMessageDialog(null, "Telefone já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        return 0;
    }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into usuario(nome, telefone, email) ")
                .append("VALUES (?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, usuario.getNome(),
                                                        usuario.getTelefone(),
                                                        usuario.getEmail()
                                                        );
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return linha;
    }

    @Override
    public int editar(Usuario usuario) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE usuario SET ")
                .append("nome = ?, ")
                .append("telefone= ?, ")
                .append("email = ? ")
                .append("WHERE id_usuario = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, usuario.getNome(), 
                                                        usuario.getTelefone(),
                                                        usuario.getEmail(),
                                                        usuario.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return linha; 
    }

    @Override
    public int apagar(int id_usuario) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
    Connection con = getConexao();
    PreparedStatement stmt = null;
    int linhasAfetadas = 0;

    try {
        con.setAutoCommit(false);

        // 1. Apagar empréstimos do usuário
        stmt = con.prepareStatement("DELETE FROM emprestimo WHERE id_usuario = ?");
        stmt.setInt(1, id_usuario);
        stmt.executeUpdate();
        stmt.close();

        // 2. Apagar livros cadastrados pelo usuário
        stmt = con.prepareStatement("DELETE FROM livro WHERE id_usuario = ?");
        stmt.setInt(1, id_usuario);
        stmt.executeUpdate();
        stmt.close();

        // 3. Apagar o próprio usuário
        stmt = con.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
        stmt.setInt(1, id_usuario);
        linhasAfetadas = stmt.executeUpdate();
        stmt.close();

        con.commit();

    } catch (SQLException e) {
        con.rollback();
        throw e;
    } finally {
        if (stmt != null) stmt.close();
        if (con != null) con.close();
    }

    return linhasAfetadas;
}
public List<Usuario> listarPorNome(String textoDigitado) {
    List<Usuario> usuarios = new ArrayList<>();

    String sql = "SELECT id_usuario, nome, telefone, email " +
                 "FROM usuario " +
                 "WHERE LOWER(nome) LIKE ? " +
                 "ORDER BY nome";

    try {
        // Prepara o parâmetro com % para busca parcial e converte para minúsculo
        String parametro = "%" + textoDigitado.toLowerCase() + "%";
        ResultSet rs = DAOGenerico.executarConsulta(sql, parametro);

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setEmail(rs.getString("email"));
            usuarios.add(usuario);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return usuarios;
} 
    @Override
    public List<Usuario> listar() {
        ResultSet rset;
        String select = "SELECT * FROM usuario ORDER BY id_usuario";
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rset.getInt("id_usuario"));
                usuario.setNome(rset.getString("nome"));
                usuario.setTelefone(rset.getString("telefone"));
                usuario.setEmail(rset.getString("email"));
                usuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return usuarios;
    }

    /**
     *
     * @param id_usuario
     * @return
     */
    @Override
    public Usuario listar(int id_usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
