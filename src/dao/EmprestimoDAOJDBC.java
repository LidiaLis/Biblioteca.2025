package dao;

import static dao.DAOGenerico.getConexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Emprestimo;
import modelo.Livro;
import modelo.Usuario;

public class EmprestimoDAOJDBC implements EmprestimoDAO {

    @Override
    public int inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (id_livro, id_usuario, data_emprestimo, data_prevista) VALUES (?, ?, ?, ?)";
        int linha = 0;

        try {
            linha = DAOGenerico.executarComando(sql,
                emprestimo.getId_livro().getId_livro(),
                emprestimo.getId_usuario().getId_usuario(),
                new java.sql.Date(emprestimo.getData_emprestimo().getTime()),
                new java.sql.Date(emprestimo.getData_prevista().getTime())
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
            emprestimo = new Emprestimo();
            emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
            emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
            emprestimo.setData_prevista(rs.getDate("data_prevista"));

            // Buscar livro
            int idLivro = rs.getInt("id_livro");
            LivroDAO livroDAO = new LivroDAOJDBC();
            Livro livro = livroDAO.buscaPorId(idLivro);
            emprestimo.setId_livro(livro);

            // Buscar usuário
            int idUsuario = rs.getInt("id_usuario");
            UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();
            Usuario usuario = usuarioDAO.buscaPorId(idUsuario);
            emprestimo.setId_usuario(usuario);
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Erro ao buscar empréstimo por ID: " + e.getMessage());
    }

    return emprestimo;
    }
    
    @Override
    public int editar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET id_livro = ?, id_usuario = ?, data_emprestimo = ?, data_prevista = ? WHERE id_emprestimo = ?";
        int linha = 0;

        try {
            linha = DAOGenerico.executarComando(sql,
                emprestimo.getId_livro().getId_livro(),
                emprestimo.getId_usuario().getId_usuario(),
                new java.sql.Date(emprestimo.getData_emprestimo().getTime()),
                new java.sql.Date(emprestimo.getData_prevista().getTime()),
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

    public List<Emprestimo> listarPorCampo(String campo, Object valor) {
    List<Emprestimo> emprestimos = new ArrayList<>();
    boolean filtrar = valor != null && !valor.toString().equalsIgnoreCase("Todos");

    StringBuilder sqlBuilder = new StringBuilder();
    sqlBuilder.append("SELECT e.*, l.titulo AS titulo_livro, u.nome AS nome_leitor ")
              .append("FROM emprestimo e ")
              .append("JOIN livro l ON e.id_livro = l.id_livro ")
              .append("JOIN usuario u ON e.id_usuario = u.id_usuario ");

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
            case "data_prevista":
                sqlBuilder.append("WHERE MONTH(e.").append(campo).append(") = ? AND YEAR(e.").append(campo).append(") = ? ");
                filtroMesAno = true;
                break;
            default:
                sqlBuilder.append("WHERE e.").append(campo).append(" = ? ");
                break;
        }
    }

    sqlBuilder.append("ORDER BY e.id_emprestimo");

    try {
        ResultSet rs;
        if (filtroMesAno) {
    // Parsear a string "08/2025" para mês e ano
            String[] partes = valor.toString().split("/");
            int mes = Integer.parseInt(partes[0]);
            int ano = Integer.parseInt(partes[1]);
            rs = DAOGenerico.executarConsulta(sqlBuilder.toString(), mes, ano);
        } else {
            rs = DAOGenerico.executarConsulta(sqlBuilder.toString(), valor);
        }

        while (rs.next()) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
            emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
            emprestimo.setData_prevista(rs.getDate("data_prevista"));

            Livro livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo_livro"));
            emprestimo.setId_livro(livro);

            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome_leitor"));
            emprestimo.setId_usuario(usuario);

            emprestimos.add(emprestimo);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return emprestimos;
}

    private Emprestimo mapearEmprestimo(ResultSet rs) throws SQLException {
    Emprestimo emprestimo = new Emprestimo();

    emprestimo.setId_emprestimo(rs.getInt("id_emprestimo"));
    emprestimo.setData_emprestimo(rs.getDate("data_emprestimo"));
    emprestimo.setData_prevista(rs.getDate("data_prevista"));

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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}