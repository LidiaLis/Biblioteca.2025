/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import dao.DAOFactory;
import dao.UsuarioDAO;
import dao.UsuarioDAOJDBC;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import modelo.Usuario;

/**
 * @author Usuario
 */
public class FrmUsuario extends javax.swing.JFrame {
    
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
    DefaultTableModel modelo = null;
            
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmUsuario.class.getName());

    /**
     * Creates new form FrmUsuario
     */
    public FrmUsuario() {
        initComponents();
        modelo = (DefaultTableModel) tblUsuario.getModel();
        preencherTabela();
        atualizarTabela();
    }

    private void atualizarTabela(String campo, Object valor) {
    DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
    model.setRowCount(0);

    UsuarioDAOJDBC dao = new UsuarioDAOJDBC();
    List<Usuario> usuarios = dao.listarPorCampo(campo, valor);

    for (Usuario usuario : usuarios) {
        model.addRow(new Object[]{
            livro.getId_livro(),
            livro.getId_doador().getNome(), // agora mostra o nome do doador
            livro.getTitulo(),
            livro.getAutor(),
            livro.getGenero(),
            livro.getData_doacao(),
            livro.getDisponivel()
        });
    }
    }
    private void atualizarTabela() {
    DefaultTableModel model = (DefaultTableModel) tblUsuario.getModel();
    model.setRowCount(0);

    UsuarioDAO dao = new UsuarioDAOJDBC();
    List<Usuario> usuarios = dao.listar(); // lista tudo

    for (Usuario usuario : usuarios) {
        model.addRow(new Object[]{
            usuario.getId_usuario(),
            usuario.getNome(),
            usuario.getTelefone(),
            usuario.getEmail(),
        });
    }
    }
    private Object valorInicialDisponivel;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ScrollLivro = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        btnInserir1 = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbDisponivel = new javax.swing.JComboBox<>();
        cbDoador = new javax.swing.JComboBox<>();
        cbGenero = new javax.swing.JComboBox<>();
        cbTitulo = new javax.swing.JComboBox<>();
        cbAutor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 244));

        ScrollLivro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ScrollLivro.setForeground(new java.awt.Color(255, 255, 204));

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Usuário", "Nome", "Telefone", "E-mail"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblUsuarioFocusGained(evt);
            }
        });
        ScrollLivro.setViewportView(tblUsuario);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/edit.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setToolTipText("Edita a linha selecionada.");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnInserir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/image-gallery.png"))); // NOI18N
        btnInserir1.setText("Inserir");
        btnInserir1.setToolTipText("Insere um novo livro.");
        btnInserir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserir1ActionPerformed(evt);
            }
        });

        btnApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/delete.png"))); // NOI18N
        btnApagar.setText("Apagar");
        btnApagar.setToolTipText("Apaga a linha selecionada.");
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 244));

        cbDisponivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sim", "Não" }));
        cbDisponivel.setToolTipText("Selecione para realizar a busca.");
        cbDisponivel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDisponivelActionPerformed(evt);
            }
        });

        cbDoador.setModel(new javax.swing.DefaultComboBoxModel(
            java.util.stream.IntStream.range(0, tblUsuario.getRowCount())
            .mapToObj(i -> tblUsuario.getValueAt(i, 1).toString())
            .toArray()
        )
    );
    cbDoador.setToolTipText("Selecione para realizar a busca.");
    cbDoador.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(java.awt.event.FocusEvent evt) {
            cbDoadorFocusGained(evt);
        }
    });
    cbDoador.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cbDoadorActionPerformed(evt);
        }
    });

    cbGenero.setToolTipText("Selecione para realizar a busca.");
    cbGenero.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusLost(java.awt.event.FocusEvent evt) {
            cbGeneroFocusLost(evt);
        }
    });
    cbGenero.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cbGeneroActionPerformed(evt);
        }
    });

    cbTitulo.setToolTipText("Selecione para realizar a busca.");
    cbTitulo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cbTituloActionPerformed(evt);
        }
    });

    cbAutor.setToolTipText("Selecione para realizar a busca.");
    cbAutor.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            cbAutorActionPerformed(evt);
        }
    });

    jLabel5.setText("  Titulo  ");
    jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0)));

    jLabel6.setText("  Autor  ");
    jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0)));

    jLabel1.setText("  Gênero  ");
    jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0)));

    jLabel7.setText("  Doador  ");
    jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0)));

    jLabel4.setText("   Disponivel   ");
    jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 0)));

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(36, 36, 36)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cbDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jLabel7)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(83, 83, 83)
                    .addComponent(jLabel1)
                    .addGap(122, 122, 122)
                    .addComponent(jLabel5))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(45, 45, 45)
                    .addComponent(cbTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(46, 46, 46)
                    .addComponent(cbAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(90, 90, 90)))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(cbDisponivel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel4)
                    .addGap(28, 28, 28)))
            .addGap(57, 57, 57))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbDisponivel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel7)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbDoador, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(53, Short.MAX_VALUE))
    );

    btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/broom.png"))); // NOI18N
    btnLimpar.setText("Limpar");
    btnLimpar.setToolTipText("Limpa os filtros de busca.");
    btnLimpar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnLimparActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(557, 915, Short.MAX_VALUE)
            .addComponent(jLabel3))
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addComponent(ScrollLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(180, 180, 180)
                    .addComponent(btnInserir1)
                    .addGap(53, 53, 53)
                    .addComponent(btnEditar)
                    .addGap(49, 49, 49)
                    .addComponent(btnApagar)
                    .addGap(48, 48, 48)
                    .addComponent(btnLimpar)))
            .addGap(0, 0, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(ScrollLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(8, 8, 8)
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnInserir1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(35, 35, 35))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblUsuarioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tblUsuarioFocusGained

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int linhaSelecionada = tblUsuario.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para editar.");
            return;
        }

        int idLivro = (int) tblUsuario.getValueAt(linhaSelecionada, 0);
        DialogEditar dialog = new DialogEditar(this, true, idLivro);
        dialog.setVisible(true);
        atualizarTabela();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnInserir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserir1ActionPerformed
        DialogInserir dialog = new DialogInserir(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        atualizarTabela();
    }//GEN-LAST:event_btnInserir1ActionPerformed

    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        int linhaSelecionada = tblUsuario.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione a linha a ser apagada!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Tem certeza que deseja apagar essa linha?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            int idLivro = (int) tblUsuario.getValueAt(linhaSelecionada, 0);

            try {
                // Usa a implementação correta do DAO
                LivroDAO dao = new LivroDAOJDBC();

                // Tenta apagar do banco
                int sucesso = dao.apagar(idLivro);

                if (sucesso > 0) {
                    ((DefaultTableModel) tblUsuario.getModel()).removeRow(linhaSelecionada);
                    JOptionPane.showMessageDialog(this, "Livro apagado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao apagar o livro!", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao tentar apagar o livro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        //Tooltip
        cbGenero.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JComponent) {
                    ((JComponent) c).setToolTipText(value.toString());
                }
                return c;
            }
        });
    }//GEN-LAST:event_btnApagarActionPerformed

    private void cbDisponivelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDisponivelActionPerformed
        String genero = (String) cbGenero.getSelectedItem();
        if (genero.equals("Todos")) {
            atualizarTabela(); // mostra todos
        } else {
            atualizarTabela("disponivel", genero); // filtra
        }
    }//GEN-LAST:event_cbDisponivelActionPerformed

    private void cbDoadorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbDoadorFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cbDoadorFocusGained

    private void cbDoadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDoadorActionPerformed
        atualizarTabela("doador", cbDoador.getSelectedItem().toString());
    }//GEN-LAST:event_cbDoadorActionPerformed

    private void cbGeneroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbGeneroFocusLost
        cbDisponivel.setSelectedItem(valorInicialDisponivel);
        atualizarTabela(); // atualiza a tabela mostrando "todos"
    }//GEN-LAST:event_cbGeneroFocusLost

    private void cbGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGeneroActionPerformed
        atualizarTabela("genero", cbGenero.getSelectedItem().toString());
    }//GEN-LAST:event_cbGeneroActionPerformed

    private void cbTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTituloActionPerformed
        atualizarTabela("titulo", cbTitulo.getSelectedItem().toString());
    }//GEN-LAST:event_cbTituloActionPerformed

    private void cbAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAutorActionPerformed
        atualizarTabela("autor", cbAutor.getSelectedItem().toString());
    }//GEN-LAST:event_cbAutorActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        cbDoador.setSelectedIndex(0);      // ou valorInicialDoador se quiser
        cbGenero.setSelectedIndex(0);
        cbTitulo.setSelectedIndex(0);
        cbAutor.setSelectedIndex(0);
        cbDisponivel.setSelectedIndex(0);

        // Atualiza a tabela para mostrar todos os registros
        atualizarTabela();        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimparActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        ajustarLarguraColunas(tblUsuario);
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmUsuario().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollLivro;
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInserir1;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JComboBox<String> cbAutor;
    private javax.swing.JComboBox<String> cbDisponivel;
    private javax.swing.JComboBox<Usuario> cbDoador;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JComboBox<String> cbTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTable tblUsuario;
    // End of variables declaration//GEN-END:variables
}
