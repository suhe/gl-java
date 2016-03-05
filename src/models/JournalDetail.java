/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Format;
import helpers.Lang;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.JournalDetails;


/**
 *
 * @author suhe
 */
public class JournalDetail {
    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.account_no"), Lang.getString("App.description"),
        Lang.getString("App.debet"), Lang.getString("App.credit")};
    
    private Integer journalId;
    private Integer accountId;
    private Integer pos;
    private String accountNo;
    private String description;
    private Double debet;
    private Double credit;
    
    public Integer getJournalId() {
        return this.journalId;
    }
    
    public void setJournalId(Integer var) {
        this.journalId = var;
    }
    
    public Integer getPos() {
        return this.pos;
    }
    
    public void setPos(Integer var) {
        this.pos = var;
    }
    
    public Integer getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Integer var) {
        this.accountId = var;
    }
    
   
    public String getAccountNo(){
        return this.accountNo;
    }
    
    public void setAccountNo(String var) {
        this.accountNo = var;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String var) {
        this.description = var;
    }
    
    public Double getDebet() {
        return this.debet;
    }
    
    public void setDebet(Double var) {
        this.debet = var;
    }
    
    public Double getCredit() {
        return this.credit;
    }
    
    public void setCredit(Double var) {
        this.credit = var;
    }
    
    public DefaultTableModel getList(Integer offset, final Integer limit) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public String getColumnName(int column) {
                return TABLE_COLUMN_NAME[column];
            }

            @Override
            public int getColumnCount() {
                return TABLE_COLUMN_NAME.length;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        return model;
    }

    public void addTableRow(JTable table) {
        DefaultTableModel defaultModel = (DefaultTableModel) table.getModel();
        defaultModel.addRow(new Object[]{
            this.getPos(),
            this.getAccountNo(),
            this.getDescription(),
            Format.currency(this.getDebet(),2),
            Format.currency(this.getCredit(),2),
        });
    }
    
    public void save() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            JournalDetails details = new JournalDetails();
            details.setJournalId(this.getJournalId());
            details.setAccountId(this.getAccountId());
            details.setAccountNo(this.getAccountNo());
            details.setDescription(this.getDescription());
            details.setPosition(this.getPos());
            details.setDebet(this.getDebet());
            details.setCredit(this.getCredit());
            session.save(details);
            session.flush();
            tx.commit();
            
        }catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
    
}
