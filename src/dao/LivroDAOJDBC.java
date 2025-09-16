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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;
import modelo.Livro;

/**
 *
 * @author Lídia Lis
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
    public Livro buscaPorId(int idLivro) {
    Livro livro = null;

    String sql = "SELECT * FROM livro WHERE id_livro = ?";

    try (Connection conn = getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idLivro);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setGenero(rs.getString("genero"));
            livro.setData_doacao(rs.getDate("data_doacao"));
            livro.setDisponivel(rs.getBoolean("disponivel"));

            // Buscar o doador (usuário) pelo ID
            int idDoador = rs.getInt("id_doador");
            UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();
            Usuario doador = usuarioDAO.buscaPorId(idDoador);
            livro.setId_doador(doador);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar livro por ID: " + e.getMessage());
    }

    return livro;
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
                .append("disponivel = ?  ")
                .append("WHERE id_livro = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, livro.getId_doador().getId_usuario(),
                                                        livro.getTitulo(),
                                                        livro.getAutor(),
                                                        livro.getGenero(),
                                                        new java.sql.Date(livro.getData_doacao().getTime()), 
                                                        livro.getDisponivel(),
                                                        livro.getId_livro());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return linha; 
    }

    @Override
    public int apagar(int id_livro) throws ClassNotFoundException, SQLException {
        int linhasAfetadas = 0;

        try (Connection conn = getConexao()) {
            // Verifica se o livro tem empréstimos
            String verificaSql = "SELECT COUNT(*) FROM emprestimo WHERE id_livro = ?";
            try (PreparedStatement stmt = conn.prepareStatement(verificaSql)) {
                stmt.setInt(1, id_livro);
                ResultSet rs = stmt.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // Tem empréstimos → pergunta ao usuário
                    int confirmacao = javax.swing.JOptionPane.showConfirmDialog(
                            null,
                            "Este livro possui empréstimos vinculados.\n" +
                            "Se confirmar, TODOS os empréstimos relacionados serão apagados.\n\n" +
                            "Deseja realmente continuar?",
                            "Confirmação de Exclusão",
                            javax.swing.JOptionPane.YES_NO_OPTION,
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmacao != javax.swing.JOptionPane.YES_OPTION) {
                        return 0; // usuário desistiu
                    }

                    // Apaga os empréstimos vinculados
                    String sqlEmprestimo = "DELETE FROM emprestimo WHERE id_livro = ?";
                    DAOGenerico.executarComando(sqlEmprestimo, id_livro);
                }
            }

            // Agora apaga o livro
            String sqlLivro = "DELETE FROM livro WHERE id_livro = ?";
            linhasAfetadas = DAOGenerico.executarComando(sqlLivro, id_livro);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return linhasAfetadas;
    }
    
  public List<Livro> listarPorCampo(String campo, Object valor) {
    List<Livro> livros = new ArrayList<>();
    boolean filtrar = valor != null && !valor.toString().equalsIgnoreCase("Todos");

    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT l.id_livro, l.id_doador, l.titulo, l.autor, l.genero, l.disponivel, l.data_doacao, ")
              .append("u.id_usuario, u.nome, u.telefone, u.email ")
              .append("FROM livro l ")
              .append("JOIN usuario u ON l.id_doador = u.id_usuario ");

    if (filtrar) {
        switch (campo) {
            case "doador":
                sqlBuilder.append("WHERE u.nome LIKE ? ");
                valor = "%" + valor + "%";
                break;
            case "disponivel":
                sqlBuilder.append("WHERE l.disponivel = ? ");
                break;
            default:
                sqlBuilder.append("WHERE l.").append(campo).append(" LIKE ? ");
                valor = "%" + valor + "%";
                break;
        }
    }

    sqlBuilder.append("ORDER BY l.id_livro");
    String sql = sqlBuilder.toString();

    try {
        ResultSet rs;

        if (filtrar) {
            if (campo.equals("disponivel") && valor instanceof String) {
                boolean disponivel = valor.equals("Sim");
                rs = DAOGenerico.executarConsulta(sql, disponivel);
            } else {
                rs = DAOGenerico.executarConsulta(sql, valor);
            }
        } else {
            rs = DAOGenerico.executarConsulta(sql);
        }

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_doador"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setEmail(rs.getString("email"));

            Livro livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setGenero(rs.getString("genero"));
            livro.setData_doacao(rs.getDate("data_doacao"));
            livro.setDisponivel(rs.getBoolean("disponivel"));
            livro.setId_doador(usuario);

            livros.add(livro);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return livros;
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
    String select = "SELECT l.id_livro, l.id_doador, l.titulo, l.autor, l.genero, l.disponivel, l.data_doacao, " +
                    "u.nome, u.telefone, u.email " +
                    "FROM livro l " +
                    "JOIN usuario u ON l.id_doador = u.id_usuario " +
                    "WHERE l.id_livro = ?";
    Livro livro = null;
    try {
        ResultSet rset = DAOGenerico.executarConsulta(select, id_livro);
        if (rset.next()) {
            Usuario usuario = new Usuario();
            usuario.setId_usuario(rset.getInt("id_doador"));
            usuario.setNome(rset.getString("nome"));
            usuario.setTelefone(rset.getString("telefone"));
            usuario.setEmail(rset.getString("email"));

            livro = new Livro();
            livro.setId_livro(rset.getInt("id_livro"));
            livro.setTitulo(rset.getString("titulo"));
            livro.setAutor(rset.getString("autor"));
            livro.setGenero(rset.getString("genero"));
            livro.setData_doacao(rset.getDate("data_doacao"));
            livro.setDisponivel(rset.getBoolean("disponivel"));
            livro.setId_doador(usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(LivroDAOJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    return livro;
}
  
}
