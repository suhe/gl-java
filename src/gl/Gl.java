/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gl;

/**
 *
 * @author suhe
 */

import helpers.Formula;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Gl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String formula = Formula.field("sum(100:100)");
        //System.out.println(formula);
        try {
            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        gl.layout.Main main =  new gl.layout.Main();
        main.setVisible(true);
    }
    
}
