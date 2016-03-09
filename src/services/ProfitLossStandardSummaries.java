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
public class ProfitLossStandardSummaries implements java.io.Serializable {
    private Integer id;
    private String description;
    private Integer ref;
    private Double totalThisMonth;
    private Double totalUntilMonth;
    private String type;
    
    public ProfitLossStandardSummaries() {
    }

    public ProfitLossStandardSummaries(String description,Integer ref,String type, Double totalThisMonth, Double totalUntilMonth) {
        this.description= description;
        this.ref = ref;
        this.type = type;
        this.totalThisMonth = totalThisMonth;
        this.totalUntilMonth = totalUntilMonth;
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
    
    public Double getTotalThisMonth() {
        return this.totalThisMonth;
    }

    public void setTotalThisMonth(Double totalThisMonth) {
        this.totalThisMonth = totalThisMonth;
    }
    
    public Double getTotalUntilMonth() {
        return this.totalUntilMonth;
    }

    public void setTotalUntilMonth(Double totalUntilMonth) {
        this.totalUntilMonth = totalUntilMonth;
    }
    
}
