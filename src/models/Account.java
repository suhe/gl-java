/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.Accounts;

/**
 *
 * @author suhe
 */
public class Account {
    String[] TABLE_COLUMN_NAME = {"No", "Account No", "Account Name", "Type"};
    
    public String AccountNo;
    public String AccountName;
    public String AccountType;
    public Boolean isEdit;
    
    public DefaultTableModel getList(Integer offset,Integer limit) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public String getColumnName(int column) {
                return TABLE_COLUMN_NAME[column];
            }

            @Override
            public int getColumnCount() {
                return TABLE_COLUMN_NAME.length;
            }
        };
        
         //set to list all data
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Integer i = (limit * (offset - 1));
            tx = session.beginTransaction();
            List list = session.createQuery("FROM Accounts")
                    .setFirstResult(i)
                    .setMaxResults(limit + i).list();
            i++;
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Accounts acc = (Accounts) iterator.next();
                model.addRow(new Object[]{
                    i,
                    acc.getNo(),
                    acc.getName(),
                    acc.getType()
                });
                i++;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        
        return model;
    }
    
    public Integer getCount() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = null;
        
        try {
            tx = session.beginTransaction();
            count = ((Long) session.createQuery("select count(*) from Accounts").uniqueResult()).intValue();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        
        return count;
    }
    
    public void save() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Accounts acc = new Accounts();
            acc.setName(AccountName);
            acc.setNo(AccountNo);
            acc.setType(AccountType);
            session.save(acc);
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
