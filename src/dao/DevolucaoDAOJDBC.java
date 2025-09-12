package dao;

import static dao.DAOGenerico.getConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import modelo.Devolucao;
import modelo.Emprestimo;
import modelo.Livro;
import modelo.Usuario;

public class DevolucaoDAOJDBC implements DevolucaoDAO {
    
    @Override
    public Date buscarDataEmprestimo(int idEmprestimo) {
    String sql = "SELECT data_emprestimo FROM emprestimo WHERE id_emprestimo = ?";
    Date data = null;

    try (Connection conn = getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idEmprestimo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            data = rs.getDate("data_emprestimo");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return data;
    }
    
public Devolucao buscaPorId(int idDevolucao) {
    Devolucao devolucao = null;
    String sql = "SELECT d.id_devolucao, d.data_devolucao, e.id_emprestimo, e.data_emprestimo, "
                 + "l.id_livro, l.titulo, u.id_usuario, u.nome "
                 + "FROM devolucao d "
                 + "JOIN emprestimo e ON d.id_emprestimo = e.id_emprestimo "
                 + "JOIN livro l ON d.id_livro = l.id_livro "
                 + "JOIN usuario u ON d.id_usuario = u.id_usuario "
                 + "WHERE d.id_devolucao = ?";

    try (Connection conn = getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idDevolucao);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            devolucao = new Devolucao();
            devolucao.setId_devolucao(rs.getInt("id_devolucao"));
            devolucao.setData_devolucao(rs.getDate("data_devolucao"));

            // Criando os objetos relacionados diretamente da consulta
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
            emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
            devolucao.setId_emprestimo(emprestimo);

            Livro livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo"));
            devolucao.setId_livro(livro);

            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            devolucao.setId_usuario(usuario);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar devolução por ID: " + e.getMessage());
    }

    return devolucao;
}
    
    @Override
    public int inserir(Devolucao devolucao) {
    String sql = "INSERT INTO devolucao (id_emprestimo, id_livro, id_usuario, data_devolucao) VALUES (?, ?, ?, ?)";
    int linha = 0;

    try {

        linha = DAOGenerico.executarComando(sql,
            devolucao.getId_emprestimo().getId_emprestimo(),
            devolucao.getId_livro().getId_livro(),
            devolucao.getId_usuario().getId_usuario(),
            new java.sql.Date(devolucao.getData_devolucao().getTime())
        );
    } catch (Exception e) {
        e.printStackTrace();
    }

    return linha;
}
  
 @Override
public int editar(Devolucao devolucao) {
    String sql = "UPDATE devolucao SET id_emprestimo = ?, id_livro = ?, id_usuario = ?, data_devolucao = ? WHERE id_devolucao = ?";
    int linha = 0;

    try {
        linha = DAOGenerico.executarComando(sql,
            devolucao.getId_emprestimo().getId_emprestimo(),
            devolucao.getId_livro().getId_livro(),
            devolucao.getId_usuario().getId_usuario(),
            new java.sql.Date(devolucao.getData_devolucao().getTime()),
            devolucao.getId_devolucao()
        );
    } catch (Exception e) {
        e.printStackTrace();
    }

    return linha;
}   
    
@Override
public int apagar(int id_devolucao) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
    String sql = "DELETE FROM devolucao WHERE id_devolucao = ?";
    return DAOGenerico.executarComando(sql, id_devolucao);
}
@Override
public List<Devolucao> listar() {
    List<Devolucao> devolucoes = new ArrayList<>();
    String sql = "SELECT * FROM devolucao ORDER BY id_devolucao";

    try {
        ResultSet rs = DAOGenerico.executarConsulta(sql);
        while (rs.next()) {
            devolucoes.add(mapearDevolucao(rs));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return devolucoes;
}

public List<Devolucao> listarPorCampo(String campo, Object valor) {
    List<Devolucao> devolucoes = new ArrayList<>();
    boolean filtrar = valor != null && !valor.toString().equalsIgnoreCase("Todos");
    
    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT d.*, e.data_emprestimo, e.data_prevista, l.titulo, u.nome ")
              .append("FROM devolucao d ")
              .append("JOIN emprestimo e ON d.id_emprestimo = e.id_emprestimo ")
              .append("JOIN livro l ON d.id_livro = l.id_livro ")
              .append("JOIN usuario u ON d.id_usuario = u.id_usuario ");
    
    boolean filtroMesAno = false;
    
    if (filtrar) {
        switch (campo.toLowerCase()) {
            case "titulo":
                sqlBuilder.append("WHERE l.titulo LIKE ? ");
                valor = "%" + valor + "%";
                break;
            case "leitor":
                sqlBuilder.append("WHERE u.nome LIKE ? ");
                valor = "%" + valor + "%";
                break;
            case "data_emprestimo":
                sqlBuilder.append("WHERE MONTH(e.data_emprestimo) = ? AND YEAR(e.data_emprestimo) = ? ");
                filtroMesAno = true;
                break;
            case "data_devolucao":
                sqlBuilder.append("WHERE MONTH(d.data_devolucao) = ? AND YEAR(d.data_devolucao) = ? ");
                filtroMesAno = true;
                break;
            default:
                sqlBuilder.append("WHERE d.").append(campo).append(" = ? ");
                break;
        }
    }
    
    sqlBuilder.append("ORDER BY d.id_devolucao");
    
    try {
        ResultSet rs;
        
        if (filtroMesAno) {
            // Parsear MM/YYYY
            String[] partes = valor.toString().split("/");
            int mes = Integer.parseInt(partes[0]);
            int ano = Integer.parseInt(partes[1]);
            rs = DAOGenerico.executarConsulta(sqlBuilder.toString(), mes, ano);
        } else {
            rs = DAOGenerico.executarConsulta(sqlBuilder.toString(), valor);
        }
        
        while (rs.next()) {
            Devolucao devolucao = new Devolucao();
            devolucao.setId_devolucao(rs.getInt("id_devolucao"));
            devolucao.setData_devolucao(rs.getDate("data_devolucao"));
            
            // Emprestimo
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
            emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
            emprestimo.setData_prevista(rs.getDate("data_prevista"));
            devolucao.setId_emprestimo(emprestimo);
            
            // Livro
            Livro livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo"));
            devolucao.setId_livro(livro);
            
            // Usuario
            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            devolucao.setId_usuario(usuario);
            
            devolucoes.add(devolucao);
        }
        
    } catch (Exception e) {
        System.err.println("Erro na consulta: " + e.getMessage());
        e.printStackTrace();
    }
    
    return devolucoes;
}

    @Override
    public Devolucao listar(int id_devolucao) {
    String sql = "SELECT * FROM devolucao WHERE id_devolucao = ?";
    Devolucao devolucao = null;

    try {
        ResultSet rs = DAOGenerico.executarConsulta(sql, id_devolucao);
        if (rs.next()) {
            devolucao = mapearDevolucao(rs);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return devolucao;
}

private Devolucao mapearDevolucao(ResultSet rs) throws SQLException {
    Devolucao devolucao = new Devolucao();

    Emprestimo emprestimo = new Emprestimo();
    emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
    Livro livro = new Livro();
    livro.setId_livro(rs.getInt("id_livro"));

    Usuario usuario = new Usuario();
    usuario.setId_usuario(rs.getInt("id_usuario"));

    devolucao.setId_devolucao(rs.getInt("id_devolucao"));
    devolucao.setId_emprestimo(emprestimo);
    devolucao.setId_livro(livro);
    devolucao.setId_usuario(usuario);
    devolucao.setData_devolucao(rs.getDate("data_devolucao"));

    return devolucao;
} 
}