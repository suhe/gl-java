/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

/**
 *
 * @author suhe
 */
public class BalanceSheetSummaries implements java.io.Serializable {
    private Integer id;
    private String description;
    private Integer ref;
    private Double total;
    private String type;
    
    public BalanceSheetSummaries() {
    }

    public BalanceSheetSummaries(String description,Integer ref,String type, Double total) {
        this.description= description;
        this.ref = ref;
        this.type = type;
        this.total = total;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getRef() {
        return this.ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }
    
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    
    
}
