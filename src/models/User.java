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
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import services.Roles;
import services.Users;

/**
 *
 * @author suhe
 */
public class User {
    String[] TABLE_COLUMN_NAME = {Lang.getString("App.no"),
        Lang.getString("App.username"), Lang.getString("App.role"), "#"};
    
    private static Integer id = null;
    private static String username = "";
    private static Integer roleId = null;
    private static String roleName = "";
    private static Boolean isEdit = false;
    private static String usernameFind = "";
    
    public User() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String value) {
        username = value;
    }
    
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer value) {
        roleId = value;
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String value) {
        roleName = value;
    }
    
    public String getUsernameFind() {
        return usernameFind;
    }

    public void setUsernameFind(String value) {
        usernameFind = value;
    }

    public boolean authenticateUser(String username, String password) {
        Users user = getUserByUserId(username);
        if ((user != null && user.getUsername().equals(username)) && (user.getPassword().equals(password))) {
            return true;
        } else {
            return false;
        }
    }

    public Users getUserByUserId(String username) {
        Session session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Users user = null;
        try {
            tx = (Transaction) session.getTransaction();
            tx.begin();
            Query query = session.createQuery("from Users where username='" + username + "'");
            user = (Users)query.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return user;
    }
    
    public Integer getCountTableModel() {
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Integer count = 0;

        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Users.class);

            if (getUsernameFind() != null) {
                criteria.add(Restrictions.like("name", "%" + getUsernameFind() + "%"));
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
            Criteria criteria = session.createCriteria(Users.class)
                    .setFirstResult(i)
                    .setMaxResults(limit + i);

            //search provider
            if (getUsernameFind() != null) {
                criteria.add(Restrictions.like("username", "%" + getUsernameFind() + "%"));
            }
          
            List list = criteria.list();
            i++;
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                Users user = (Users) iterator.next();
                model.addRow(new Object[]{
                    i,
                    user.getUsername(),
                    user.getRoles().getName(),
                    user.getId()
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
    
    public Users getSingleRow(Integer Id) {
        Users users = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Users.class)
                    .add(Restrictions.eq("id", Id));
            users = (Users) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return users;
    }
    
    public Users getSingleRowByUserName(String username) {
        Users users = null;
        Session session;
        session = DatabaseUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Users.class)
                    .add(Restrictions.eq("username", username));
            
            users = (Users) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }

        return users;
    }
    
    public Boolean isValid(String username) {
        Boolean status = true;
        Users users;
        Users rowId;
        users = getSingleRowByUserName(username);
        rowId =  getSingleRow(this.getId());
        if((!getIsEdit()) && (users != null)) {
            status = false;
        } else if(getIsEdit() && rowId != null ) {
            if((!rowId.getUsername().equals(username)) && (users != null)) {
                status = false;
            }
        } 
        return status;
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
            Users users = new Users();
            users.setUsername(getUsername());
            users.setRoleId(1);
            session.save(users);
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
            Users users = (Users) session.get(Users.class, getId());//1
            users.setUsername(getUsername());
            users.setRoleId(1);
            session.update(users);
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
            Users users = (Users) session.get(Users.class, Key);
            session.delete(users);
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
