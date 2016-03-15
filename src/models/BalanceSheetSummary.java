/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Formula;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.BalanceSheetSummaries;

/**
 *
 * @author suhe
 */
public class BalanceSheetSummary {
    private String description;
    private String type;
    private Integer ref;
    private Double total;
   
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRef() {
        return this.ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List getRowsByList() {
        List list;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(BalanceSheetSummaries.class);  
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
    
    public void deleteAll() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from BalanceSheetSummaries where id > :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", 0);
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
    
    public Double GetSummary(String args) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String formula = Formula.main(args);
        Double xtotal = 0.00;
        try {
            tx = session.beginTransaction();
            String sql;
            SQLQuery query;
            Integer[] arg;
            switch (formula.toLowerCase().trim()) {
                case "sum" :
                    sql = "select " + formula + "(total) from balance_sheet_summaries"
                            + " where ref >= :arg1 and ref <= :arg2";
                    query = session.createSQLQuery(sql);
                    arg = Formula.ref(args);
                    query.setParameter("arg1", arg[0].toString());
                    query.setParameter("arg2", arg[1].toString());
                    xtotal = (Double) query.uniqueResult();
                    break;   
                case "avg" :
                    sql = "select " + formula + "(total) from balance_sheet_summaries"
                            + " where ref >= :arg1 and ref <= :arg2";
                    query = session.createSQLQuery(sql);
                    arg = Formula.ref(args);
                    query.setParameter("arg1", arg[0].toString());
                    query.setParameter("arg2", arg[1].toString());
                    xtotal =(Double) query.uniqueResult();
                    break;       
                default:
                    arg = Formula.ref(args);
                    Double subtotal = 0.00;
                    if(arg.length > 0)  {
                        for(Integer i=0;i<arg.length;i++) {
                            sql = "select total from balance_sheet_summaries"
                            + " where ref = :arg ";
                            query = session.createSQLQuery(sql);
                            query.setParameter("arg", arg[i]);
                            if(!"+".equals(Formula.separator(args))) {
                                subtotal-=(Double) query.uniqueResult();
                            } else {
                                subtotal+=(Double) query.uniqueResult();
                            }
                          
                            xtotal = subtotal;
                        }
                    }
                    break;

            }
          
            tx.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }

        } finally {
            session.close();
        }
        return xtotal;
    }
    
    public void save() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            BalanceSheetSummaries bss;
            bss = new BalanceSheetSummaries(getDescription(), getRef(), getType(), getTotal());
            session.save(bss);
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
    
    public void update(Integer ref, Double total) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "update BalanceSheetSummaries "
                    + "set total = :total "
                    + "where ref = :ref";
            Query query = session.createQuery(hql);
            query.setDouble("total", total);
            query.setInteger("ref", ref);
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
    
}
