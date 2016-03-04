/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author suhe
 */
public class Format {
    public static String Currency(Double var,int digit) {
        String Format = "#,###.";
        for(int i=0; i<digit; i++) {
            Format+= "0";
        }
        
        NumberFormat formatter = new DecimalFormat(Format);
        String Currency = formatter.format(var);
        return Currency;
    }
    
    public static void isDecimal(KeyEvent evt) {
       char c = evt.getKeyChar(); // Get the typed character
        // Don't ignore backspace or delete
        if (c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            // If the key was not a number then discard it (this is a sloppy way to check)
            if (!(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '.')) {
                evt.consume();  // Ignore this key
            }
        }
    }
    
}
