package dao;

import java.util.List;
import modelo.Usuario;
import java.sql.*;

public interface UsuarioDAO {
    public int inserir(Usuario usuario);
    public int editar(Usuario usuario);
    public int apagar(int id_usuario) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public Usuario buscaPorId(int idUsuario);
    public List<Usuario> listarPorNome(String nome);    
    public List<Usuario> listar();
    public Usuario listar(int id_usuario);
}