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
import java.math.BigInteger;
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
import org.hibernate.transform.Transformers;
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
                        jd.getAccounts().getNo(),
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
            //details.setJournalId(this.getJournalId());
            //details.setAccountId(this.getAccountId());
            //details.setAccountNo(this.getAccountNo());
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
    
    public Double getSumByThisMonth(String year,String month,String[] accountNo,String calc){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double total = 0.00;
        String calcSelect = "Debet - Credit".equals(calc) ? "jd.debet - jd.credit" : "jd.credit - jd.debet";
        try {
            tx = session.beginTransaction();
            String sql = "select sum(" + calcSelect + ") from Journal_details jd inner join journals j on j.id = jd.journal_id  "
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
    
    public Double getSumByUntilMonth(String year,String month,String[] accountNo,String calc){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double total = 0.00;
        String calcSelect = "Debet - Credit".equals(calc) ? "jd.debet - jd.credit" : "jd.credit - jd.debet";
        try {
            tx = session.beginTransaction();
            String sql = "select sum(" + calcSelect + ") from journal_details jd inner join journals j on j.id = jd.journal_id  "
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
    
    public JournalDetails getSumBalanceByUntilDate(String year,Date date,String accountNo) {
        JournalDetails jds = null;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = " select sum(jd.debet) as debet,sum(jd.credit) as credit "
                    + " from journal_details jd "
                    + " inner join journals j on j.id = jd.journal_id "
                    + " where j.date < :date and year(j.date) = :year and jd.account_no = :no ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("date", date);
            query.setParameter("year", year);
            query.setParameter("no",accountNo);
            query.setResultTransformer(Transformers.aliasToBean(JournalDetails.class));
            session.flush();
            tx.commit();
            jds = (JournalDetails) query.uniqueResult();
            
        }catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        
        return jds;
    }
    
    public JournalDetails getSumBalanceByDate(Date dateFrom,Date dateTo,String accountNo) {
        JournalDetails jds = null;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String sql = " select sum(jd.debet) as debet,sum(jd.credit) as credit "
                    + " from journal_details jd "
                    + " inner join journals j on j.id = jd.journal_id  "
                    + " where (j.date>= :dateFrom and j.date <= :dateTo) and jd.account_no = :no ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("dateFrom", dateFrom);
            query.setParameter("dateTo", dateFrom);
            query.setParameter("no",accountNo);
            query.setResultTransformer(Transformers.aliasToBean(JournalDetails.class));
            session.flush();
            tx.commit();
            jds = (JournalDetails) query.uniqueResult();
            
        }catch (HibernateException e) {
            System.out.println(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        
        return jds;
    }
    
    public Double[] getSumByUntilDate(String year,Date dateTo,String accountNo){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double[] total = new Double[2];
        try {
            tx = session.beginTransaction();
            String sql = "select sum(jd.debet),sum(jd.credit) from journal_details jd "
                    + "inner join journals j on j.id = jd.journal_id  "
                    + " where j.date < :date and year(j.date) = :year and jd.account_no = :no ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("date", dateTo);
            query.setParameter("year", year);
            query.setParameter("no",accountNo);
            List list = query.list();
            Iterator it = list.iterator();
            Double total1 = 0.00;
            Double total2 = 0.00;
            if(it.hasNext() == true) {
                
                while (it.hasNext()) {
                    Object obj[] = (Object[]) it.next();
                    System.out.println("Result Sum  :" + obj[0]);
                    total1 += obj[0] != null ? Double.parseDouble(obj[0].toString()) : 0.00;
                    total2 += obj[1] != null ? Double.parseDouble(obj[1].toString()) : 0.00;
                }
                
                total[0] = total1;
                total[1] = total2;
            } else {
                total[0] = 0.00;
                total[1] = 0.00;
            }
            
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            total[0] = 0.00;
            total[1] = 0.00;
            System.out.println(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            
        } finally {
            session.close();
        }
        
        return total;
    }
    
    public Double getBalanceSheetSummary(Date periode,String year,String accountNo,String calc){
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double total = null;
        String[] args;
        accountNo = accountNo.trim();
        String calcSelect = "Debet - Credit".equals(calc.trim()) ? "jd.debet - jd.credit" : "jd.credit - jd.debet";
        String sql;
        SQLQuery query;
        try {
            tx = session.beginTransaction();
            System.out.println("Account Data : " + accountNo);
            if (accountNo.contains("to")){
                args = Formula.args(accountNo.trim(),"to");
                String arg1 = args[0].trim();
                String arg2 = args[1].trim();
                System.out.println("Data ke 0 : " + args[0]);
                System.out.println("Data ke 1 : " + arg1);
                
                sql= "select sum(" + calcSelect + ") from journal_details jd inner join journals j on j.id = jd.journal_id  "
                    + " where j.date <= :date and year(j.date) = :year and jd.account_no BETWEEN :arg0 and :arg1 ";
                query = session.createSQLQuery(sql);
                query.setParameter("date", periode);
                query.setParameter("year", year);
                query.setParameter("arg0", arg1);
                query.setParameter("arg1", arg2);
                total = (Double) query.uniqueResult();
                
            } else {
                args = Formula.args(accountNo.trim(),"\\,");
                sql= "select sum(" + calcSelect + ") from journal_details jd inner join journals j on j.id = jd.journal_id  "
                    + " where j.date <= :date and year(j.date) = :year and jd.account_no in(:no) ";
                query = session.createSQLQuery(sql);
                query.setParameter("date", periode);
                query.setParameter("year", year);
                query.setParameterList("no",args);
                total = (Double) query.uniqueResult();
            }
            
            session.flush();
            tx.commit();
        } catch (HibernateException e) {
            total = 0.00;
            System.out.println(e.getMessage());
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
    
    public Double[] GetProfitLossSummary(String accountNo,Date dateFrom,Date dateTo) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Double[] total = new Double[2];

        try {
            tx = session.beginTransaction();
            String sql;
            SQLQuery query;
            sql = "select sum(jd.debet),sum(jd.credit) from journal_details jd "
                + "inner join journals j on j.id = jd.journal_id "
                + "where (j.date >= :datefrom and  j.date <=:dateto) and jd.account_no = :account";
            query = session.createSQLQuery(sql);
            query.setParameter("account", accountNo);
            query.setParameter("datefrom", dateFrom);
            query.setParameter("dateto", dateTo);
            List list = query.list();
            Iterator it = list.iterator();
            Double total1 = 0.00;
            Double total2 = 0.00;
            while (it.hasNext()) {
                Object obj[] = (Object[]) it.next();
                try {
                    total1 += Double.parseDouble(obj[0].toString());
                    total2 += Double.parseDouble(obj[1].toString());
                } catch(Exception e) {
                    total1 = null;
                    total2 = null;
                }
            }
            total[0] = total1;
            total[1] = total2;
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
    
    public List getRowsByList(String accountNoFrom, String accountNoTo, Date dateFrom, Date dateTo) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        JournalDetails jds;
        List list = null;
        try {
            tx = session.beginTransaction();
            String sql = " select jd.id,a.no,a.name,j.date,jd.description,jd.debet,jd.credit"
                    + " from journal_details jd "
                    + " inner join journals j on j.id = jd.journal_id "
                    + " inner join accounts a on a.no = jd.account_no "
                    + " where (j.date>= :dateFrom and j.date <= :dateTo) and (a.no >= :accountFrom and  a.no <= :accountTo) "
                    + " order by a.no ASC,j.date ASC ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("dateFrom", dateFrom);
            query.setParameter("dateTo", dateTo);
            query.setParameter("accountFrom", accountNoFrom);
            query.setParameter("accountTo", accountNoTo);
            query.addEntity("jd",JournalDetails.class);
            query.addJoin("a","jd.accounts");
            query.addJoin("j","j.journals");
            //query.setResultTransformer(Transformers.aliasToBean(JournalDetails.class));
            list = query.list();
            tx.commit();
        } catch (HibernateException e) {
            list = null;
            System.out.println(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return list;
    }
    
    public Integer getCount(String accountNoFrom, String accountNoTo, Date dateFrom, Date dateTo) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = null;

        try {
            tx = session.beginTransaction();
            String sql = " select count(*) "
                    + " from journal_details jd "
                    + " inner join journals j on j.id = jd.journal_id "
                    + " inner join accounts a on a.no = jd.account_no "
                    + " where (j.date>= :dateFrom and j.date <= :dateTo) and (a.no >= :accountFrom and  a.no <= :accountTo) "
                    + " order by a.no ASC,j.date ASC ";
            SQLQuery query = session.createSQLQuery(sql);
            //query.addJoin("entity1.entity2",""); 
            query.setParameter("dateFrom", dateFrom);
            query.setParameter("dateTo", dateTo);
            query.setParameter("accountFrom", accountNoFrom);
            query.setParameter("accountTo", accountNoTo);
            query.addEntity("jd",JournalDetails.class);
            query.addJoin("a","jd.accounts");
            query.addJoin("j","j.journals");
            count = ((BigInteger) query.uniqueResult()).intValue();
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