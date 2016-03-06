/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl.journal;

import helpers.Format;
import helpers.Lang;
import helpers.Validator.IsValidValidator;
import helpers.Validator.RequiredValidator;
import helpers.Validator.ValueRequiredValidator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import models.Account;
import models.Journal;
import models.JournalDetail;
import services.Accounts;
import services.Journals;

/**
 *
 * @author suhe
 */
public class Transaction extends javax.swing.JInternalFrame {
    public JDesktopPane JP = new JDesktopPane();
    public String AccountNo;
    Journal headModel; 
    JournalDetail detailModel;
    Integer id;
    TransactionForm form;
    
     
    /**
     * Creates new form Transaction
     */
    public Transaction() {
        initComponents();
        initForm();
        initTable();
        initComboType();
        initDatePicker();
    }
    
    private void initForm() {
        jTextFieldTDebet.setEditable(false);
        jTextFieldTCredit.setEditable(false);
        jTextFieldTBalanced.setEditable(false);
        if(this.id == null) { 
            this.setEnabledPaginationButton(false);
        }else {
            this.setEnabledPaginationButton(true);
        }
    }
    
    private void setEnabledPaginationButton(Boolean status){
        jButtonFirst.setEnabled(status);
        jButtonPrev.setEnabled(status);
        jButtonNext.setEnabled(status);
        jButtonLast.setEnabled(status);
    }
    
    private void initComboType() {
        jComboBoxType.removeAllItems();
        String[] typeList = Lang.getArray("App.journal_type_list");
        if(0 > typeList.length) {
        } else {
            for(Short i=0;i<typeList.length;i++) {
                jComboBoxType.addItem(typeList[i]);
            }
        }
    }
    
    private void initDatePicker() {
        dateChooserComboDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    }
    
    public void initExistData(Integer Id) {
        headModel = new Journal();
        Journals jn = headModel.getRowById(Id);
        if(jn != null) {
            jButtonSave.setText(Lang.getString("App.update"));
            jTextFieldVoucherNo.setText(jn.getNumber());
            jTextFieldDescription.setText(jn.getDescription());
            dateChooserComboDate.setText(Format.dateToString(jn.getDate().toString(),"yyyy-MM-dd", "dd/MM/yyyy"));
            jTextFieldGiroQc.setText(jn.getCheckNumber());
            jTextFieldTDebet.setText(Format.currency(jn.getDebet() == null ? 0.00 : jn.getDebet(),2));
            jTextFieldTCredit.setText(Format.currency(jn.getCredit() == null ? 0.00 : jn.getCredit(),2));
            Double tbalanced = (jn.getDebet() == null ? 0.00 : jn.getDebet()) - (jn.getCredit() == null ? 0.00 : jn.getCredit());
            jTextFieldTBalanced.setText(Format.currency(tbalanced,2));
            this.id = Id; 
            
        } else {
            jButtonSave.setText(Lang.getString("App.save"));
            jTextFieldVoucherNo.setText("");
            jTextFieldDescription.setText("");
            jTextFieldGiroQc.setText("");
            jTextFieldTDebet.setText("");
            jTextFieldTCredit.setText("");
            jTextFieldTBalanced.setText("");
            this.id = null; 
        }
        
        this.initForm();
        this.initTable();
    }
    
    private void initTable() {
        detailModel = new JournalDetail();
        jTable1.setModel(detailModel.getList(this.id,0, 1000));
        TableColumnModel table = jTable1.getColumnModel();
        table.getColumn(0).setPreferredWidth(30);
        table.getColumn(1).setPreferredWidth(160);
        table.getColumn(2).setPreferredWidth(250);
        table.getColumn(3).setPreferredWidth(150);
        table.getColumn(4).setPreferredWidth(150);
    }
    
    public void initCalculation() {
        Double totalDebet = 0.00,totalCredit =0.00;
        int totalRow = jTable1.getRowCount();
        if(totalRow > 0){
            for(int i=0;i<totalRow;i++){
                String SDebet = jTable1.getValueAt(i, 3).toString();
                String SCredit = jTable1.getValueAt(i, 4).toString();
                Double debet = Format.stringToDouble(SDebet);
                Double credit = Format.stringToDouble(SCredit);
                totalDebet+=debet;
                totalCredit+=credit;
            }
        }
        
        Double totalBalance = totalDebet - totalCredit;  
        jTextFieldTDebet.setText(Format.currency(totalDebet,2));
        jTextFieldTCredit.setText(Format.currency(totalCredit,2));
        jTextFieldTBalanced.setText(Format.currency(totalBalance,2));
    }
    
