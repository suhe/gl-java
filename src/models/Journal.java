/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
            
    public Boolean isValid(String No) {
        Boolean status = true;
        Integer count = getCountByNumber(No);
        if( count > 0) status = false;
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
    
}