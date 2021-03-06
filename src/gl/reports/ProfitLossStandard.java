/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl.reports;

import config.DatabaseUtil;
import helpers.Format;
import helpers.Lang;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import models.JournalDetail;
import models.ProfitLossStandardSummary;
import models.TProfitLoss;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import static org.hibernate.internal.util.ConfigHelper.getResourceAsStream;
import services.ProfitLossStandardSummaries;
import services.TplProfitLoss;

/**
 *
 * @author suhe
 */
public class ProfitLossStandard extends javax.swing.JInternalFrame {
    public JDesktopPane JP;
    public JProgressBar jProgressBarStatus = new JProgressBar();
    //TProfitLoss tplModel;
    //ProfitLossStandardSummary plModel;
    //JournalDetail jdModel;
    Integer totalRow;
    

    /**
     * Creates new form ProfitLoss
     */
    public ProfitLossStandard() {
        initComponents();
        initForm();
    }

    private void initForm() {
        jComboBoxType.removeAllItems();
        jComboBoxType.addItem("Standard Summary");
        jComboBoxType.addItem("Standard Details");

        jComboBoxMonth.removeAllItems();
        String[] monthList = Lang.getArray("App.month_list");
        if (0 > monthList.length) {
        } else {
            for (Short i = 0; i < monthList.length; i++) {
                jComboBoxMonth.addItem(monthList[i]);
            }
        }
    }

    private void initProcess() {
        Runnable doProgress;
        doProgress = new Runnable() {
            @Override
            public void run() {
                TProfitLoss tplModel = new TProfitLoss();
                totalRow = tplModel.getCount();
                List list = tplModel.getRowList();
                jProgressBarStatus.setMinimum(1);
                jProgressBarStatus.setMaximum(totalRow);
                String month = jComboBoxMonth.getSelectedItem().toString().substring(0,2);
                String year = jTextFieldYear.getText();
                //delete all profit loss summaries template 
                ProfitLossStandardSummary pl = new ProfitLossStandardSummary();
                pl.deleteAll();
                Integer i = jProgressBarStatus.getMinimum();
                String description,type;
                Double totalThisMonth;
                Double totalUntilMonth;
                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    TplProfitLoss tpl = (TplProfitLoss) iterator.next();
                    description = tpl.getDescription();
                    type = tpl.getType();
                    switch(tpl.getType()) {
                        case "Field" :
                            JournalDetail jdModel = new JournalDetail();
                            String[] accountNo = Format.getArray(tpl.getAccountNo().trim(),"\\,");
                            totalThisMonth = jdModel.getSumByThisMonth(year, month,accountNo,tpl.getCalc());
                            totalUntilMonth = jdModel.getSumByUntilMonth(year, month,accountNo,tpl.getCalc());
                            break;
                        case "Total" :
                            ProfitLossStandardSummary plxModel = new ProfitLossStandardSummary();
                            Double Total[] = plxModel.GetSummary(tpl.getFormula());
                            totalThisMonth = Total[0];
                            totalUntilMonth = Total[1];
                            plxModel.update(tpl.getRef(), totalThisMonth, totalUntilMonth);
                            break;    
                        default : 
                            totalThisMonth = null;
                            totalUntilMonth = null;
                            break;
                    }
                    
                    ProfitLossStandardSummary plModel = new ProfitLossStandardSummary();
                    plModel.setDescription(tpl.getDescription()); //save to description
                    plModel.setType(tpl.getType()); //save to type
                    plModel.setRef(tpl.getRef()); //save to ref
                    plModel.setTotalThisMonth(totalThisMonth);
                    plModel.setTotalUntilMonth(totalUntilMonth);
                    plModel.save();
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
        jLabel2 = new javax.swing.JLabel();
        jComboBoxMonth = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldYear = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("Profit & Loss Report");

        jLabel1.setText("Type");

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Month");

        jComboBoxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Tahun");

        jTextFieldYear.setText("2009");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/printer_16X16.png"))); // NOI18N
        jButton1.setText("Preview");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cancel_16X16.png"))); // NOI18N
        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldYear, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(9, 9, 9)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        initProcess();
        try {
            Session session = DatabaseUtil.getSessionFactory().openSession();
            Transaction tx;
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(ProfitLossStandardSummaries.class);
            List list = criteria.list();
            JRBeanCollectionDataSource beanCollection = new JRBeanCollectionDataSource(list);
            Map<String, Object> map = new HashMap<>();
            map.put("PERIODE", jComboBoxMonth.getSelectedItem().toString() + " " + jTextFieldYear.getText());
            InputStream input = getResourceAsStream("/reports/ProfitLossStandardSummary.jrxml");
            JasperDesign design = JRXmlLoader.load(input);
            JasperReport report = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, beanCollection);
            jasperPrint.setName("Laporan ");
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
            session.flush();
            tx.commit();
        } catch (JRException ex) {
            System.out.println(ex.getMessage());

        }
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBoxMonth;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextFieldYear;
    // End of variables declaration//GEN-END:variables
}
