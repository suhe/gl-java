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
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import services.Accounts;

/**
 *
 * @author suhe
 */
public class Account {

    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.account_no"), Lang.getString("App.account_name"),
        Lang.getString("App.type"), "#"};

    public String AccountNo;
    public String AccountName;
    public String AccountType;
    public static Boolean isEdit = false;
    public static Boolean isValid = false;
    public static Integer Id;
    public static String _accountNo = "";
    public static String _accountName = "";
    public static String query_ = "";
    public static String type_ = "";
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
    
    public String getQuery_() {
        return query_;
    }

    public void setQuery_(String var) {
        query_ = var;
    }
    
    public String getType_() {
        return type_;
    }

    public void setType_(String var) {
        type_ = var;
    }

    public Boolean isValid(String No) {
        Boolean status = true;
        Accounts acc;
        Accounts rowId;
        acc = getRowByAccountNo(No);
        rowId =  getRow(this.getId());
        if((!getIsEdit()) && (acc != null)) {
            status = false;
        } else if(getIsEdit() && rowId != null ) {
            if((!rowId.getNo().equals(No)) && (acc != null)) {
                status = false;
            }
        } 
        return status;
    }
    
    public Boolean isExists(String No) {
        Boolean status = true;
        Accounts acc;
        Accounts rowId;
        acc = getRowByAccountNo(No);
        if(acc == null) status = false;
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
    
    public Integer getCount(String accountNoFrom,String accountNoTo) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = null;

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class);
            criteria.add(Restrictions.ge("no",accountNoFrom));
            criteria.add(Restrictions.le("no",accountNoTo));
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

    public Accounts getRow(Integer Id) {
        Accounts acc = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class)
                    .add(Restrictions.eq("id", Id));
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
    
    public List getRowsByList() {
        //set to list all data
        List list;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class);
            criteria.addOrder(Order.asc("no"));
            /*ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("id").as("id"));
            projList.add(Projections.property("no").as("no"));
            projList.add(Projections.property("name").as("name"));
            projList.add(Projections.property("type").as("type"));
            criteria.setProjection(projList);*/
            
            //if (!getAccountNo().equals("")) {
              //  criteria.add(Restrictions.like("no", "%" + getAccountNo() + "%"));
            //}
            
            //criteria.setResultTransformer(Transformers.aliasToBean(Accounts.class));
            list = criteria.list(); 
            tx.commit();
        } catch (HibernateException e) {
            list = null;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return list;
    }
    
    public List getRowsByList(String accountNoFrom,String accountNoTo) {
        List list;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class);
            criteria.add(Restrictions.ge("no", accountNoFrom));
            criteria.add(Restrictions.le("no", accountNoTo));
            criteria.addOrder(Order.asc("no"));
            list = criteria.list(); 
            tx.commit();
        } catch (HibernateException e) {
            list = null;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return list;
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
    
    public Accounts getRowByAccountName(String accountName) {
        Accounts acc = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class);
            criteria.setMaxResults(1);
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("id").as("id"));
            projList.add(Projections.property("no").as("no"));
            projList.add(Projections.property("accountName").as("accountName"));
            criteria.setProjection(projList);
            criteria.addOrder(Order.asc("no"));
            criteria.add(Restrictions.ilike("accountName", accountName));
            criteria.setResultTransformer(Transformers.aliasToBean(Accounts.class));
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
    
    public Integer getCountShared() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = null;

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Accounts.class);

            if (!getQuery_().equals("")) {
                criteria.add(Restrictions.like("accountName", "%" + getQuery_()+ "%"));
            }

            if (!getType_().equals("")) {
                criteria.add(Restrictions.like("type", "%" + getType_() + "%"));
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
    
    public DefaultTableModel getListShared(Integer offset, final Integer limit) {
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

            if (!getQuery_().equals("")) {
                criteria.add(Restrictions.like("accountName", "%" + getQuery_()+ "%"));
            }

            if (!getType_().equals("")) {
                criteria.add(Restrictions.like("type", "%" + getType_() + "%"));
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
}
