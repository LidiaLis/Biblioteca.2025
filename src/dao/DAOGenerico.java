package dao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAOGenerico {
    
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
    Properties props = new Properties();
    
    try (InputStream input = DAOGenerico.class.getClassLoader().getResourceAsStream("config.properties")) {
        if (input == null) {
            throw new IOException("Arquivo config.properties não encontrado no classpath.");
        }
        props.load(input);
    } catch (IOException ex) {
        Logger.getLogger(DAOGenerico.class.getName()).log(Level.SEVERE, "Erro ao carregar config.properties", ex);
        throw new SQLException("Falha ao carregar propriedades de configuração.");
    }

    String url = props.getProperty("db.url");
    String user = props.getProperty("db.user");
    String password = props.getProperty("db.password");

    return DriverManager.getConnection(url, user, password);
}

    
     public static int executarComando(String query, Object... params) throws SQLException, ClassNotFoundException {
         int result;
        try (PreparedStatement sql = (PreparedStatement)  getConexao().prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                sql.setObject(i+1,params[i]);
            }   result = sql.executeUpdate();
        }
        return result;
     }
     
     public static ResultSet executarConsulta(String query, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement sql = (PreparedStatement)  getConexao().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            sql.setObject(i+1,params[i]);
        }
        return sql.executeQuery();
    }

}