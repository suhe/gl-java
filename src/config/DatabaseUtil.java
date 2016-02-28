/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.swing.JOptionPane;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author suhe
 */
public class DatabaseUtil {

    //private static final SessionFactory sessionFactory;
    private static SessionFactory sessionFactory;

    //public static void configure(Configuration configuration) {
      //  sessionFactory = configuration.buildSessionFactory();
    //}

    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration().configure("/config/Database.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            JOptionPane.showMessageDialog(null, "Connection error,please advice administrator , error code" + ex, "Database Connection", JOptionPane.ERROR_MESSAGE);    
            //System.err.println("Gagal Konek." + ex);
            //throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    
    
}
