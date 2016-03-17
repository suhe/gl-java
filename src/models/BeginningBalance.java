/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Format;
import helpers.Formula;
import helpers.Lang;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
        return Year;
    }

    public void setYear(String var) {
        Year = var;
    }

    public Double getDebet() {
        return Debet;
    }

    public void setDebet(Double var) {
        Debet = var;
    }

    public Double getCredit() {
        return Credit;
    }

    public void setCredit(Double var) {
        Credit = var;
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
            criteria = session.createCriteria(Accounts.class);
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections.property("id").as("id"));
            projList.add(Projections.property("no").as("no"));
            projList.add(Projections.property("name").as("name"));
            projList.add(Projections.property("type").as("type"));
            criteria.setProjection(projList);
            criteria.addOrder(Order.asc("no"));
            criteria.setFirstResult(i);
            criteria.setMaxResults(i + limit);
            criteria.setResultTransformer(Transformers.aliasToBean(Accounts.class));
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
                    bb == null ? 0.00 : Format.currency(bb.getDebet(), 2),
                    bb == null ? 0.00 : Format.currency(bb.getCredit(), 2),
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
    
    public BeginningBalances getRowByAccountNoAndYear(String accountNo, String Year) {
        BeginningBalances bb = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(BeginningBalances.class);
            criteria.add(Restrictions.eq("year", Year));
            criteria.add(Restrictions.eq("accountNo", accountNo));
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
            Query query = session.createSQLQuery("INSERT INTO beginning_balances (account_id, year,debet,credit) VALUES (:id,:year,:debet,:credit)");
            query.setParameter("id", getAccountId());
            query.setParameter("year", getYear());
            query.setParameter("debet", getDebet());
            query.setParameter("credit", getCredit());
            query.executeUpdate();
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
            Query query = session.createSQLQuery("update beginning_balances set debet = :debet,credit = :credit WHERE account_id = :id and year = :year ");
            query.setParameter("id", getAccountId());
            query.setParameter("year", getYear());
            query.setParameter("debet", getDebet());
            query.setParameter("credit", getCredit());
            query.executeUpdate();
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
    
    public void update2(String key,Integer id) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "update BeginningBalances set accountId = :id where accountNo = :key";
            Query query = session.createQuery(hql);
            query.setInteger("id", id);
            query.setString("key", key);
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
    
    public Double getSumBalance(String year,String accountNo,String calc){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double total = null;
        accountNo = accountNo.trim();
        String[] args;
        String calcSelect = "Debet - Credit".equals(calc) ? "debet - credit" : "credit - debet";
        try {
            tx = session.beginTransaction();
            if(accountNo.contains("to")) {
                args = Formula.args(accountNo.trim(),"to");
                String sql = "select sum(" + calcSelect + ") from beginning_balances bb "
                        + " where bb.year = :year and (bb.account_no >= :arg1 and bb.account_no<= :arg2) ";
                SQLQuery query = session.createSQLQuery(sql);
                query.setParameter("year", year);
                query.setParameter("arg1",args[0]);
                query.setParameter("arg2",args[1]);
                total = (Double) query.uniqueResult();
            } else {
                args = Formula.args(accountNo.trim(),"\\,");
                String sql = "select sum(" + calcSelect + ") from beginning_balances bb "
                        + " where bb.year = :year and bb.account_no in(:no) ";
                SQLQuery query = session.createSQLQuery(sql);
                query.setParameter("year", year);
                query.setParameterList("no",args);
                total = (Double) query.uniqueResult();
            }
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            System.out.println("Error :" + e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        
        if(total == null) {
            total = 0.00;
        }
        return total;
    }
}
