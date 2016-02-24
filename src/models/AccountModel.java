/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import service.AccountService;

/**
 *
 * @author suhe
 */
public class AccountModel extends AbstractTableModel {
    List<AccountService> listAccount = new ArrayList<AccountService>();
    private final String HEADER[] = {"#","No","Name","Type"};
    
    public void setList(List<AccountService> listAccount){
        this.listAccount = listAccount;
    }
    
    public void save(AccountService account){
        listAccount.add(account);
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }
    
    public void edit(int index,AccountService account){
        listAccount.set(index, account);
        fireTableRowsUpdated(index, index);
    }
    
    public void delete(int index){
        listAccount.remove(index);
        fireTableRowsDeleted(index, index);
    }
    
    public AccountService findOne(int index){
        return listAccount.get(index);
    }
    
    @Override
    public int getRowCount() {
        return listAccount.size();
    }

    @Override
    public int getColumnCount() {
        return HEADER.length;
    }
    
    @Override
    public String getColumnName(int column){
        return HEADER[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AccountService account = listAccount.get(rowIndex);
        
        switch(columnIndex){
            case 0:
            return account.getRowId();
            
            case 1:
            return account.getNo();
            
            case 2:
            return account.getName();
                
            case 3:
            return account.getType();
             
            default:
            return null;
        }
    }
    
    
}