    public void setAccountLoad(String AccountNo,int Row,int Col) {
        jTable1.setValueAt("144", 1, 0);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldGiroQc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldDescription = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldVoucherNo = new javax.swing.JTextField();
        dateChooserComboDate = new datechooser.beans.DateChooserCombo();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonFirst = new javax.swing.JButton();
        jButtonPrev = new javax.swing.JButton();
        jButtonNext = new javax.swing.JButton();
        jButtonLast = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonSearch = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldTBalanced = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldTCredit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldTDebet = new javax.swing.JTextField();
        jButtonAddRow = new javax.swing.JButton();
        jButtonDelRow = new javax.swing.JButton();
        jButtonUpRow = new javax.swing.JButton();
        jButtonBottomRow = new javax.swing.JButton();

        setClosable(true);
        setName("Transaction"); // NOI18N

        jLabel1.setText("No");

        jLabel2.setText("Date");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Type");

        jLabel4.setText("Giro/QC");

        jLabel5.setText("Description");

        dateChooserComboDate.setFormat(1);
        dateChooserComboDate.setLocale(new java.util.Locale("in", "", ""));
        dateChooserComboDate.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldVoucherNo, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                            .addComponent(dateChooserComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldGiroQc, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextFieldDescription))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(jTextFieldVoucherNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextFieldGiroQc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(dateChooserComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jButtonLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/pagination_next_16x16.png"))); // NOI18N
        jButtonLast.setText("Last");
        jButtonLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLastActionPerformed(evt);
            }
        });

        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/plus_16X16.png"))); // NOI18N
        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/search_16x16.png"))); // NOI18N
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/save_16X16.png"))); // NOI18N
        jButtonCancel.setText("Cancel");

        jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/save_16X16.png"))); // NOI18N
        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jLabel6.setText("Balanced");

        jLabel7.setText("Total Credit");

        jLabel8.setText("Total Debet");

        jButtonAddRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/plus_16X16.png"))); // NOI18N
        jButtonAddRow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddRowActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonFirst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPrev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNext)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonLast)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSearch))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldTDebet, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldTCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextFieldTBalanced, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(603, 603, 603)
                                        .addComponent(jButtonAdd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButtonSave)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                        .addComponent(jButtonCancel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonAddRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonDelRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonUpRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonBottomRow, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(15, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButtonAddRow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDelRow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUpRow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBottomRow)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTBalanced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldTCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldTDebet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonLast)
                    .addComponent(jButtonNext)
                    .addComponent(jButtonPrev)
                    .addComponent(jButtonFirst)
                    .addComponent(jButtonSearch)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonSave))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFirstActionPerformed
        headModel = new Journal();
        Journals jn  =  headModel.getFirstRow();
        Integer xid = jn != null ? jn.getId() : 0; 
        initExistData(xid);
        jButtonFirst.setEnabled(false);
        jButtonPrev.setEnabled(false);
        jButtonNext.setEnabled(true);
        jButtonLast.setEnabled(true);
    }//GEN-LAST:event_jButtonFirstActionPerformed

    private void jButtonPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrevActionPerformed
        headModel = new Journal();
        Journals jfirst =  headModel.getFirstRow();
        Journals jprev  =  headModel.getPreviousRow(this.id);
        Integer xfid = jfirst != null ? jfirst.getId() : 0;
        Integer xpid = jprev != null ? jprev.getId() : 0;
       
        initExistData(xpid);
        if(Objects.equals(xfid, xpid)) {
            jButtonFirst.setEnabled(false);
            jButtonPrev.setEnabled(false);
        }else {
            jButtonFirst.setEnabled(true);
            jButtonPrev.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonPrevActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        headModel = new Journal();
        Journals jnext  =  headModel.getNextRow(this.id);
        Journals jlast =  headModel.getLastRow();
        //Journals jlast  =  headModel.getLastRow();
       
        Integer xnid = jnext != null ? jnext.getId() : 0;
        Integer xlid = jnext != null ? jlast.getId() : 0;
         
        initExistData(xnid);
        if(Objects.equals(xnid, xlid)) {
            jButtonLast.setEnabled(false);
            jButtonNext.setEnabled(false);
        }else {
            jButtonLast.setEnabled(true);
            jButtonNext.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLastActionPerformed
        headModel = new Journal();
        Journals jn  =  headModel.getLastRow();
        Integer xid = jn != null ? jn.getId() : 0; 
        initExistData(xid);
        jButtonFirst.setEnabled(true);
        jButtonPrev.setEnabled(true);
        jButtonNext.setEnabled(false);
        jButtonLast.setEnabled(false);
    }//GEN-LAST:event_jButtonLastActionPerformed

    private void jButtonAddRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddRowActionPerformed
        // TODO add your handling code here
        detailModel = new JournalDetail();
        detailModel.setIsEdit(false);
        detailModel.setAccountNo_("");
        detailModel.setDescription_("");
        detailModel.setDebet_(0.00);
        detailModel.setCredit_(0.00);
        form = new TransactionForm(this,false);        
        form.setLocationRelativeTo(this);
        form.pack();
        form.list = this;
        form.table = this.jTable1;
        form.setVisible(true);
    }//GEN-LAST:event_jButtonAddRowActionPerformed

    private void jButtonDelRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDelRowActionPerformed
        // TODO add your handling code here:
        ((DefaultTableModel) jTable1.getModel()).removeRow(jTable1.getSelectedRow());
        int numRows = jTable1.getSelectedRows().length;
        for(int i=0; i<numRows ; i++ ) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(jTable1.getSelectedRow());
            //m_tableModel.removeRow(table.getSelectedRow());
        }
    }//GEN-LAST:event_jButtonDelRowActionPerformed

    private void jButtonUpRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpRowActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model =  (DefaultTableModel)jTable1.getModel();
        int[] rows = jTable1.getSelectedRows();
        model.moveRow(rows[0],rows[rows.length-1],rows[0]-1);
        jTable1.setRowSelectionInterval(rows[0]-1, rows[rows.length-1]-1);
    }//GEN-LAST:event_jButtonUpRowActionPerformed

    private void jButtonBottomRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBottomRowActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model =  (DefaultTableModel)jTable1.getModel();
        int[] rows = jTable1.getSelectedRows();
        model.moveRow(rows[0],rows[rows.length-1],rows[0]+1);
        jTable1.setRowSelectionInterval(rows[0]+1, rows[rows.length-1]+1);
    }//GEN-LAST:event_jButtonBottomRowActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:    
        if(this.id != null) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure want to replace data", "On Update", dialogButton);
            if(dialogResult == JOptionPane.NO_OPTION) {
              return;
            } 
        }

        headModel = new Journal();
        headModel.setId_(id);
        RequiredValidator voucherNoVal = new RequiredValidator(null, jTextFieldVoucherNo, "The field voucher number is required !");
        IsValidValidator isValid = new IsValidValidator(null, jTextFieldVoucherNo, "The voucher number is already !", headModel.isValid(jTextFieldVoucherNo.getText()));
        RequiredValidator descriptionVal = new RequiredValidator(null, jTextFieldDescription, "The field description is required !");
        RequiredValidator tdebetVal = new RequiredValidator(null, jTextFieldTDebet, "The field debet is required !");
        RequiredValidator tcreditVal = new RequiredValidator(null, jTextFieldTCredit, "The field credit is required !");
        RequiredValidator tbalancedVal = new RequiredValidator(null, jTextFieldTBalanced, "The field balanced is required !");
        ValueRequiredValidator isTBalancedValue = new ValueRequiredValidator(null, jTextFieldTBalanced, "The balanced must be .00 !",".00");
        
        Boolean isNumberFill = voucherNoVal.verify(jTextFieldVoucherNo);
        Boolean isValidFill = isValid.verify(jTextFieldVoucherNo);
        Boolean isDescFill = descriptionVal.verify(jTextFieldDescription);
        Boolean isTDebitFill = tdebetVal.verify(jTextFieldTDebet);
        Boolean isTCreditFill = tcreditVal.verify(jTextFieldTCredit);
        Boolean isTBalancedFill = tbalancedVal.verify(jTextFieldTBalanced);
        Boolean isValueTBalancedFill =isTBalancedValue.verify(jTextFieldTBalanced);
        
        if (isNumberFill && isValidFill && isDescFill && isTDebitFill && isTCreditFill && isTBalancedFill) {
            Integer JournalId;
            String number = jTextFieldVoucherNo.getText();
            String dateVal = Format.dateToString(dateChooserComboDate.getText(), "dd/MM/yyyy", "yyyy-MM-dd");
            String type = jComboBoxType.getSelectedItem().toString();
            String description = jTextFieldDescription.getText();
            String qcGiro = jTextFieldGiroQc.getText();
            Double tdebet = Format.stringToDouble(jTextFieldTDebet.getText());
            Double tcredit = Format.stringToDouble(jTextFieldTCredit.getText());
            Journal jModel = new Journal();
            jModel.setNumber(number);
            jModel.setDate(Format.stringToDate(dateVal,"yyyy-MM-dd"));
            jModel.setType(type);
            jModel.setDescription(description);
            jModel.setOther(qcGiro);
            jModel.setDebet(tdebet);
            jModel.setCredit(tcredit);
            if(this.id == null) {
                JournalId = jModel.getInsertId();
            } else {
               JournalId = jModel.getUpdateId(this.id);
               JournalDetail jds = new JournalDetail();
               jds.delete(JournalId);
            }
            
            Integer totalRowTable = this.jTable1.getRowCount();
            if(totalRowTable > 0) {
                for(Integer i=0;i<totalRowTable;i++) {
                    //set account id
                    Account accModel = new Account();
                    Accounts acc = accModel.getRowByAccountNo(jTable1.getValueAt(i, 1).toString());
                    JournalDetail jd = new JournalDetail();
                    jd.setJournalId(JournalId);
                    jd.setPos(i + 1);
                    jd.setAccountId(acc != null ? acc.getId() : 0 );
                    jd.setAccountNo(jTable1.getValueAt(i, 1).toString());
                    jd.setDescription(jTable1.getValueAt(i, 2).toString());
                    jd.setDebet(Format.stringToDouble(jTable1.getValueAt(i, 3).toString()));
                    jd.setCredit(Format.stringToDouble(jTable1.getValueAt(i, 4).toString()));
                    jd.save();
                }
            }
            
            JOptionPane.showMessageDialog(null, "Successfully store to database !", "Store DB", JOptionPane.INFORMATION_MESSAGE);
            this.initExistData(JournalId);
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        // TODO add your handling code here:
        System.out.println("Form : " + this.getName());
        TransactionShared shared = new TransactionShared(this,false);
        shared.setLocationRelativeTo(this);
        shared.pack();
        shared.transaction = this;
        shared.formName = this.getName();
        shared.setVisible(true);
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
         initExistData(null);
         jTextFieldVoucherNo.requestFocus();
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            detailModel = new JournalDetail();
            int row = jTable1.getSelectedRow();
            detailModel.setIsEdit(true);
            detailModel.setAccountNo_(jTable1.getValueAt(row, 1).toString());
            detailModel.setDescription_(jTable1.getValueAt(row, 2).toString());
            detailModel.setDebet_(Format.stringToDouble(jTable1.getValueAt(row, 3).toString()) );
            detailModel.setCredit_(Format.stringToDouble(jTable1.getValueAt(row, 4).toString()));
            TransactionForm formx = new TransactionForm(this,false);
            formx.setLocationRelativeTo(this);
            formx.pack();
            formx.list = this;
            formx.table = this.jTable1;
            formx.row = row;
            formx.setAccountNo("112");
            formx.setVisible(true);
            
         }
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserComboDate;
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddRow;
    private javax.swing.JButton jButtonBottomRow;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonDelRow;
    private javax.swing.JButton jButtonFirst;
    private javax.swing.JButton jButtonLast;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrev;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUpRow;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldDescription;
    private javax.swing.JTextField jTextFieldGiroQc;
    private javax.swing.JTextField jTextFieldTBalanced;
    private javax.swing.JTextField jTextFieldTCredit;
    private javax.swing.JTextField jTextFieldTDebet;
    private javax.swing.JTextField jTextFieldVoucherNo;
    // End of variables declaration//GEN-END:variables

    
}
