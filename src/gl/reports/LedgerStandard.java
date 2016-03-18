/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl.reports;

import helpers.Format;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import models.BeginningBalance;
import models.JournalDetail;
import models.Ledger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import static org.hibernate.internal.util.ConfigHelper.getResourceAsStream;
import services.BeginningBalances;
import services.JournalDetails;
import services.Ledgers;

/**
 *
 * @author BDO-IT
 */
public class LedgerStandard extends javax.swing.JInternalFrame {
    public JDesktopPane JP;
    public JProgressBar jProgressBarStatus = new JProgressBar();
    public JLabel jLabelStatus = new JLabel();
    Integer totalRow;
    /**
     * Creates new form BalanceTrialStandard
     */
    public LedgerStandard() {
        initComponents();
        initForm();
    }

    private void initForm() {
        dateChooserComboDateFrom.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        dateChooserComboDateTo.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldAccountNoFrom = new javax.swing.JTextField();
        jLabelDate = new javax.swing.JLabel();
        dateChooserComboDateFrom = new datechooser.beans.DateChooserCombo();
        dateChooserComboDateTo = new datechooser.beans.DateChooserCombo();
        jLabel2 = new javax.swing.JLabel();
        jLabelAccountNo = new javax.swing.JLabel();
        jTextFieldAccountNoTo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButtonPreview = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();

        jTextFieldAccountNoFrom.setText("10-000-000");

        jLabelDate.setText("Date");

        dateChooserComboDateFrom.setFormat(1);
        dateChooserComboDateFrom.setLocale(new java.util.Locale("in", "", ""));
        dateChooserComboDateFrom.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        dateChooserComboDateTo.setFormat(1);
        dateChooserComboDateTo.setLocale(new java.util.Locale("in", "", ""));
        dateChooserComboDateTo.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        jLabel2.setText("to");

        jLabelAccountNo.setText("Account No");

        jTextFieldAccountNoTo.setText("99-999-999");

        jLabel4.setText("to");

        jButtonPreview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/printer_16X16.png"))); // NOI18N
        jButtonPreview.setText("Preview");
        jButtonPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviewActionPerformed(evt);
            }
        });

        jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cancel_16X16.png"))); // NOI18N
        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDate)
                            .addComponent(jLabelAccountNo))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserComboDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jTextFieldAccountNoFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldAccountNoTo)
                            .addComponent(dateChooserComboDateTo, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonPreview)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClose)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dateChooserComboDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dateChooserComboDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelDate)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAccountNoFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAccountNo)
                    .addComponent(jTextFieldAccountNoTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonPreview))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void preview() {
        try {
            Ledger ledger = new Ledger();
            JRBeanCollectionDataSource beanCollection = new JRBeanCollectionDataSource(ledger.getRowsByList());
            Map<String, Object> map = new HashMap<>();
            map.put("PERIODE", dateChooserComboDateFrom.getText() + " - " + dateChooserComboDateTo.getText());
            map.put("ACCOUNT", jTextFieldAccountNoFrom.getText() + " - " + jTextFieldAccountNoTo.getText());
            InputStream input = getResourceAsStream("/reports/LedgerStandard.jrxml");
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, beanCollection);
            jasperPrint.setName("Report");
            JRViewer jv = new JRViewer(jasperPrint);
            JInternalFrame preview = new JInternalFrame();
            preview.setClosable(true);
            JP.add(preview);
            Container c = preview.getContentPane();
            c.setLayout(new BorderLayout());
            Component add = c.add(jv);
            preview.setSize(1024, 700);
            preview.setMaximizable(true);
            Dimension desktopSize = JP.getSize();
            Dimension jInternalFrameSize = this.getSize();
            setLocation((desktopSize.width - jInternalFrameSize.width) / 2, (desktopSize.height - jInternalFrameSize.height) / 2);
            preview.setVisible(true);
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        } finally {
            this.dispose();
        }
    }
    
    private void jButtonPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewActionPerformed
        new Thread(new threadProcess()).start(); //Start the thread    
        
    }//GEN-LAST:event_jButtonPreviewActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    
    public class threadProcess implements Runnable{
        @Override
        public void run(){
            String accountNoFrom = jTextFieldAccountNoFrom.getText();
            String accountNoTo = jTextFieldAccountNoTo.getText();
            String dateFromStr = Format.dateToString(dateChooserComboDateFrom.getText(), "dd/MM/yyyy", "yyyy-MM-dd");
            Date dateFrom = Format.stringToDate(dateFromStr, "yyyy-MM-dd");
            String dateToStr = Format.dateToString(dateChooserComboDateTo.getText(), "dd/MM/yyyy", "yyyy-MM-dd");
            Date dateTo = Format.stringToDate(dateToStr, "yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateFrom);
            int iyear = cal.get(Calendar.YEAR);
            String year = String.valueOf(iyear);
            BeginningBalance bbModel = new BeginningBalance();
            Ledger lgModel = new Ledger();
            JournalDetail jdModel = new JournalDetail();
            totalRow = jdModel.getCount(accountNoFrom,accountNoTo,dateFrom,dateTo);
            List list = jdModel.getRowsByList(accountNoFrom,accountNoTo,dateFrom,dateTo);
            
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            jProgressBarStatus.setMaximum(totalRow);
            jProgressBarStatus.setMinimum(1);
            
            lgModel.deleteAll(); //delete all from old balance
            lgModel.resetAll(); //reset auto increment to 1
            
            Integer i = 1;
            Double bbDebet = 0.00;
            Double bbCredit = 0.00;
            Double uDebet = 0.00;
            Double uCredit = 0.00;
            Double bbTotal = 0.00;
            
            String accountNo = null;
            String accountName = null;
            String type = null;
            Date date = null;
            String description = null;
            Double debet = 0.00;
            Double credit = 0.00;
            Double saldo = 0.00;
            String accountDescription = accountNoFrom + " - " + accountNoTo;
            String periodeDescription = dateFromStr + " - " + dateToStr;
            
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                JournalDetails jds = (JournalDetails) iterator.next();
                jLabelStatus.setText("Status : processing account  :" + jds.getAccounts().getNo());
                jProgressBarStatus.setValue(i);
                jProgressBarStatus.repaint(); //Refresh graphics
                try {
                    Thread.sleep(50);
                    //beginning balances
                    accountNo = jds.getAccounts().getNo();
                    accountName = jds.getAccounts().getName();
                    
                    if(lgModel.getCount(jds.getAccounts().getNo(),"Beginning Balance") == null) {
                        BeginningBalances bb = bbModel.getRowByAccountNoAndYear(jds.getAccounts().getNo(), year);
                        JournalDetails u = jdModel.getSumBalanceByUntilDate(year,dateFrom,jds.getAccounts().getNo());
                        bbDebet = bb != null && bb.getDebet() != null ? bb.getDebet() : 0.00;
                        bbCredit = bb != null &&  bb.getCredit()!= null ? bb.getCredit() : 0.00;
                        uDebet = u != null && u.getDebet() != null ? u.getDebet() : 0.00;
                        uCredit = u != null &&  u.getCredit()!= null ? u.getCredit() : 0.00;
                        bbTotal = (bbDebet + uDebet) - (bbCredit + uCredit);
                        bbDebet = bbTotal >= 0 ? bbTotal : 0.00;
                        bbCredit = bbTotal < 0 ? bbTotal * (-1) : 0.00;
                        
                        type = "Beginning Balance";
                        date = dateFrom;
                        description = "Beginning Balance Until " + dateFromStr;
                        debet = bbDebet;
                        credit = bbCredit;
                        saldo = debet - credit;
                    } else {
                        type = "Transaction";
                        date = jds.getDate();
                        description = jds.getDescription();
                        debet = jds.getDebet();
                        credit = jds.getCredit();
                        saldo+= debet - credit;
                    }
                    
                } 
                catch (InterruptedException err){}
                if(jds.getAccounts().getNo() != null) {
                    lgModel.save(accountNo, accountName, type,date,description,debet,credit,saldo,accountDescription,periodeDescription);
                }
                
                i++;
            }
            
            setCursor(Cursor.getDefaultCursor());
            jLabelStatus.setText("Status : Done");
            preview();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserComboDateFrom;
    private datechooser.beans.DateChooserCombo dateChooserComboDateTo;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonPreview;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelAccountNo;
    private javax.swing.JLabel jLabelDate;
    private javax.swing.JTextField jTextFieldAccountNoFrom;
    private javax.swing.JTextField jTextFieldAccountNoTo;
    // End of variables declaration//GEN-END:variables
}
