/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import config.DatabaseUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import services.Users;

/**
 *
 * @author suhe
 */
public class User {

    public boolean authenticateUser(String username, String password) {
        Users user = getUserByUserId(username);
        if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
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

}
