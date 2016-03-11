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
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import models.BalanceSheetSummary;
import models.BeginningBalance;
import models.JournalDetail;
import models.TBalance;
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
import services.TplBalances;

/**
 *
 * @author suhe
 */
public class BalanceSheetStandard extends javax.swing.JInternalFrame {
    public JDesktopPane JP;
    public JProgressBar jProgressBarStatus = new JProgressBar();
    Integer totalRow;

    /**
     * Creates new form BalanceSheetStandard
     */
    public BalanceSheetStandard() {
        initComponents();
        initForm();
    }
    
    private void initForm() {
        jComboBoxType.removeAllItems();
        jComboBoxType.addItem("Standard Summary");
        jComboBoxType.addItem("Standard Details");
        dateChooserComboDate.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));   
    }
    
    private void initProcess() {
        Runnable doProgress;
        doProgress = new Runnable() {
            @Override
            public void run() {
                TBalance tplModel = new TBalance();
                totalRow = tplModel.getCount();
                List list = tplModel.getRowsByList();
                jProgressBarStatus.setMinimum(1);
                jProgressBarStatus.setMaximum(totalRow);
                String periodeStr = Format.dateToString(dateChooserComboDate.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                Date periode = Format.stringToDate(periodeStr, "yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(periode);
                int iyear = cal.get(Calendar.YEAR);
                String year = String.valueOf(iyear);
                //delete all profit loss summaries template 
                BalanceSheetSummary bs = new BalanceSheetSummary();
                bs.deleteAll();
                Integer i = jProgressBarStatus.getMinimum();
                String description,type;
                Double total;
                Double balance;
                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    TplBalances tpl = (TplBalances) iterator.next();
                    description = tpl.getDescription();
                    type = tpl.getType();
                    switch(tpl.getType()) {
                        case "Field" :
                            JournalDetail jdModel = new JournalDetail();
                            BeginningBalance bModel = new BeginningBalance();
                            String[] accountNo = Format.getArray(tpl.getAccountNo().trim(),"\\,");
                            total = jdModel.getBalanceSheetSummary(periode,year,accountNo,tpl.getCalc().trim());
                            balance = bModel.getSumBalance(year,accountNo,tpl.getCalc().trim());
                            total+=balance;
                            break;
                        case "Total":
                            BalanceSheetSummary bssModel = new  BalanceSheetSummary();
                            total = bssModel.GetSummary(tpl.getFormula());
                            BalanceSheetSummary bssUpdateModel = new  BalanceSheetSummary();
                            bssUpdateModel.update(tpl.getRef(), total);
                            break;
                        default : 
                            total = null;
                            break;
                    }
                    
                    BalanceSheetSummary bsxModel = new BalanceSheetSummary();
                    bsxModel.setDescription(tpl.getDescription()); //save to description
                    bsxModel.setType(tpl.getType()); //save to type
                    bsxModel.setRef(tpl.getRef()); //save to ref
                    bsxModel.setTotal(total); // save to total
                    bsxModel.save();
                    //progress bar counter +1
                    jProgressBarStatus.setValue(i);
                    i++;
                }
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

        jLabel1 = new javax.swing.JLabel();
        jComboBoxType = new javax.swing.JComboBox<>();
        jButtonPreview = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        dateChooserComboDate = new datechooser.beans.DateChooserCombo();

        setTitle("Balance Sheet");

        jLabel1.setText("Type");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        jLabel2.setText("Periode");

        dateChooserComboDate.setFormat(1);
        dateChooserComboDate.setLocale(new java.util.Locale("in", "", ""));
        dateChooserComboDate.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateChooserComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(70, 70, 70))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonPreview)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClose)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(dateChooserComboDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButtonPreview))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviewActionPerformed
       try {
           initProcess();
           BalanceSheetSummary balanceSheetSummary = new BalanceSheetSummary();
           JRBeanCollectionDataSource beanCollection = new JRBeanCollectionDataSource(balanceSheetSummary.getRowsByList());
           Map<String, Object> map = new HashMap<>();
           map.put("PERIODE", dateChooserComboDate.getText());
           InputStream input = getResourceAsStream("/reports/BalanceSheetStandardSummary.jrxml");
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
        }catch (JRException ex) {
            System.out.println(ex.getMessage());
        }finally {
            this.dispose();
        }
       
    }//GEN-LAST:event_jButtonPreviewActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooserComboDate;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonPreview;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
