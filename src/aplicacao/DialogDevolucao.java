/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package aplicacao;

import dao.DAOGenerico;
import dao.DevolucaoDAO;
import dao.DevolucaoDAOJDBC;
import dao.EmprestimoDAO;
import dao.EmprestimoDAOJDBC;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Devolucao;
import modelo.Usuario;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import modelo.Emprestimo;
import modelo.Livro;


/**
 *
 * @author Lídia Lis
 */
public class DialogDevolucao extends javax.swing.JDialog {
    
   private Devolucao devolucaoSelecionado = null;
   private boolean salvouAlteracao = false; // ADICIONAR ESTA VARIÁVEL
    /**
     * Creates new form DialogInserir
     * @param parent
     * @param modal
     * @param idDevolucao
     */
    public DialogDevolucao(java.awt.Frame parent, boolean modal, Integer idDevolucao) {
    super(parent, modal);
    initComponents();
    setLocationRelativeTo(parent);
    carregarEmprestimos(); // Preenche o ComboBox com os empréstimos disponíveis
    if (idDevolucao != null) {
        DevolucaoDAO dao = new DevolucaoDAOJDBC();
        devolucaoSelecionado = dao.buscaPorId(idDevolucao);
        if (devolucaoSelecionado != null) {
            preencherCamposSeEdicao();
            btnInserirSalva.setText("Salvar Alterações");
        } else {
            JOptionPane.showMessageDialog(this, "Devolução não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        btnInserirSalva.setText("Inserir");
    }
    // Atualiza nome do leitor ao selecionar empréstimo
    cbEmprestimo.addActionListener(e -> atualizarNomeLeitor());
}
// ADICIONAR ESTE MÉTODO PÚBLICO:
    public boolean salvouAlteracao() {
        return salvouAlteracao;
    }
// MÉTODO ALTERADO: Carrega empréstimos no formato "ID - Nome do Leitor"
private void carregarEmprestimos() {
    try {
        EmprestimoDAO emprestimoDAO = new EmprestimoDAOJDBC();

        cbEmprestimo.removeAllItems();

        String sql = "SELECT e.*, u.nome, l.titulo " +
                     "FROM emprestimo e " +
                     "JOIN usuario u ON e.id_usuario = u.id_usuario " +
                     "JOIN livro l ON e.id_livro = l.id_livro " +
                     "WHERE e.id_emprestimo NOT IN (SELECT id_emprestimo FROM devolucao WHERE id_emprestimo IS NOT NULL) " +
                     "ORDER BY e.id_emprestimo";

        ResultSet rs = DAOGenerico.executarConsulta(sql);

        while (rs.next()) {
            Emprestimo emp = new Emprestimo();
            emp.setId_emprestimo(rs.getInt("id_emprestimo"));
            emp.setData_emprestimo(rs.getDate("data_emprestimo"));
            emp.setData_prevista(rs.getDate("data_prevista"));

            Usuario usuario = new Usuario();
            usuario.setId_usuario(rs.getInt("id_usuario"));
            usuario.setNome(rs.getString("nome"));
            emp.setId_usuario(usuario);

            Livro livro = new Livro();
            livro.setId_livro(rs.getInt("id_livro"));
            livro.setTitulo(rs.getString("titulo"));
            emp.setId_livro(livro);

            cbEmprestimo.addItem(emp); // adiciona o objeto, não a string
        }

    } catch (Exception e) {
        System.err.println("Erro ao carregar empréstimos: " + e.getMessage());
        JOptionPane.showMessageDialog(this,
            "Erro ao carregar empréstimos: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE);
    }
}

private void atualizarNomeLeitor() {
    Emprestimo emp = (Emprestimo) cbEmprestimo.getSelectedItem();
    if (emp != null && emp.getId_usuario() != null) {
        txtNome.setText(emp.getId_usuario().getNome());
    } else {
        txtNome.setText("");
    }
}

private void preencherCamposSeEdicao() {
    if (devolucaoSelecionado != null) {
        Emprestimo empSelecionado = devolucaoSelecionado.getId_emprestimo();

        for (int i = 0; i < cbEmprestimo.getItemCount(); i++) {
            Emprestimo emp = (Emprestimo) cbEmprestimo.getItemAt(i);
            if (emp.getId_emprestimo() == empSelecionado.getId_emprestimo()) {
                cbEmprestimo.setSelectedIndex(i);
                break;
            }
        }

        if (devolucaoSelecionado.getData_devolucao() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            ftxtData.setText(sdf.format(devolucaoSelecionado.getData_devolucao()));
        }

        atualizarNomeLeitor();
    }
}
// MÉTODO NOVO: Para obter o ID do empréstimo selecionado (usar ao salvar)
public Integer obterIdEmprestimoSelecionado() {
    Emprestimo emp = (Emprestimo) cbEmprestimo.getSelectedItem();
    return (emp != null) ? emp.getId_emprestimo() : null;
}    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ftxtData = new javax.swing.JFormattedTextField();
        btnInserirSalva = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cbEmprestimo = new javax.swing.JComboBox<>();
        txtNome = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 244));

        jPanel5.setBackground(new java.awt.Color(0, 153, 204));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        jLabel2.setText("Nome do Leitor: ");

        jLabel11.setText("Data de Devolução: ");

        try {
            ftxtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftxtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftxtDataActionPerformed(evt);
            }
        });

        btnInserirSalva.setText("Salvar");
        btnInserirSalva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirSalvaActionPerformed(evt);
            }
        });

        jLabel8.setText("ID do Emprestimo:");

        cbEmprestimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEmprestimoActionPerformed(evt);
            }
        });

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnInserirSalva)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cbEmprestimo, javax.swing.GroupLayout.Alignment.LEADING, 0, 74, Short.MAX_VALUE)
                                            .addComponent(ftxtData, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(71, 71, 71))
                                    .addComponent(txtNome)))
                            .addComponent(jLabel8))
                        .addGap(0, 32, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(ftxtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnInserirSalva)
                .addGap(53, 53, 53))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
    // Atualiza a combo de empréstimos
    DefaultComboBoxModel<Emprestimo> modeloEmprestimos = new DefaultComboBoxModel<>();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAOJDBC();

    for (Emprestimo emp : emprestimoDAO.listar()) {
        modeloEmprestimos.addElement(emp);
    }

    cbEmprestimo.setModel(modeloEmprestimos);

    // Se estiver em modo edição, seleciona o empréstimo correto
    if (devolucaoSelecionado != null) {
        for (int i = 0; i < modeloEmprestimos.getSize(); i++) {
            Emprestimo emp = modeloEmprestimos.getElementAt(i);
            if (emp.getId_emprestimo() == devolucaoSelecionado.getId_emprestimo().getId_emprestimo()) {
                cbEmprestimo.setSelectedIndex(i);
                txtNome.setText(emp.getId_usuario().getNome());
                break;
            }
        }
    } else {
        // Se for inserção, seleciona o primeiro empréstimo por padrão
        if (modeloEmprestimos.getSize() > 0) {
            cbEmprestimo.setSelectedIndex(0);
            Emprestimo emp = modeloEmprestimos.getElementAt(0);
            txtNome.setText(emp.getId_usuario().getNome());
        }
    }
    }//GEN-LAST:event_formWindowGainedFocus

    private void btnInserirSalvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirSalvaActionPerformed
      try {
            String dataTexto = ftxtData.getText().trim();

            if (dataTexto.isEmpty() || dataTexto.contains("_")) {
                JOptionPane.showMessageDialog(this, "Preencha a data de devolução corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date dataDevolucao;
            try {
                dataDevolucao = sdf.parse(dataTexto);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date hoje = new Date();
            if (dataDevolucao.after(hoje)) {
                JOptionPane.showMessageDialog(this, "A data de devolução não pode ser futura!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Emprestimo emprestimoSelecionado = (Emprestimo) cbEmprestimo.getSelectedItem();
            if (emprestimoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um empréstimo válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Livro livro = emprestimoSelecionado.getId_livro();
            Usuario usuario = emprestimoSelecionado.getId_usuario();

            DevolucaoDAO dao = new DevolucaoDAOJDBC();

            if (devolucaoSelecionado == null) {
                // INSERIR
                Devolucao novaDevolucao = new Devolucao();
                novaDevolucao.setId_livro(livro);
                novaDevolucao.setId_usuario(usuario);
                        
                // garantir que o objeto Emprestimo dentro da devolução tenha a data preenchida
            Emprestimo emp = emprestimoSelecionado;
            if (emp.getData_emprestimo() == null) {
                // busca completa no banco, só por segurança
                EmprestimoDAO empDAO = new EmprestimoDAOJDBC();
                emp = empDAO.buscaPorId(emprestimoSelecionado.getId_emprestimo());
            }

            novaDevolucao.setId_emprestimo(emp);
        
                        
                novaDevolucao.setData_devolucao(dataDevolucao);

                int sucesso = dao.inserir(novaDevolucao);
                if (sucesso > 0) {
                    salvouAlteracao = true; // MARCAR QUE SALVOU
                    JOptionPane.showMessageDialog(this, "Devolução registrada com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao registrar devolução.");
                }

            } else {
                // EDITAR
                devolucaoSelecionado.setId_emprestimo(emprestimoSelecionado);
                devolucaoSelecionado.setId_livro(livro);
                devolucaoSelecionado.setId_usuario(usuario);
                devolucaoSelecionado.setData_devolucao(dataDevolucao);

                int sucesso = dao.editar(devolucaoSelecionado);
                if (sucesso > 0) {
                    salvouAlteracao = true; // MARCAR QUE SALVOU
                    JOptionPane.showMessageDialog(this, "Devolução atualizada com sucesso!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar devolução.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro inesperado: " + e.getMessage());
        }
    }//GEN-LAST:event_btnInserirSalvaActionPerformed

    private void ftxtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftxtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftxtDataActionPerformed

    private void cbEmprestimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEmprestimoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEmprestimoActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>

        /* Create and display the dialog */
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInserirSalva;
    private javax.swing.JComboBox<Emprestimo> cbEmprestimo;
    private javax.swing.JFormattedTextField ftxtData;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
