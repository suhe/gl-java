/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Formula;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.ProfitLossStandardSummaries;

/**
 *
 * @author suhe
 */
public class ProfitLossStandardSummary {
    private String description;
    private String type;
    private Integer ref;
    private Double totalThisMonth;
    private Double totalUntilMonth;

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

    public Double getTotalThisMonth() {
        return this.totalThisMonth;
    }

    public void setTotalThisMonth(Double totalThisMonth) {
        this.totalThisMonth = totalThisMonth;
    }

    public Double getTotalUntilMonth() {
        return this.totalUntilMonth;
    }

    public void setTotalUntilMonth(Double totalUntilMonth) {
        this.totalUntilMonth = totalUntilMonth;
    }

    public void save() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ProfitLossStandardSummaries pl;
            pl = new ProfitLossStandardSummaries(getDescription(), getRef(), getType(), getTotalThisMonth(), getTotalUntilMonth());
            session.save(pl);
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

    public void deleteAll() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from ProfitLossStandardSummaries where id > :id";
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

    public Double[] GetSummary(String args) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String formula = Formula.main(args);
        Double[] total = new Double[2];

        try {
            tx = session.beginTransaction();
            String sql;
            SQLQuery query;
            switch (formula.toLowerCase().trim()) {
                case "sum":
                    sql = "select " + formula + "(total_this_month)," + formula + "(total_until_month) from profit_loss_standard_summaries"
                            + " where ref >= :arg1 and ref <= :arg2";
                    query = session.createSQLQuery(sql);
                    Integer[] arg = Formula.ref(args);
                    query.setParameter("arg1", arg[0].toString());
                    query.setParameter("arg2", arg[1].toString());
                    break;
                default:
                    sql = "select " + formula + "(total_this_month)," + formula + "(total_until_month) from profit_loss_standard_summaries"
                            + " where ref in(:arg) ";
                    query = session.createSQLQuery(sql);
                    query.setParameterList("arg", Formula.ref(args));
                    break;

            }
            List list = query.list();
            Iterator it = list.iterator();
            Double total1 = 0.00;
            Double total2 = 0.00;
            while (it.hasNext()) {
                Object obj[] = (Object[]) it.next();
                total1 += Double.parseDouble(obj[0].toString());
                total2 += Double.parseDouble(obj[1].toString());
            }

            System.out.println("Total 0 " + total1);
            System.out.println("Total 1 " + total2);
            total[0] = total1;
            total[1] = total2;
            tx.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }

        } finally {
            session.close();
        }
        return total;
    }

    public void update(Integer ref, Double totalThisMonth, Double totalUntilMonth) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "update ProfitLossStandardSummaries "
                    + "set totalThisMonth = :totalThisMonth, "
                    + "totalUntilMonth = :totalUntilMonth "
                    + "where ref = :ref";
            Query query = session.createQuery(hql);
            query.setDouble("totalThisMonth", totalThisMonth);
            query.setDouble("totalUntilMonth", totalUntilMonth);
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
