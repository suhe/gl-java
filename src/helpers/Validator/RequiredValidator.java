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
 * @author BDO-IT
 */
public class RequiredValidator extends BaseValidator {
    public RequiredValidator(JDialog parent, JTextField c, String message) {
        super(parent, c, message);
    }
	
    @Override
    protected boolean validationCriteria(JComponent c) {
        return !((JTextField)c).getText().equals("");
    }
}
