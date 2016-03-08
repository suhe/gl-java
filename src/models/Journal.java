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
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.Journals;

/**
 *
 * @author suhe
 */
public class Journal {
    private String number;
    private Date date;
    private String type;
    private String description;
    private String other;
    private Double debet;
    private Double credit;
    
    //search
    private Integer id_;
    private String number_;
    private Boolean isSearchDate_;
    private String dateFrom_;
    private String dateTo_;
    
    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.number"), Lang.getString("App.date"),Lang.getString("App.type"),
        Lang.getString("App.description"), Lang.getString("App.check_giro"),
        Lang.getString("App.debet"),Lang.getString("App.credit"),"#"};
   
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String var) {
        this.number = var;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date var) {
        this.date = var;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(String var) {
        this.type = var;
    }
    
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String var) {
        this.description = var;
    }
    
    public String getOther() {
        return other;
    }
    
    public void setOther(String var) {
        this.other = var;
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
    
    public Integer getId_() {
        return this.id_;
    }
    
    public void setId_(Integer var) {
        this.id_ = var;
    }
    
    public String getNumber_() {
        return number_;
    }
    
    public void setNumber_(String var) {
        this.number_ = var;
    }
    
    public Boolean getIsSearchDate_() {
        return isSearchDate_;
    }
    
    public void setIsSearchDate__(Boolean status) {
        this.isSearchDate_ = status;
    }
    
    public String getDateFrom_() {
        return dateFrom_;
    }
    
    public void setDateFrom_(String var) {
        this.dateFrom_ = var;
    }
    
    public String getDateTo_() {
        return dateTo_;
    }
    
    public void setDateTo_(String var) {
        this.dateTo_ = var;
    }
            
    public Boolean isValid(String No) {
        /*Boolean status = true;
        Integer count = getCountByNumber(No);
        if( count > 0) status = false;
        return status;*/
        Boolean status = true;
        Integer countRow;
        Journals rowId;
        countRow = getCountByNumber(No);
        rowId =  getRowById(this.getId_());
        if((getId_() == null) && (countRow != 0)) {
            status = false;
        } else if((getId_() != null) && (rowId != null) ) {
            if((!rowId.getNumber().equals(No)) && (countRow != 0)) {
                status = false;
            }
        } 
        return status;
    }
    
    public Integer getCountByNumber(String Number) {
        Integer count = 0;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Journals.class);
            criteria.add(Restrictions.eq("number",Number));
            count+= ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
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
    
    public Journals getRowById(Integer id) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Journals jn = null;
        try {
            tx = (Transaction) session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Journals.class);
            criteria.add(Restrictions.eq("id",id));
            criteria.setMaxResults(1);
            jn = (Journals) criteria.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return jn;
    }
    
    public Integer getInsertId() {
        Integer Id;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Journals master = new Journals();
            master.setNumber(this.getNumber());
            master.setDate(this.getDate());
            master.setType(this.getType());
            master.setDescription(this.getDescription());
            master.setCheckNumber(this.getOther());
            master.setDebet(this.getDebet());
            master.setCredit(this.getCredit());
            session.save(master);
            session.flush();
            Id = master.getId();
            tx.commit();
            
        }catch (HibernateException ex) {
            Id = 0;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return Id;
    }
    
    public Integer getUpdateId(Integer Key) {
        Integer Id;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Journals master = (Journals) session.get(Journals.class, Key);
            master.setNumber(this.getNumber());
            master.setDate(this.getDate());
            master.setType(this.getType());
            master.setDescription(this.getDescription());
            master.setCheckNumber(this.getOther());
            master.setDebet(this.getDebet());
            master.setCredit(this.getCredit());
            session.update(master);
            session.flush();
            Id = master.getId();
            tx.commit();
            
        }catch (HibernateException ex) {
            Id = 0;
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return Id;
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
            Criteria criteria = session.createCriteria(Journals.class)
                    .setFirstResult(i)
                    .setMaxResults(limit + i);
            if (!this.getNumber_().equals("")) {
                criteria.add(Restrictions.like("number", "%" + getNumber_() + "%"));
            }
            
            if(this.getIsSearchDate_() == true) {
                criteria.add(Restrictions.ge("date",Format.stringToDate(getDateFrom_(), "yyyy-MM-dd") ));
                criteria.add(Restrictions.le("date",Format.stringToDate(getDateTo_(), "yyyy-MM-dd") ));
            }
            
            criteria.addOrder(Order.asc("date"));
            
            List list = criteria.list();
            i++;
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Journals jn = (Journals) iterator.next();
                model.addRow(new Object[]{
                    i,
                    jn.getNumber(),
                    jn.getDate(),
                    jn.getType(),
                    jn.getDescription(),
                    jn.getCheckNumber(),
                    jn.getDebet(),
                    jn.getCredit(),
                    jn.getId()
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
            Criteria criteria = session.createCriteria(Journals.class);
            if (!this.getNumber_().equals("")) {
                criteria.add(Restrictions.like("number", "%" + getNumber_() + "%"));
            }
            
            if(this.getIsSearchDate_() == true) {
                criteria.add(Restrictions.ge("date",Format.stringToDate(getDateFrom_(), "yyyy-MM-dd") ));
                criteria.add(Restrictions.le("date",Format.stringToDate(getDateTo_(), "yyyy-MM-dd") ));
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
    
    public Journals getFirstRow() {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Journals jn = null;
        try {
            tx = (Transaction) session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Journals.class);
            criteria.addOrder(Order.asc("id"));
            criteria.setMaxResults(1);
            jn = (Journals) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return jn;
    }
    
    public Journals getPreviousRow(Integer id) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Journals jn = null;
        try {
            tx = (Transaction) session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Journals.class);
            criteria.add(Restrictions.lt("id",id));
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            jn = (Journals) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return jn;
    }
    
    public Journals getNextRow(Integer id) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Journals jn = null;
        try {
            tx = (Transaction) session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Journals.class);
            criteria.add(Restrictions.gt("id",id));
            criteria.addOrder(Order.asc("id"));
            criteria.setMaxResults(1);
            jn = (Journals) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return jn;
    }
    
    public Journals getLastRow() {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Journals jn = null;
        try {
            tx = (Transaction) session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Journals.class);
            criteria.addOrder(Order.desc("id"));
            criteria.setMaxResults(1);
            jn = (Journals) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            System.out.println(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return jn;
    }
    
    public void delete(Integer Key) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete from Journals where id = :id";
            Query query = session.createQuery(hql);
            query.setInteger("id", Key);
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