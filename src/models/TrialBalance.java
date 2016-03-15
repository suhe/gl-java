/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.TrialBalances;

/**
 *
 * @author BDO-IT
 */
public class TrialBalance {
    public void deleteAll() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from TrialBalances where id > :id";
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
    
    public List getRowsByList() {
        List list;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(TrialBalances.class);  
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
}
