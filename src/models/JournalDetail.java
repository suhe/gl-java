/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Format;
import helpers.Lang;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.JournalDetails;


/**
 *
 * @author suhe
 */
public class JournalDetail {
    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.account_no"), Lang.getString("App.description"),
        Lang.getString("App.debet"), Lang.getString("App.credit")};
    
    private Integer journalId;
    private Integer accountId;
    private Integer pos;
    private String accountNo;
    private String description;
    private Double debet;
    private Double credit;
    
    private static Boolean isEdit = false;
    private static String accountNo_;
    private static String description_;
    private static Double debet_;
    private static Double credit_;
    
    
    public Integer getJournalId() {
        return this.journalId;
    }
    
    public void setJournalId(Integer var) {
        this.journalId = var;
    }
    
    public Integer getPos() {
        return this.pos;
    }
    
    public void setPos(Integer var) {
        this.pos = var;
    }
    
    public Integer getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Integer var) {
        this.accountId = var;
    }
    
   
    public String getAccountNo(){
        return this.accountNo;
    }
    
    public void setAccountNo(String var) {
        this.accountNo = var;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String var) {
        this.description = var;
    }
    
    public Double getDebet() {
        return this.debet;
    }
    
    public void setDebet(Double var) {
        this.debet = var;
    }
    
    public Double getCredit() {
        return this.credit;
    }
    
    public void setCredit(Double var) {
        this.credit = var;
    }
    
    public Boolean getIsEdit() {
        return isEdit;
    }
    
    public void setIsEdit(Boolean status) {
        isEdit = status;
    }
    
    public String getAccountNo_(){
        return accountNo_;
    }
    
    public void setAccountNo_(String var) {
        accountNo_ = var;
    }
    
    public String getDescription_(){
        return description_;
    }
    
    public void setDescription_(String var) {
        description_ = var;
    }
    
    public Double getDebet_() {
        return debet_;
    }
    
    public void setDebet_(Double var) {
        debet_ = var;
    }
    
    public Double getCredit_() {
        return credit_;
    }
    
    public void setCredit_(Double var) {
        credit_ = var;
    }
    
    public DefaultTableModel getList(Integer id,Integer offset, final Integer limit) {
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
        
        if (id != null) {
            Session session = DatabaseUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(JournalDetails.class);
                criteria.add(Restrictions.eq("journalId", id));
                criteria.addOrder(Order.asc("position"));
                List list = criteria.list();
                for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                    JournalDetails jd = (JournalDetails) iterator.next();
                    model.addRow(new Object[]{
                        jd.getPosition(),
                        jd.getAccountNo(),
                        jd.getDescription(),
                        Format.currency(jd.getDebet(), 2),
                        Format.currency(jd.getCredit(), 2),});
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
        }
        
        return model;
    }

    public void addTableRow(JTable table) {
        DefaultTableModel defaultModel = (DefaultTableModel) table.getModel();
        defaultModel.addRow(new Object[]{
            this.getPos(),
            this.getAccountNo(),
            this.getDescription(),
            Format.currency(this.getDebet(),2),
            Format.currency(this.getCredit(),2),
        });
    }
    
    public void save() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            JournalDetails details = new JournalDetails();
            details.setJournalId(this.getJournalId());
            details.setAccountId(this.getAccountId());
            details.setAccountNo(this.getAccountNo());
            details.setDescription(this.getDescription());
            details.setPosition(this.getPos());
            details.setDebet(this.getDebet());
            details.setCredit(this.getCredit());
            session.save(details);
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
    
    public void update(Integer key,String number,Date date) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "update JournalDetails set journalId = :jid where number = :number and date = :date";
            Query query = session.createQuery(hql);
            query.setInteger("jid", key);
            query.setString("number", number);
            query.setDate("date", date);
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
    
    public void delete(Integer Key) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from JournalDetails where journalId = :jid";
            Query query = session.createQuery(hql);
            query.setInteger("jid", Key);
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
    
    public Double getSumByThisMonth(String year,String month,String[] accountNo){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double total = 0.00;
        try {
            tx = session.beginTransaction();
            //String[] tags = {"11-151-003", "11-170-004"};
            String sql = "select sum(jd.credit - jd.debet) from Journal_details jd inner join journals j on j.id = jd.journal_id  "
                    + " where month(j.date) = :month and year(j.date) = :year and jd.account_no in(:no) ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("month", month);
            query.setParameter("year", year);
            query.setParameterList("no",accountNo);
            List list = query.list();
            total = Double.parseDouble(list.get(0)!= null ? list.get(0).toString() : "0.00");
            session.flush();
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
    
    public Double getSumByUntilMonth(String year,String month,String[] accountNo){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double total = 0.00;
        try {
            tx = session.beginTransaction();
            //String[] tags = {"11-151-003", "11-170-004"};
            String sql = "select sum(jd.credit - jd.debet) from Journal_details jd inner join journals j on j.id = jd.journal_id  "
                    + " where month(j.date) <= :month and year(j.date) = :year and jd.account_no in(:no) ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("month", month);
            query.setParameter("year", year);
            query.setParameterList("no",accountNo);
            List list = query.list();
            total = Double.parseDouble(list.get(0)!= null ? list.get(0).toString() : "0.00");
            session.flush();
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
    
    
    
}
