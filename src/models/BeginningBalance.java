/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Format;
import helpers.Lang;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.Accounts;
import services.BeginningBalances;

/**
 *
 * @author suhe
 */
public class BeginningBalance {

    public static Boolean isEdit = false;
    public static Integer AccountId;
    public static String Year;
    public static Double Debet;
    public static Double Credit;

    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.account_no"), Lang.getString("App.account_name"),
        Lang.getString("App.type"), Lang.getString("App.debet"), Lang.getString("App.credit"), "#"};

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean status) {
        isEdit = status;
    }

    public Integer getAccountId() {
        return AccountId;
    }

    public void setAccountId(Integer var) {
        AccountId = var;
    }

    public String getYear() {
        return this.Year;
    }

    public void setYear(String var) {
        this.Year = var;
    }

    public Double getDebet() {
        return this.Debet;
    }

    public void setDebet(Double var) {
        this.Debet = var;
    }

    public Double getCredit() {
        return this.Credit;
    }

    public void setCredit(Double var) {
        this.Credit = var;
    }

    public DefaultTableModel getList(String Year, Integer offset, final Integer limit) {
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
            criteria = session.createCriteria(Accounts.class, "Accounts");
            criteria.setFetchMode("Accounts.BeginningBalances", FetchMode.JOIN);
            criteria.createAlias("Accounts.BeginningBalances", "bb", Criteria.LEFT_JOIN);
            criteria.setFirstResult(i);
            criteria.setMaxResults(i + limit);
            List list = criteria.list();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                i++;
                Accounts acc;
                acc = (Accounts) it.next();

                BeginningBalance modelJoin = new BeginningBalance();
                BeginningBalances bb;
                bb = modelJoin.getRowByIdAndYear(acc.getId(), Year);
                model.addRow(new Object[]{
                    i,
                    acc.getNo(),
                    acc.getName(),
                    acc.getType(),
                    bb == null ? 0.00 : Format.Currency(bb.getDebet(), 2),
                    bb == null ? 0.00 : Format.Currency(bb.getCredit(), 2),
                    acc.getId()
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
            Criteria criteria;
            criteria = session.createCriteria(Accounts.class);
            count = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                count = 0;
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return count;
    }

    public BeginningBalances getRowByIdAndYear(Integer Id, String Year) {
        BeginningBalances bb = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(BeginningBalances.class);
            criteria.add(Restrictions.like("year", Year));
            criteria.add(Restrictions.eq("accountId", Id));

            bb = (BeginningBalances) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return bb;
    }

    public void saveOrUpdate() {
        BeginningBalances bb = this.getRowByIdAndYear(getAccountId(), getYear());
        if (bb != null) {
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
            BeginningBalances acc = new BeginningBalances();
            acc.setAccountId(this.getAccountId());
            acc.setYear(this.getYear());
            acc.setDebet(this.getDebet() == null ? 0.00 : this.getDebet());
            acc.setCredit(this.getCredit() == null ? 0.00 : this.getCredit());
            session.save(acc);
            session.flush();
            tx.commit();
            System.out.println("Account ID :" + this.getAccountId());
            System.out.println("Year :" + this.getYear());
            System.out.println("Debet :" + this.getDebet().toString());
            System.out.println("Credit :" + this.getCredit().toString());

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
            System.out.println("Account ID :" + this.getAccountId());
            System.out.println("Year :" + this.getYear());
            System.out.println("Debet :" + this.getDebet().toString());
            System.out.println("Credit :" + this.getCredit().toString());
            
            tx = session.beginTransaction();
            Query query = session.createQuery("from BeginningBalances where accountId = :accountId and year = :year ");
            query.setParameter("accountId", this.getAccountId());
            query.setParameter("year", this.getYear());
            BeginningBalances acc = (BeginningBalances) query.list().get(0);
            acc.setDebet(this.getDebet() == null ? 0.00 : this.getDebet());
            acc.setCredit(this.getCredit() == null ? 0.00 : this.getCredit());
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
}
