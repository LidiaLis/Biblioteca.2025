package dao;

import static dao.DAOGenerico.getConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Emprestimo;
import modelo.Emprestimo.StatusEmprestimo;
import modelo.Livro;
import modelo.Usuario;

public class EmprestimoDAOJDBC implements EmprestimoDAO {

    @Override
    public int inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_livro, id_usuario, data_emprestimo, data_prevista, data_devolucao, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        int linha = 0;

        try {
            linha = DAOGenerico.executarComando(sql,
                emprestimo.getId_livro().getId_livro(),
                emprestimo.getId_usuario().getId_usuario(),
                new java.sql.Date(emprestimo.getData_emprestimo().getTime()),
                new java.sql.Date(emprestimo.getData_prevista().getTime()),
                emprestimo.getData_devolucao() != null ? 
                    new java.sql.Date(emprestimo.getData_devolucao().getTime()) : null,
                emprestimo.getStatus().name() // Enum → String
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return linha;
    }
    
    @Override
    public Emprestimo buscaPorId(int idEmprestimo) {
        Emprestimo emprestimo = null;
        String sql = "SELECT * FROM emprestimo WHERE id_emprestimo = ?";

        try (Connection conn = getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmprestimo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                emprestimo = mapearEmprestimo(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar empréstimo por ID: " + e.getMessage());
        }

        return emprestimo;
    }
    
    @Override
    public int editar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET id_livro = ?, id_usuario = ?, data_emprestimo = ?, data_prevista = ?, data_devolucao = ?, status = ? "
                   + "WHERE id_emprestimo = ?";
        int linha = 0;

        try {
            linha = DAOGenerico.executarComando(sql,
                emprestimo.getId_livro().getId_livro(),
                emprestimo.getId_usuario().getId_usuario(),
                new java.sql.Date(emprestimo.getData_emprestimo().getTime()),
                new java.sql.Date(emprestimo.getData_prevista().getTime()),
                emprestimo.getData_devolucao() != null ? 
                    new java.sql.Date(emprestimo.getData_devolucao().getTime()) : null,
                emprestimo.getStatus().name(),
                emprestimo.getId_emprestimo()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return linha;
    }

    @Override
    public int apagar(int id_emprestimo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        String sql = "DELETE FROM emprestimo WHERE id_emprestimo = ?";
        return DAOGenerico.executarComando(sql, id_emprestimo);
    }
    
public List<Emprestimo> listarPorCampo(String campo, Object valor) {
    List<Emprestimo> emprestimos = new ArrayList<>();
    boolean filtrar = valor != null && !valor.toString().equalsIgnoreCase("Todos");

    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT e.id_emprestimo, e.data_emprestimo, e.data_prevista, e.data_devolucao, e.status, ")
              .append("l.id_livro, l.titulo, l.autor, l.genero, ")
              .append("u.id_usuario, u.nome, u.telefone, u.email ")
              .append("FROM emprestimo e ")
              .append("JOIN livro l ON e.id_livro = l.id_livro ")
              .append("JOIN usuario u ON e.id_usuario = u.id_usuario ");

    if (filtrar) {
        switch (campo) {
            case "titulo":
                sqlBuilder.append("WHERE l.titulo LIKE ? ");
                valor = "%" + valor + "%";
                break;
            case "usuario":
                sqlBuilder.append("WHERE u.nome LIKE ? ");
                valor = "%" + valor + "%";
                break;
            case "status":
                sqlBuilder.append("WHERE e.status = ? ");
                break;
            case "dataEmprestimo":
                sqlBuilder.append("WHERE DATE_FORMAT(e.data_emprestimo, '%Y-%m') = ? ");
                break;
            case "dataPrevista":
                sqlBuilder.append("WHERE DATE_FORMAT(e.data_prevista, '%Y-%m') = ? ");
                break;
            default:
                throw new IllegalArgumentException("Campo de filtro não suportado: " + campo);
        }
    }

    sqlBuilder.append("ORDER BY e.id_emprestimo");
    String sql = sqlBuilder.toString();

    try {
        ResultSet rs;

        if (filtrar) {
            rs = DAOGenerico.executarConsulta(sql, valor);
        } else {
            rs = DAOGenerico.executarConsulta(sql);
        }

        while (rs.next()) {
            // Usuário
            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setEmail(rs.getString("email"));

            // Livro
            Livro livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo"));
            livro.setAutor(rs.getString("autor"));
            livro.setGenero(rs.getString("genero"));

            // Empréstimo
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
            emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
            emprestimo.setData_prevista(rs.getDate("data_prevista"));
            emprestimo.setData_devolucao(rs.getDate("data_devolucao"));
            emprestimo.setStatus(StatusEmprestimo.valueOf(rs.getString("status")));
            emprestimo.setId_usuario(usuario);
            emprestimo.setId_livro(livro);

            emprestimos.add(emprestimo);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return emprestimos;
}

    @Override
    public List<Emprestimo> listar() {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo ORDER BY id_emprestimo";

        try {
            ResultSet rs = DAOGenerico.executarConsulta(sql);
            while (rs.next()) {
                emprestimos.add(mapearEmprestimo(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprestimos;
    }
    
    @Override
    public Emprestimo listar(int id_emprestimo) {
        String sql = "SELECT * FROM emprestimo WHERE id_emprestimo = ?";
        Emprestimo emprestimo = null;

        try {
            ResultSet rs = DAOGenerico.executarConsulta(sql, id_emprestimo);
            if (rs.next()) {
                emprestimo = mapearEmprestimo(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emprestimo;
    }

    private Emprestimo mapearEmprestimo(ResultSet rs) throws SQLException {
        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
        emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
        emprestimo.setData_prevista(rs.getDate("data_prevista"));
        emprestimo.setData_devolucao(rs.getDate("data_devolucao"));

        // status vem como String, converte para Enum
        String statusStr = rs.getString("status");
        if (statusStr != null) {
            emprestimo.setStatus(StatusEmprestimo.valueOf(statusStr.toUpperCase()));
        }

        // Buscar livro completo
        LivroDAO livroDAO = new LivroDAOJDBC();
        Livro livro = livroDAO.buscaPorId(rs.getInt("id_livro"));
        emprestimo.setId_livro(livro);

        // Buscar usuário completo
        UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();
        Usuario usuario = usuarioDAO.buscaPorId(rs.getInt("id_usuario"));
        emprestimo.setId_usuario(usuario);

        return emprestimo;
    }

    @Override
    public Date buscarDataEmprestimo(int idEmprestimo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
