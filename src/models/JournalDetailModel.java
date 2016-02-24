/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author suhe
 */
public class JournalDetailModel extends DefaultTableModel {
    private final String HEADER[] = {"Account No","Description","Debet","Credit"};
   
    @Override
    public int getColumnCount() {
        return HEADER.length;
    }

    @Override
    public String getColumnName(int column) {
        return HEADER[column];
    }
    
}
