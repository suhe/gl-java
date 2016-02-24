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
public class AccountService {
    private Long rowId;
    private Long id;
    private String no;
    private String name;
    private String type;
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private DataSource dataSource;
    private Database db;
   
    public Long getRowId() {
        return rowId;
    }
    
    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String var) {
        this.name = var;
    }
    
    public String getNo() {
        return no;
    }
    
    public void setNo(String var) {
        this.no = var;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String var) {
        this.type = var;
    }
    
    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
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
    
    /**
     *
     * @param page
     * @param totalRow
     * @param pageNum
     * @return
     */
    public List<AccountService> findAll(int page,int totalRow){
        List<AccountService> listAccount = new ArrayList<AccountService>();
        try{
            connection = getDataSource().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("SELECT * FROM accounts order by no asc limit ?,?");
            preparedStatement.setInt(1, totalRow * (page - 1));
            preparedStatement.setInt(2, totalRow);
            ResultSet rs=preparedStatement.executeQuery();
            
            long i = (totalRow * (page - 1)) + 1;
            while(rs.next()){
                AccountService table = new AccountService();
                table.setId(rs.getLong("id"));
                table.setNo(rs.getString("no"));
                table.setName(rs.getString("name"));
                table.setType(rs.getString("type"));
                table.setRowId(i);
                listAccount.add(table);
                i++;
            }
            connection.commit();
            connection.setAutoCommit(true);
            
        } catch(SQLException e){ 
            try{
                connection.rollback();
            }catch(SQLException exRollBack){
                exRollBack.printStackTrace();
            }
        }finally{
            try {
                connection.close();
            }catch(SQLException exClose){
            }
            return listAccount;
        } 
    }
    
    public int count(){
        int totalrow = 0;
        try {
            //connection = dataSource.getConnection();
            connection = getDataSource().getConnection();
            connection.setAutoCommit(false);
            preparedStatement=connection.prepareStatement("SELECT count(id) from accounts");
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                totalrow = rs.getInt("count(id)");
            }
            connection.commit();
            connection.setAutoCommit(true);
            
        }catch(SQLException ex){
            ex.printStackTrace();
            try{
                connection.rollback();
            }catch(SQLException exRollBack){
                exRollBack.printStackTrace();
            }
        }finally{
            try{
                connection.close();
            }catch(SQLException exClose){
                exClose.printStackTrace();
            }
            return totalrow;
        }
    }
    
}