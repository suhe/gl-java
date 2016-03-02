/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Lang;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import services.Accounts;

/**
 *
 * @author suhe
 */
public class BeginningBalance {

    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.account_no"), Lang.getString("App.account_name"),
        Lang.getString("App.type"), Lang.getString("App.debet"), Lang.getString("App.credit"), "#"};

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

        //set to list all data
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Integer i = (limit * (offset - 1));
            tx = session.beginTransaction();
            Criteria criteria;
            criteria = session.createCriteria(Accounts.class);
            List list = criteria.list();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                i++;
                Accounts acc;
                acc = (Accounts) it.next();
                model.addRow(new Object[]{
                    i,
                    acc.getNo(),
                    acc.getName(),
                    acc.getType(),
                    acc.getBeginningBalances() == null ? 0.00 : acc.getBeginningBalances().getDebet() ,
                    acc.getBeginningBalances() == null ? 0.00 : acc.getBeginningBalances().getCredit()
                });
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
            Criteria criteria = session.createCriteria(Account.class);
            count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
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
    
    
}
