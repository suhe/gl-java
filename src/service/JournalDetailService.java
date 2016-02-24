/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author suhe
 */
public class JournalDetailService {
    private Long rowId;
    private Long id;
    private Long accountID;
    private String accountNo;
    private String description;
    private Double debet;
    private Double credit;
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    
    public void setAccountNo(String var) {
        this.accountNo = var;
    }
    
    public String getAccountNo() {
        return this.accountNo;
    }
    
    public void setDescription(String var) {
        this.description = var;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDebet(Double var) {
        this.debet = var;
    }
    
    public Double getDebet() {
        return this.debet;
    }
    
    public void setCredit(Double var) {
        this.credit = var;
    }
    
    public Double getCredit() {
        return this.credit;
    }
    
    public DataSource getDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUser(Database.getDbUser());
        ds.setPassword(Database.getDbPassword());
        ds.setServerName(Database.getDbHost());
        ds.setDatabaseName(Database.getDbName());
        ds.setPortNumber(Database.getDbPort());
        return ds;
    }
    
    public List<JournalDetailService> findAll(){
        List<JournalDetailService> list = new ArrayList<JournalDetailService>();
        try{
            connection = getDataSource().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM journal_details where journal_id = 0");
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                JournalDetailService table = new JournalDetailService();
                table.setAccountNo(rs.getString("account_no"));
                table.setDescription(rs.getString("description"));
                table.setDebet(rs.getDouble("debet"));
                table.setCredit(rs.getDouble("credit"));
                list.add(table);
            }
            connection.commit();
            connection.setAutoCommit(true);
            
        }catch(SQLException e){ 
            e.printStackTrace();
        }finally{
            try{
                connection.close();
            }catch(SQLException exClose){
                exClose.printStackTrace();
            }
        }
        
        return list;
    }

    
    
    

    
    
}
