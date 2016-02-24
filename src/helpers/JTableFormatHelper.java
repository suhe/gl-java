/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author suhe
 */
public class JTableFormatHelper extends DefaultTableCellRenderer {

    private Format Formatter;
    public String Type;

    /*
	 *   Use the specified formatter to format the Object
     */
    public JTableFormatHelper(Format formatter,String Type) {
        this.Formatter = formatter;
        this.Type = Type;
    }

    @Override
    public void setValue(Object value) {
        //  Format the Object before setting its value in the renderer
        
        try {
            if (value != null) {
                switch(Type) { 
                    case "ACCOUNTING" : Formatter = new DecimalFormat("#,###.00");
                    break;
                    default : Formatter = new DecimalFormat("#,###");break;
                }
                
                value = Formatter.format(value);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        super.setValue(value);
    }
    
    /*
    *  Use the default currency formatter for the default locale
    */
    public static JTableFormatHelper Accounting() {
        return new JTableFormatHelper(NumberFormat.getCurrencyInstance(),"ACCOUNTING");
    }
}
