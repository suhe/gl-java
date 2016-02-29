/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author suhe
 */
public class Lang {

    /**
     *
     */
    public static String locale = Config.getString("App.locale");
    
    public void setLocale(String var) {
        locale = var;
    }
    
    public String getLocale() {
        return locale;
    }

    public static String getString(String Args) {
        String StrLang;
        try {
            String[] var = Args.split("\\.");
            ResourceBundle config;
            config = ResourceBundle.getBundle("resources/languages/" + locale + "/" + var[0]);
            StrLang = config.getString(var[1]);
        } catch (MissingResourceException ex){
            StrLang = Args;
        }
        return StrLang;
    }
}
