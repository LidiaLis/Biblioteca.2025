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

/**
 *
 * @author Usuario
 */
public class UsuarioDAOJDBC implements UsuarioDAO{    
    @Override
    public int inserir(Usuario usuario) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into usuario(nome, telefone, email, tipo_usuario) ")
                .append("VALUES (?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, usuario.getNome(),
                                                        usuario.getTelefone(),
                                                        usuario.getEmail(),
                                                        usuario.getTipo_usuario()
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
                .append("email = ?, ")
                .append("tipo_usuario = ?, ")
                .append("WHERE id_usuario = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, usuario.getNome(), 
                                                        usuario.getTelefone(),
                                                        usuario.getEmail(),
                                                        usuario.getTipo_usuario(),
                                                        usuario.getId_usuario());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return linha; 
    }

    @Override
    public int apagar(int id_usuario) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM usuario ")
                .append("WHERE id_usuario = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id_usuario);
        return linha;
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
                usuario.setTipo_usuario(rset.getString("tipo_usuario"));
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
