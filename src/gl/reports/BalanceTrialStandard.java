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
import models.Account;
import models.BeginningBalance;
import models.JournalDetail;
import models.TrialBalance;
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
import services.Accounts;
import services.BeginningBalances;
import services.JournalDetails;

/**
 *
 * @author BDO-IT
 */
public class BalanceTrialStandard extends javax.swing.JInternalFrame {
    public JDesktopPane JP;
    public JProgressBar jProgressBarStatus = new JProgressBar();
    public JLabel jLabelStatus = new JLabel();
    Integer totalRow;
    /**
     * Creates new form BalanceTrialStandard
     */
    public BalanceTrialStandard() {
        initComponents();
        initForm();
    }

    private void initForm() {
        dateChooserComboDateFrom.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        dateChooserComboDateTo.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
    }

    private void initProcess() {
        //list of account
        Account accountModel = new Account();
        totalRow = accountModel.getCount();
        List list = accountModel.getRowsByList();

        jProgressBarStatus.setMinimum(1);
        jProgressBarStatus.setMaximum(totalRow);

        String dateFromStr = Format.dateToString(dateChooserComboDateFrom.getText(), "dd/MM/yyyy", "yyyy-MM-dd");
        Date dateFrom = Format.stringToDate(dateFromStr, "yyyy-MM-dd");
        String dateToStr = Format.dateToString(dateChooserComboDateTo.getText(), "dd/MM/yyyy", "yyyy-MM-dd");
        Date dateTo = Format.stringToDate(dateToStr, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        int iyear = cal.get(Calendar.YEAR);
        String year = String.valueOf(iyear);

        //delete all profit loss summaries template 
        TrialBalance trialBalance = new TrialBalance();
        trialBalance.deleteAll();

        Integer i = jProgressBarStatus.getMinimum();
        jProgressBarStatus.setValue(i);
        Double bbDebet;
        Double bbCredit;
        Double bbTotal;
        Double plDebet;
        Double plCredit;
        Double plTotal;
        Double bDebet;
        Double bCredit;
        Double bTotal;
        Double ebDebet;
        Double ebCredit;
        Double [] plResult;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Accounts acc = (Accounts) iterator.next();
            BeginningBalance bbModel = new BeginningBalance();
            JournalDetail jdModel = new JournalDetail();
            //beginning balance
            BeginningBalances bb = bbModel.getRowByAccountNoAndYear(acc.getNo(), year);
            //System.out.println("Hasil" + bb.getDebet());
            bbDebet = bb != null && bb.getDebet() != null ? bb.getDebet() : 0.00;
            bbCredit = bb != null &&  bb.getCredit()!= null ? bb.getCredit() : 0.00;
            plResult = jdModel.getSumByUntilDate(year, dateFrom, acc.getNo());
            bbDebet+= plResult[0];
            bbCredit+=plResult[1];
            bbTotal = bbDebet - bbCredit;
            
            if(bbTotal < 0) {
                bbDebet = 0.00;
                bbCredit = bbTotal * (-1);
            } else {
                bbDebet = bbTotal;
                bbCredit = 0.00;
            }
            
            plResult = jdModel.GetProfitLossSummary(acc.getNo(), dateFrom, dateTo);
            if(("REVENUE".equals(acc.getType())) || ("INCOME".equals(acc.getType())) || ("EXPENSE".equals(acc.getType()))) {
                plDebet =  plResult[0] != null ? plResult[0] : 0.00 ;
                plCredit = plResult[1] != null ? plResult[1] : 0.00 ;
                bDebet = 0.00;
                bCredit = 0.00;
                
            } else {
                plDebet = 0.00;
                plCredit = 0.00;
                bDebet = plResult[0] != null ? plResult[0] : 0.00 ;
                bCredit = plResult[1] != null ? plResult[1] : 0.00 ;
            }
            
            plTotal = plCredit - plDebet;
            bTotal  = bDebet - bCredit;
            
            if(acc.getNo().equals("11-151-004")) {
                System.out.println("Debet :" + bDebet);
                System.out.println("Credit :" + bCredit);
            }
            
            if(plTotal < 0) {
                plDebet = plTotal * (-1);
                plCredit = 0.00;
                
            } else {
               plDebet = 0.00;
               plCredit = plTotal;
            }
            
            if(bTotal < 0) {
                bDebet = 0.00;
                bCredit = bTotal * (-1);
            } else {
                bDebet = bTotal;
                bCredit = 0.00;
            }
            
            if(acc.getNo().equals("11-151-004")) {
                System.out.println("Total Debet :" + bDebet);
                System.out.println("Total Credit :" + bCredit);
            }
            
            //total All
            ebDebet = bbDebet + plDebet + bDebet;
            ebCredit = bbCredit + plCredit + bCredit;
            
            //put to trial balance
            TrialBalance tb = new TrialBalance();
            tb.save(acc.getNo(), acc.getName(), bbDebet, bbCredit,plDebet,plCredit,bDebet,bCredit,ebDebet,ebCredit);
            jProgressBarStatus.setValue(i);
            i++;
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
        jButton1 = new javax.swing.JButton();

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

        jButton1.setText("Progress");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
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
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
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
                    .addComponent(jButtonPreview)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void preview() {
        try {
            TrialBalance trialBalance = new TrialBalance();
            JRBeanCollectionDataSource beanCollection = new JRBeanCollectionDataSource(trialBalance.getRowsByList());
            Map<String, Object> map = new HashMap<>();
            map.put("PERIODE", dateChooserComboDateFrom.getText() + " - " + dateChooserComboDateTo.getText());
            InputStream input = getResourceAsStream("/reports/BalanceTrialStandard.jrxml");
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

        try {
            initProcess();
            TrialBalance trialBalance = new TrialBalance();
            JRBeanCollectionDataSource beanCollection = new JRBeanCollectionDataSource(trialBalance.getRowsByList());
            Map<String, Object> map = new HashMap<>();
            map.put("PERIODE", dateChooserComboDateFrom.getText() + " - " + dateChooserComboDateTo.getText());
            InputStream input = getResourceAsStream("/reports/BalanceTrialStandard.jrxml");
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

    }//GEN-LAST:event_jButtonPreviewActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    
    public class thread1 implements Runnable{
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
            
            Account accountModel = new Account();
            BeginningBalance bbModel = new BeginningBalance();
            TrialBalance tbModel = new TrialBalance();
            JournalDetail jdModel = new JournalDetail();
            
            totalRow = accountModel.getCount(accountNoFrom,accountNoTo);
            List list = accountModel.getRowsByList(accountNoFrom,accountNoTo);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            jProgressBarStatus.setMaximum(totalRow);
            jProgressBarStatus.setMinimum(1);
            Integer i = 1;
            Double bbDebet = 0.00;
            Double bbCredit = 0.00;
            Double uDebet = 0.00;
            Double uCredit = 0.00;
            Double bbTotal = 0.00;
            Double plDebet = 0.00;
            Double plCredit = 0.00;
            Double plTotal = 0.00;
            Double bDebet = 0.00;
            Double bCredit = 0.00;
            Double bTotal = 0.00;
            Double ebDebet = 0.00;
            Double ebCredit = 0.00;
            
            tbModel.deleteAll(); //delete all from old balance
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Accounts acc = (Accounts) iterator.next();
                jLabelStatus.setText("Status : processing account  :" + acc.getNo());
                jProgressBarStatus.setValue(i);
                jProgressBarStatus.repaint(); //Refresh graphics
                try{
                    Thread.sleep(50);
                    //beginning balances
                    BeginningBalances bb = bbModel.getRowByAccountNoAndYear(acc.getNo(), year);
                    JournalDetails u = jdModel.getSumBalanceByUntilDate(year,dateFrom,acc.getNo());
                    JournalDetails jds = jdModel.getSumBalanceByDate(dateFrom,dateTo,acc.getNo());
                    bbDebet = bb != null && bb.getDebet() != null ? bb.getDebet() : 0.00;
                    bbCredit = bb != null &&  bb.getCredit()!= null ? bb.getCredit() : 0.00;
                    uDebet = u != null && u.getDebet() != null ? u.getDebet() : 0.00;
                    uCredit = u != null &&  u.getCredit()!= null ? u.getCredit() : 0.00;
                    bbTotal = (bbDebet + uDebet) - (bbCredit + uCredit);
                    bbDebet = bbTotal >= 0 ? bbTotal : 0.00;
                    bbCredit = bbTotal < 0 ? bbTotal * (-1) : 0.00;
                    
                    // profit loss & balance
                    if(acc.getType().equals("REVENUE") || acc.getType().equals("INCOME") || acc.getType().equals("EXPENSE")) {
                        plDebet = jds != null && jds.getDebet() != null ? jds.getDebet() : 0.00;
                        plCredit = jds != null && jds.getCredit() != null ? jds.getCredit() : 0.00;
                        plTotal = plDebet - plCredit;
                        bbCredit = plTotal >= 0 ? plTotal : 0.00;
                        bbDebet  = plTotal < 0 ? plTotal * (-1) : 0.00;
                        bDebet = 0.00;
                        bCredit = 0.00;
                    } else {
                        plDebet = 0.00;
                        plCredit = 0.00;
                        bDebet = jds != null && jds.getDebet() != null ? jds.getDebet() : 0.00;
                        bCredit = jds != null && jds.getCredit() != null ? jds.getCredit() : 0.00;
                        bTotal = bDebet - bCredit;
                        bDebet = bTotal >= 0 ? bTotal : 0.00;
                        bCredit  = bTotal < 0 ? bTotal * (-1) : 0.00;
                    }
                    
                    //ending balance
                    ebDebet = bbDebet + plDebet + bDebet;
                    ebCredit = bbCredit + plCredit + bCredit;
                } 
                catch (InterruptedException err){}
                tbModel.save(acc.getNo(), acc.getName(), bbDebet, bbCredit,plDebet,plCredit,bDebet,bCredit,ebDebet,ebCredit);
                
                i++;
            }
            
            setCursor(Cursor.getDefaultCursor());
            jLabelStatus.setText("Status : Done");
            preview();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Thread(new thread1()).start(); //Start the thread
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserComboDateFrom;
    private datechooser.beans.DateChooserCombo dateChooserComboDateTo;
    private javax.swing.JButton jButton1;
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
