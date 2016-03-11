/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl.accounts;

import helpers.Config;
import helpers.Lang;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import models.Account;
import models.JournalDetail;
import services.Journals;

import config.DatabaseUtil;
import java.util.List;
import models.BeginningBalance;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.Accounts;

/**
 *
 * @author suhe
 */
public class Coa extends javax.swing.JInternalFrame {

    Integer pageNumber = 1;
    Integer totalRowPerPage = 100;
    Integer totalPage = 1;
    Integer totalRow = 0;
    Account model;
    public JProgressBar jProgressBarStatus = new JProgressBar();

    /**
     * Creates new form Balance
     */
    public Coa() {
        initComponents();
        initComboItem();
        setParameterLanguage();
        initTable();
    }

    public void coa() {
        initTable();
    }
    
    private void setParameterLanguage() {
        jButtonFirst.setText(Lang.getString("App.first"));
        jButtonNext.setText(Lang.getString("App.next"));
        jButtonPrev.setText(Lang.getString("App.previous"));
        jButtonLast.setText(Lang.getString("App.last"));
        jButtonSearch.setText(Lang.getString("App.search"));
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
        model.jProgressBarStatus = jProgressBarStatus;
        totalRow = model.getCount();
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
        jTableAccount.setModel(model.getList(pageNumber, totalRowPerPage));
        jTableAccount.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTableAccount.setCellSelectionEnabled(false);
        jTableAccount.setRowSelectionAllowed(true);
        TableColumnModel table = jTableAccount.getColumnModel();
        table.getColumn(0).setPreferredWidth(80);
        table.getColumn(1).setPreferredWidth(160);
        table.getColumn(2).setPreferredWidth(260);
        table.getColumn(3).setPreferredWidth(160);
        table.getColumn(4).setPreferredWidth(50);
        jLabelSummary.setText(Lang.getString("App.page") + " : " + pageNumber + " / " + totalPage + Lang.getString("App.total") + " : " + totalRow);
        //set to value 0 progress bar
        Runnable doProgress;
        doProgress = new Runnable() {
            @Override
            public void run() {
                jProgressBarStatus.setMaximum(totalPage - 1);
                jProgressBarStatus.setMinimum(0);
                jProgressBarStatus.setValue(pageNumber);
            }
        };
        SwingUtilities.invokeLater(doProgress);
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
        jTableAccount = new javax.swing.JTable();
        jButtonAdd = new javax.swing.JButton();
        jButtonDelRow = new javax.swing.JButton();
        jButtonUpRow = new javax.swing.JButton();
        jButtonBottomRow = new javax.swing.JButton();
        jButtonFirst = new javax.swing.JButton();
        jButtonPrev = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jButtonLast = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jComboBoxTotalRows = new javax.swing.JComboBox<>();
        jLabelSummary = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Cart of account");

        jTableAccount.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableAccount.setColumnSelectionAllowed(true);
        jTableAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAccountMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAccount);

        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/plus_16X16.png"))); // NOI18N
        jButtonAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonDelRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/minus_16X16.png"))); // NOI18N
        jButtonDelRow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDelRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDelRowActionPerformed(evt);
            }
        });

        jButtonUpRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/up_16X16.png"))); // NOI18N
        jButtonUpRow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonUpRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpRowActionPerformed(evt);
            }
        });

        jButtonBottomRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/bottom_16X16.png"))); // NOI18N
        jButtonBottomRow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBottomRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBottomRowActionPerformed(evt);
            }
        });

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

        jButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/search_16x16.png"))); // NOI18N
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jComboBoxTotalRows.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelSummary.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPrev)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxTotalRows, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 774, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUpRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBottomRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jButtonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelRow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUpRow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBottomRow)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonFirst)
                        .addComponent(jButtonNext)
                        .addComponent(jButtonLast)
                        .addComponent(jButtonSearch)
                        .addComponent(jButtonPrev))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxTotalRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelSummary)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here
        model = new Account();
        model.setIsEdit(false);
        CoaForm form = new CoaForm(this, false);
        form.setLocationRelativeTo(this);
        form.pack();
        form.list = this;
        form.setVisible(true);
        
        //set to list all data
        /*Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Journals.class);
            List list = criteria.list();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Journals jn = (Journals) iterator.next();
                JournalDetail model2 = new JournalDetail();
                model2.update(jn.getId(), jn.getNumber(),jn.getDate());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }*/
        
        /*Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class);
            List list = criteria.list();
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Accounts acc = (Accounts) iterator.next();
                BeginningBalance model2 = new BeginningBalance();
                model2.update2(acc.getNo(),acc.getId());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }*/

    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonDelRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDelRowActionPerformed
        // TODO add your handling code here:
        int row = jTableAccount.getSelectedRow();
        int _col = 1;
        int col = 4;
        if (row > 0) {
            String accountNo;
            Integer key = Integer.parseInt(jTableAccount.getValueAt(row, col).toString());
            accountNo = jTableAccount.getValueAt(row, _col).toString();
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure want to delete account no : " + accountNo + " ?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                model = new Account();
                model.delete(key);
                initTable();
            }
        }
    }//GEN-LAST:event_jButtonDelRowActionPerformed

    private void jButtonUpRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpRowActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) jTableAccount.getModel();
        int[] rows = jTableAccount.getSelectedRows();
        tableModel.moveRow(rows[0], rows[rows.length - 1], rows[0] - 1);
        jTableAccount.setRowSelectionInterval(rows[0] - 1, rows[rows.length - 1] - 1);

    }//GEN-LAST:event_jButtonUpRowActionPerformed

    private void jButtonBottomRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBottomRowActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) jTableAccount.getModel();
        int[] rows = jTableAccount.getSelectedRows();
        tableModel.moveRow(rows[0], rows[rows.length - 1], rows[0] + 1);
        jTableAccount.setRowSelectionInterval(rows[0] + 1, rows[rows.length - 1] + 1);
    }//GEN-LAST:event_jButtonBottomRowActionPerformed

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

    private void jTableAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAccountMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            model = new Account();
            int row = jTableAccount.getSelectedRow();
            int col = 4;
            Integer key = Integer.parseInt(jTableAccount.getValueAt(row, col).toString());
            model.setIsEdit(true);
            model.setId(key);
            CoaForm form = new CoaForm(this, false);
            form.setLocationRelativeTo(this);
            form.pack();
            form.list = this;
            form.setVisible(true);
        }
    }//GEN-LAST:event_jTableAccountMouseClicked

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        // TODO add your handling code here:
        CoaFind form = new CoaFind(this, false);
        form.setLocationRelativeTo(this);
        form.pack();
        form.list = this;
        form.setVisible(true);
    }//GEN-LAST:event_jButtonSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonBottomRow;
    private javax.swing.JButton jButtonDelRow;
    private javax.swing.JButton jButtonFirst;
    private javax.swing.JButton jButtonLast;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrev;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUpRow;
    private javax.swing.JComboBox<String> jComboBoxTotalRows;
    private javax.swing.JLabel jLabelSummary;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAccount;
    // End of variables declaration//GEN-END:variables
}
