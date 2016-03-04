/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import helpers.Lang;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author suhe
 */
public class JournalDetail {
    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.account_no"), Lang.getString("App.description"),
        Lang.getString("App.debet"), Lang.getString("App.credit")};
    
    private Integer pos;
    private String accountNo;
    private String description;
    private Double debet;
    private Double credit;
    
    
    public Integer getPos() {
        return this.pos;
    }
    
    public void setPos(Integer var) {
        this.pos = var;
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
            this.getDebet(),
            this.getCredit()
        });
    }
    
}
