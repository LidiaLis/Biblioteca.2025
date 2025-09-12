package aplicacao;

/**
 *
 * @author Lídia Lis
 */

public class Biblioteca extends javax.swing.JFrame {

    public Biblioteca() {
        initComponents(); // gerado automaticamente
        setLocationRelativeTo(null); // centraliza na tela (opcional)
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FrmPrincipal1().setVisible(true); // Aqui inicia a tela
        });
    }

    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

