package services;
// Generated Feb 25, 2016 9:15:53 PM by Hibernate Tools 4.3.1



/**
 * JournalDetails generated by hbm2java
 */
public class JournalDetails  implements java.io.Serializable {


     private Integer id;
     private Integer journalId;
     private Integer order;
     private Integer accountId;
     private String accountNo;
     private String description;
     private Double debet;
     private Double credit;

    public JournalDetails() {
    }

    public JournalDetails(Integer journalId, Integer order, Integer accountId, String accountNo, String description, Double debet, Double credit) {
       this.journalId = journalId;
       this.order = order;
       this.accountId = accountId;
       this.accountNo = accountNo;
       this.description = description;
       this.debet = debet;
       this.credit = credit;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getJournalId() {
        return this.journalId;
    }
    
    public void setJournalId(Integer journalId) {
        this.journalId = journalId;
    }
    public Integer getOrder() {
        return this.order;
    }
    
    public void setOrder(Integer order) {
        this.order = order;
    }
    public Integer getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    public String getAccountNo() {
        return this.accountNo;
    }
    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getDebet() {
        return this.debet;
    }
    
    public void setDebet(Double debet) {
        this.debet = debet;
    }
    public Double getCredit() {
        return this.credit;
    }
    
    public void setCredit(Double credit) {
        this.credit = credit;
    }




}

