/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import java.util.Date;
import org.hibernate.Transaction;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.Accounts;
import services.Ledgers;
import services.TrialBalances;


/**
 *
 * @author BDO-IT
 */
public class Ledger {
    
    public void deleteAll() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from Ledgers where id >= :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", 1);
            System.out.println(query.executeUpdate());
            session.flush();
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public void resetAll() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = "ALTER TABLE ledgers AUTO_INCREMENT = :id";
            SQLQuery query = session.createSQLQuery(sql);
            query.setInteger("id", 1);
            System.out.println(query.executeUpdate());
            session.flush();
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }
    
    public List getRowsByList() {
        List list;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Ledgers.class);  
            criteria.addOrder(Order.asc("date"));
            list = criteria.list(); 
            session.flush();
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
    
    public Integer getCount(String accountNo,String type) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = null;

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Ledgers.class);
            criteria.add(Restrictions.eq("accountNo",accountNo));
            criteria.add(Restrictions.eq("type",type));
            count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            tx.commit();
        } catch (HibernateException ex) {
            count = null;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return count;
    }
    
    public void save(String accountNo,String accountName,String type,Date date,String description,Double debet,Double credit,Double saldo,String account,String periode) {
        Session session;
        Ledgers lg = new Ledgers();
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            lg.setAccountNo(accountNo);
            lg.setAccountName(accountName);
            lg.setType(type);
            lg.setDate(date);
            lg.setDescription(description);
            lg.setDebet(debet);
            lg.setCredit(credit);
            lg.setSaldo(saldo);
            lg.setAccountDescription(account);
            lg.setPeriodeDescription(periode);
            session.save(lg);
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
