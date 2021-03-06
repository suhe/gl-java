/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl.accounts;

import gl.journal.TransactionForm;
import helpers.Config;
import helpers.Lang;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import models.Account;

/**
 *
 * @author suhe
 */
public class CoaShared extends javax.swing.JDialog {
    Integer pageNumber = 1;
    Integer totalRowPerPage = 100;
    Integer totalPage = 1;
    Integer totalRow = 0;
    String query = "";
    String type = "";
    Account model;
    public Coa coa;
    public String formName;
    public TransactionForm transactionForm;

    /**
     * Creates new form CoaShared
     * @param parent
     * @param modal
     */
    public CoaShared(JDialog parent, boolean modal) {
        //super(parent, modal);
        initComponents();
        initForm();
        initComboItem();
        initTable();
    }
    
    private void initForm() {
       
    }
    
    private void initComboItem() {
        jComboBoxTotalRows.removeAllItems();
        String[] pageList = Config.getArray("App.page_list");
        if(0>pageList.length) {
        } else {
            for(Short i=0;i<pageList.length;i++) {
                jComboBoxTotalRows.addItem(pageList[i]);
            }
        }
        
        jComboBoxType.removeAllItems();
        jComboBoxType.addItem("ASSET");
        jComboBoxType.addItem("LIABILITY");
        jComboBoxType.addItem("EXPENSE");
        jComboBoxType.addItem("REVENUE");
        
        jComboBoxTotalRows.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                pageNumber = 1;
                initTable();
            }
        });
    }
    
    private void initTable() {
        model = new Account();
        model.setQuery_(query);
        model.setType_(type);
        totalRow = model.getCountShared();
        totalRowPerPage = Integer.valueOf(jComboBoxTotalRows.getSelectedItem().toString());
        Double totalPageDouble = Math.ceil(totalRow.doubleValue() / totalRowPerPage.doubleValue());
        totalPage = totalPageDouble.intValue();

        if (pageNumber.equals(1)) {
            jButtonFirst.setEnabled(false);
            jButtonPrev.setEnabled(false);
        } else {
            jButtonFirst.setEnabled(true);
            jButtonPrev.setEnabled(true);
        }
        jTable1.setModel(model.getListShared(pageNumber, totalRowPerPage));
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.setCellSelectionEnabled(false);
        jTable1.setRowSelectionAllowed(true);
        TableColumnModel table = jTable1.getColumnModel();
        table.getColumn(0).setPreferredWidth(80);
        table.getColumn(1).setPreferredWidth(160);
        table.getColumn(2).setPreferredWidth(260);
        table.getColumn(3).setPreferredWidth(160);
        table.getColumn(4).setPreferredWidth(50);
    }
    
    private void getSelectedRow() {
        Integer row = jTable1.getSelectedRow();
        Integer col = 1;
        String result = jTable1.getValueAt(row, col).toString();
        switch (formName) {
            case "TransactionForm":
                transactionForm.getItemSelected(result);
                break;
            default:
                JOptionPane.showMessageDialog(null, "no choice form !", "App Shared", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldQuery = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jButtonFirst = new javax.swing.JButton();
        jButtonPrev = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jButtonLast = new javax.swing.JButton();
        jComboBoxTotalRows = new javax.swing.JComboBox<>();
        jButtonSelect = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("No or Name :");

        jButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/search_16x16.png"))); // NOI18N
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jLabel2.setText("Type :");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/pagination_first_16x16.png"))); // NOI18N
        jButtonFirst.setText("First");
        jButtonFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFirstActionPerformed(evt);
            }
        });

        jButtonPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/pagination_prev_16x16.png"))); // NOI18N
        jButtonPrev.setText("Prev");
        jButtonPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrevActionPerformed(evt);
            }
        });

        jButtonNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/pagination_next_16x16.png"))); // NOI18N
        jButtonNext.setText("Next");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jButtonLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/pagination_last_16x16.png"))); // NOI18N
        jButtonLast.setText("Last");
        jButtonLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLastActionPerformed(evt);
            }
        });

        jComboBoxTotalRows.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/plus_16X16.png"))); // NOI18N
        jButtonSelect.setText("Select");
        jButtonSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectActionPerformed(evt);
            }
        });

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cancel_16X16.png"))); // NOI18N
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxTotalRows, 0, 124, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSelect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonFirst)
                    .addComponent(jButtonNext)
                    .addComponent(jButtonLast)
                    .addComponent(jButtonPrev)
                    .addComponent(jComboBoxTotalRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSelect)
                    .addComponent(jButtonCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        // TODO add your handling code here:
        this.pageNumber = 1;
        this.query = jTextFieldQuery.getText();
        this.type = jComboBoxType.getSelectedItem().toString();
        initTable();
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFirstActionPerformed
        // TODO add your handling code here:
        this.pageNumber = 1;
        initTable();
    }//GEN-LAST:event_jButtonFirstActionPerformed

    private void jButtonPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrevActionPerformed
        // TODO add your handling code here:
        if (pageNumber > 1) {
            this.pageNumber--;
            initTable();
        }
    }//GEN-LAST:event_jButtonPrevActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        // TODO add your handling code here:
        if (pageNumber < totalPage) {
            this.pageNumber++;
            initTable();
        }
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLastActionPerformed
        // TODO add your handling code here:
        this.pageNumber = totalPage;
        initTable();
    }//GEN-LAST:event_jButtonLastActionPerformed

    private void jButtonSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectActionPerformed
        // TODO add your handling code here:
        this.getSelectedRow();
        this.dispose();
    }//GEN-LAST:event_jButtonSelectActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        this.getSelectedRow();
        this.dispose();
    }//GEN-LAST:event_jTable1MouseClicked

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CoaShared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CoaShared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CoaShared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CoaShared.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CoaShared dialog = new CoaShared(new JDialog(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonFirst;
    private javax.swing.JButton jButtonLast;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrev;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonSelect;
    private javax.swing.JComboBox<String> jComboBoxTotalRows;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldQuery;
    // End of variables declaration//GEN-END:variables
}
