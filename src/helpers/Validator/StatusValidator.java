/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers.Validator;

/**
 *
 * @author suhe
 */
public interface StatusValidator {
    void validateFailed();  // Called when a component has failed validation.
    void validatePassed();  // Called when a component has passed validation.
}


