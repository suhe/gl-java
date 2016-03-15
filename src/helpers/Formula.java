/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author suhe
 */
public class Formula {
    public static String main(String args) {
        String[] FORMULA = {"SUM","sum","AVG","avg",""};
        String Formula = null;
        for(Short i = 0;i < FORMULA.length;i++) {
            if(args.contains(FORMULA[i])) {
                Formula = FORMULA[i];
                break;
            } 
        }
        
        return Formula;
    }
    
    public static String separator(String args) {
        String[] SEPARATOR = {"+",":","-","to","\\,"};
        String Separator = null;
        for(Short i = 0;i < SEPARATOR.length;i++) {
            if(args.contains(SEPARATOR[i])) {
                Separator = SEPARATOR[i];
                break;
            } 
        }
        return Separator;
    }
    
    public static Integer[] ref(String args) {
        String formula = main(args);
        args = args.replaceAll(formula,"").trim();
        args = args.replaceAll("\\=","").trim();
        args = args.replaceAll("\\(","").trim();
        args = args.replaceAll("\\)","").trim();
        String[] ref = Format.getArray(args, "\\" + separator(args));
        Integer[] refInt = new Integer[ref.length];
        if(ref.length > 0 ){
            for (int i = 0; i < ref.length; i++) {
                String arg = ref[i];
                refInt[i] = Integer.parseInt(arg.trim());
             }
        }
        return refInt;
    }
    
    public static String[] args(String arg,String separator) {
        String[] arr = Format.getArray(arg,separator);
        return arr;
    }
   
}
