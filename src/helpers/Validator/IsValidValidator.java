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
public class IsValidValidator extends BaseValidator {
    public static Boolean isValid = false;
    public IsValidValidator(JDialog parent, JTextField c, String message, Boolean validate) {
        super(parent, c, message);
        isValid = validate;
    }

    @Override
    protected boolean validationCriteria(JComponent c) {
        return isValid;
    }
}
