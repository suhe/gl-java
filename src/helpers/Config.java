/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import static helpers.Lang.getString;
import java.util.ResourceBundle;

/**
 *
 * @author suhe
 */
public class Config {

    /**
     *
     * @param Args
     * @return
     */
    public static String getString(String Args){
        String[] var = Args.split("\\.");
        ResourceBundle config;
        config = ResourceBundle.getBundle("config/" + var[0]);
        return config.getString(var[1]);
    } 
    
     public static String[] getArray(String Args) {
        String [] var = getString(Args).split(";");
        return var;
    }
}
