/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import helpers.Lang;
import java.util.Iterator;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.Roles;

/**
 *
 * @author suhe
 */
public class Role {

    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.name"), Lang.getString("App.description"), "#"};
    
    private static Integer id = null;
    private static String name = "";
    private static String description = "";
    private static String nameFind = "";
    private static Boolean isEdit = false;
    public JProgressBar jProgressBarStatus = new JProgressBar(0, 100);

    public Role() {

    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean status) {
        isEdit = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer value) {
        id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }
    
    public String getNameFind() {
        return nameFind;
    }

    public void setNameFind(String value) {
        nameFind = value;
    }
   
    public Boolean isValid(String name) {
        Boolean status = true;
        Roles role;
        Roles rowId;
        role = getSingleRowByName(name);
        rowId =  getSingleRow(this.getId());
        if((!getIsEdit()) && (role != null)) {
            status = false;
        } else if(getIsEdit() && rowId != null ) {
            if((!rowId.getName().equals(name)) && (role != null)) {
                status = false;
            }
        } 
        return status;
    }
    
    public Boolean isExists(String name) {
        Boolean status = true;
        Roles role;
        role = getSingleRowByName(name);
        if(role == null) status = false;
        return status;
    }
    
    public DefaultTableModel getListTableModel(Integer offset, final Integer limit) {

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
            Criteria criteria = session.createCriteria(Roles.class)
                    .setFirstResult(i)
                    .setMaxResults(limit + i);

            //search provider
            if (getNameFind() != null) {
                criteria.add(Restrictions.like("name", "%" + getNameFind()+ "%"));
            }
          
            List list = criteria.list();
            i++;
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Roles role = (Roles) iterator.next();
                model.addRow(new Object[]{
                    i,
                    role.getName(),
                    role.getDescription(),
                    role.getId()
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

    public Integer getCountTableModel() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = 0;

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Roles.class);

            if (getNameFind() != null) {
                criteria.add(Restrictions.like("name", "%" + getNameFind() + "%"));
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

    public Roles getSingleRow(Integer Id) {
        Roles role = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Roles.class)
                    .add(Restrictions.eq("id", Id));
            role = (Roles) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return role;
    }
    
    public Roles getSingleRowByName(String name) {
        Roles role = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Roles.class)
                    .add(Restrictions.eq("name", name));
            role = (Roles) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return role;
    }
    
    public List getRowList() {
        List list;
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Roles.class);
            criteria.addOrder(Order.asc("name")); 
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

    public void saveOrUpdate() {
        if (getIsEdit() == true) {
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
            Roles role = new Roles();
            role.setName(getName());
            role.setDescription(getDescription());
            session.save(role);
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
            Roles role = (Roles) session.get(Roles.class, getId());//1
            role.setName(getName());
            role.setDescription(getDescription());
            session.update(role);
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

    public void delete(Integer Key) {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Roles role = (Roles) session.get(Roles.class, Key);
            session.delete(role);
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
