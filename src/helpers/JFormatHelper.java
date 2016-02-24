/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author suhe
 */
public class JFormatHelper {
    public static String Currency(Double var,int digit) {
        String Format = "#,###.";
        for(int i=0; i<digit; i++) {
            Format+= "0";
        }
        
        NumberFormat formatter = new DecimalFormat(Format);
        String Currency = formatter.format(var);
        return Currency;
    }
    
}
