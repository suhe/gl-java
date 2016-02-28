/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import java.util.Iterator;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.Accounts;

/**
 *
 * @author suhe
 */
public class Account {

    String[] TABLE_COLUMN_NAME = {"No", "Account No", "Account Name", "Type", "#"};

    public String AccountNo;
    public String AccountName;
    public String AccountType;
    public static Boolean isEdit = false;
    public static Boolean isValid = false;
    public static Integer Id;
    public static String _accountNo = "";
    public static String _accountName = "";
    public JProgressBar jProgressBarStatus = new JProgressBar(0, 100);

    public Account() {

    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean status) {
        isEdit = status;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer var) {
        Id = var;
    }

    public String getAccountNo() {
        return _accountNo;
    }

    public void setAccountNo(String var) {
        _accountNo = var;
    }

    public String getAccountName() {
        return _accountName;
    }

    public void setAccountName(String var) {
        _accountName = var;
    }

    public Boolean isValid(String No) {
        boolean status = true;
        Accounts acc;
        Accounts rowId;
        acc = getRowByAccountNo(No);
        rowId =  getRow();
        if((!getIsEdit()) && (acc != null)) {
            status = false;
        } else if(getIsEdit() && rowId != null ) {
            if((!rowId.getNo().equals(No)) && (acc != null)) {
                status = false;
            }
        } 
        return status;
    }
    
    public Boolean isUsed() {
        return true;
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

        //set to list all data
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            Integer i = (limit * (offset - 1));
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class)
                    .setFirstResult(i)
                    .setMaxResults(limit + i);

            //search provider
            if (!getAccountNo().equals("")) {
                criteria.add(Restrictions.like("no", "%" + getAccountNo() + "%"));
            }
            if (!getAccountName().equals("")) {
                criteria.add(Restrictions.like("name", "%" + getAccountName() + "%"));
            }

            List list = criteria.list();
            i++;
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Accounts acc = (Accounts) iterator.next();
                model.addRow(new Object[]{
                    i,
                    acc.getNo(),
                    acc.getName(),
                    acc.getType(),
                    acc.getId()
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
            Criteria criteria = session.createCriteria(Accounts.class);

            //search provider
            if (!getAccountNo().equals("")) {
                criteria.add(Restrictions.like("no", "%" + getAccountNo() + "%"));
            }

            if (!getAccountName().equals("")) {
                criteria.add(Restrictions.like("name", "%" + getAccountName() + "%"));
            }

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

    public Accounts getRow() {
        Accounts acc = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class)
                    .add(Restrictions.eq("id", getId()));
            acc = (Accounts) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return acc;
    }

    public Accounts getRowByAccountNo(String No) {
        Accounts acc = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class)
                    .add(Restrictions.eq("no", No));
            acc = (Accounts) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return acc;
    }
    

    public void saveOrUpdate() {
        if (getIsEdit() == true) {
            this.update();
        } else {
            this.save();
        }
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
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void update() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Accounts acc = (Accounts) session.get(Accounts.class, getId());//1
            acc.setName(AccountName);
            acc.setNo(AccountNo);
            acc.setType(AccountType);
            session.update(acc);
            session.flush();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public void delete(Integer Key) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Accounts acc = (Accounts) session.get(Accounts.class, Key);
            session.delete(acc);
            session.flush();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
}
