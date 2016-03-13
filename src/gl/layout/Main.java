/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl.layout;

import config.Database;
import database.Connect;
import gl.accounts.BeginBalance;
import gl.accounts.Coa;
import gl.authorization.AuthRole;
import gl.reports.BalanceSheetStandard;
import gl.reports.ProfitLossStandard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;
import javax.swing.*;

/**
 *
 * @author suhe
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form main
     */
    public Main() {
        initComponents();
        initLayout();
        defaultScreen();
    }
    
    public JProgressBar getProgressBar() {
        return this.jProgressBarStatus;
    }
    
    private void initLayout() {
        setTitle("General Ledger Accounting");
        setEnabledMenuBar(false);
        JP.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        JP.setOpaque(false);
        add(JP,BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void defaultScreen() {
        Login dialog = new Login(this,false);
        dialog.setLocationRelativeTo(this);
        dialog.pack();
        dialog.setVisible(true);
        
        //register menuitem
        dialog.JMenuItemCoa = JMenuItemCoa;        
    }
    
    public void setEnabledMenuBar(boolean status) {
        //JMenuItemConnect.setEnabled(status);
        //JMenuItemCoa.setEnabled(status);
        //jMenuItemTransaction.setEnabled(status);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JP = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonDatabaseConnect = new javax.swing.JButton();
        jButtonCoa = new javax.swing.JButton();
        jButtonBeginBalance = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelStatusConnected = new javax.swing.JLabel();
        jProgressBarStatus = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenu();
        JMenuItemConnect = new javax.swing.JMenuItem();
        JAccount = new javax.swing.JMenu();
        JMenuItemCoa = new javax.swing.JMenuItem();
        jMenuItemBalance = new javax.swing.JMenuItem();
        jMenuJournal = new javax.swing.JMenu();
        jMenuItemTransaction = new javax.swing.JMenuItem();
        jMenuReport = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemProfitLossStandard = new javax.swing.JMenuItem();
        jMenuAccount = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItemBalanceSheetStandard = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItemUsers = new javax.swing.JMenuItem();
        jMenuItemRoles = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");
        setName("JFrame1"); // NOI18N

        jToolBar1.setRollover(true);

        jButtonDatabaseConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/database_28X28.png"))); // NOI18N
        jButtonDatabaseConnect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDatabaseConnect.setFocusable(false);
        jButtonDatabaseConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDatabaseConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDatabaseConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDatabaseConnectActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonDatabaseConnect);

        jButtonCoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/coa_28X28.png"))); // NOI18N
        jButtonCoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonCoa.setFocusable(false);
        jButtonCoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonCoa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonCoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCoaActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonCoa);

        jButtonBeginBalance.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/balance_28X28.png"))); // NOI18N
        jButtonBeginBalance.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBeginBalance.setFocusable(false);
        jButtonBeginBalance.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonBeginBalance.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonBeginBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBeginBalanceActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonBeginBalance);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/gl_28x28.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        JP.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout JPLayout = new javax.swing.GroupLayout(JP);
        JP.setLayout(JPLayout);
        JPLayout.setHorizontalGroup(
            JPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JPLayout.setVerticalGroup(
            JPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(525, Short.MAX_VALUE))
        );

        jLabelStatusConnected.setText("Status : Not Connected");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelStatusConnected)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 323, Short.MAX_VALUE)
                .addComponent(jProgressBarStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBarStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatusConnected))
                .addContainerGap())
        );

        jMenu.setText("File");
        jMenu.setName("file"); // NOI18N

        JMenuItemConnect.setText("Connect Database");
        JMenuItemConnect.setName("JMenuItemConnect"); // NOI18N
        JMenuItemConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemConnectActionPerformed(evt);
            }
        });
        jMenu.add(JMenuItemConnect);

        jMenuBar1.add(jMenu);

        JAccount.setText("Account");

        JMenuItemCoa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        JMenuItemCoa.setText("Chart Of Account");
        JMenuItemCoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMenuItemCoaActionPerformed(evt);
            }
        });
        JAccount.add(JMenuItemCoa);

        jMenuItemBalance.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemBalance.setText("Beginning Balance");
        jMenuItemBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBalanceActionPerformed(evt);
            }
        });
        JAccount.add(jMenuItemBalance);

        jMenuBar1.add(JAccount);

        jMenuJournal.setText("Journal");

        jMenuItemTransaction.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemTransaction.setText("Transaction");
        jMenuItemTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTransactionActionPerformed(evt);
            }
        });
        jMenuJournal.add(jMenuItemTransaction);

        jMenuBar1.add(jMenuJournal);

        jMenuReport.setText("Report");

        jMenu1.setText("Profit & Lost");

        jMenuItemProfitLossStandard.setText("Profit & Lost Standard");
        jMenuItemProfitLossStandard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProfitLossStandardActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemProfitLossStandard);

        jMenuReport.add(jMenu1);

        jMenuAccount.setText("Account");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Account List");
        jMenuAccount.add(jMenuItem1);

        jMenuItem2.setText("Saldo Balanced");
        jMenuAccount.add(jMenuItem2);

        jMenuReport.add(jMenuAccount);

        jMenu2.setText("Balance Sheet");

        jMenuItem5.setText("Trial Balance");
        jMenu2.add(jMenuItem5);

        jMenuItemBalanceSheetStandard.setText("Balance Sheet Standard");
        jMenuItemBalanceSheetStandard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBalanceSheetStandardActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemBalanceSheetStandard);

        jMenuItem7.setText("Balance Sheet Details");
        jMenu2.add(jMenuItem7);

        jMenuReport.add(jMenu2);

        jMenuItem8.setText("Ledger Transaction");
        jMenuReport.add(jMenuItem8);

        jMenuBar1.add(jMenuReport);

        jMenu3.setText("Administration");

        jMenu4.setText("Authorization");

        jMenuItemUsers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemUsers.setText("Users");
        jMenu4.add(jMenuItemUsers);

        jMenuItemRoles.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemRoles.setText("Roles");
        jMenuItemRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRolesActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItemRoles);

        jMenu3.add(jMenu4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JP)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(JP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JMenuItemConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemConnectActionPerformed
        // TODO add your handling code here:
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Connect")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                Connect frmConnect = new Connect();
                JP.add(frmConnect);
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = frmConnect.getSize();
                frmConnect.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                frmConnect.setVisible(true);
                frmConnect.jLabelStatusConnected = jLabelStatusConnected;
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_JMenuItemConnectActionPerformed

    private void JMenuItemCoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMenuItemCoaActionPerformed
        // TODO add your handling code here:  
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Cart of account")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                Coa list = new Coa();
                JP.add(list);
                //jProgressBarStatus.setMaximum(100);
                //jProgressBarStatus.setMinimum(0);
                //jProgressBarStatus.setValue(50);
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = list.getSize();
                list.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                list.jProgressBarStatus = this.jProgressBarStatus;
                list.setVisible(true);
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_JMenuItemCoaActionPerformed

    private void jButtonCoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCoaActionPerformed
        // TODO add your handling code here:
        this.JMenuItemCoaActionPerformed(evt);
    }//GEN-LAST:event_jButtonCoaActionPerformed

    private void jButtonDatabaseConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDatabaseConnectActionPerformed
        // TODO add your handling code here:
        this.JMenuItemConnectActionPerformed(evt);
    }//GEN-LAST:event_jButtonDatabaseConnectActionPerformed

    private void jMenuItemTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTransactionActionPerformed
        // TODO add your handling code here:
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Report COA")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                gl.journal.Transaction frmTransaction = new gl.journal.Transaction();
                JP.add(frmTransaction);
                frmTransaction.JP = JP;
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = frmTransaction.getSize();
                frmTransaction.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                frmTransaction.setVisible(true);
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemTransactionActionPerformed

    private void jMenuItemBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBalanceActionPerformed
        // TODO add your handling code here:
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Beginning Balance")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                BeginBalance frmBalance = new BeginBalance();
                frmBalance.setTitle("Beginning Balance");
                JP.add(frmBalance);
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = frmBalance.getSize();
                frmBalance.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                frmBalance.setVisible(true);
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemBalanceActionPerformed

    private void jButtonBeginBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBeginBalanceActionPerformed
        // TODO add your handling code here:
        this.jMenuItemBalanceActionPerformed(evt);
    }//GEN-LAST:event_jButtonBeginBalanceActionPerformed

    private void jMenuItemProfitLossStandardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProfitLossStandardActionPerformed
        // TODO add your handling code here:
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Profit & Loss Standard")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                ProfitLossStandard pl = new ProfitLossStandard();
                JP.add(pl);
                pl.JP = JP;
                pl.jProgressBarStatus = this.jProgressBarStatus;
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = pl.getSize();
                pl.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                pl.setVisible(true);
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemProfitLossStandardActionPerformed

    private void jMenuItemBalanceSheetStandardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBalanceSheetStandardActionPerformed
        // TODO add your handling code here:
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Balance sheet standard")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                BalanceSheetStandard bs = new BalanceSheetStandard();
                JP.add(bs);
                bs.JP = JP;
                bs.jProgressBarStatus = this.jProgressBarStatus;
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = bs.getSize();
                bs.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                bs.setVisible(true);
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemBalanceSheetStandardActionPerformed

    private void jMenuItemRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRolesActionPerformed
        // TODO add your handling code here:
        try {
            boolean status = false;
            JInternalFrame[] children;
            children = JP.getAllFrames();
            for (JInternalFrame f : children) {
                if (this.getTitle().equals("Roles")) {
                    f.setSelected(true);
                    status = true;
                    break;
                }
            }
            if (status == false) {
                AuthRole authRole = new AuthRole();
                JP.add(authRole);
                //authRole.JP = JP;
                authRole.jProgressBarStatus = this.jProgressBarStatus;
                Dimension desktopSize = JP.getSize();
                Dimension jInternalFrameSize = authRole.getSize();
                authRole.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,(desktopSize.height - jInternalFrameSize.height) / 2);
                authRole.setVisible(true);
            }
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItemRolesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JAccount;
    private javax.swing.JMenuItem JMenuItemCoa;
    private javax.swing.JMenuItem JMenuItemConnect;
    private javax.swing.JDesktopPane JP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonBeginBalance;
    private javax.swing.JButton jButtonCoa;
    private javax.swing.JButton jButtonDatabaseConnect;
    private javax.swing.JLabel jLabelStatusConnected;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenuAccount;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItemBalance;
    private javax.swing.JMenuItem jMenuItemBalanceSheetStandard;
    private javax.swing.JMenuItem jMenuItemProfitLossStandard;
    private javax.swing.JMenuItem jMenuItemRoles;
    private javax.swing.JMenuItem jMenuItemTransaction;
    private javax.swing.JMenuItem jMenuItemUsers;
    private javax.swing.JMenu jMenuJournal;
    private javax.swing.JMenu jMenuReport;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBarStatus;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
