/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.Validator;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author suhe
 */
public class ValueRequiredValidator extends BaseValidator {
    public static String value;
     
    public ValueRequiredValidator (JDialog parent, JTextField c, String message,String str) {
        super(parent, c, message);
        value = str;
    }

    @Override
    protected boolean validationCriteria(JComponent c) {
        return ((JTextField)c).getText().equals(value);
    }
    
    
}
