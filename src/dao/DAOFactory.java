
package dao;

public class DAOFactory {
    public static UsuarioDAO criarUsuarioDAO() {
    return new UsuarioDAOJDBC();
    }
    public static LivroDAO criarLivroDAO() {
    return new LivroDAOJDBC();
    }
    public static EmprestimoDAO criarEmprestimoDAO() {
    return new EmprestimoDAOJDBC();
    }
    public static DevolucaoDAO criarDevolucaoDAO() {
    return new DevolucaoDAOJDBC();
    }
}