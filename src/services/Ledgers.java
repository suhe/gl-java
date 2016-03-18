package services;
// Generated Feb 25, 2016 9:15:53 PM by Hibernate Tools 4.3.1

import java.util.Date;


/**
 * Accounts generated by
 */
public class Ledgers implements java.io.Serializable {

    private Integer id;
    private String accountNo;
    private String accountName;
    private String type;
    private Date date;
    private String description;
    private Double debet;
    private Double credit;
    private Double saldo;
    private String accountDescription;
    private String periodeDescription;
    

    public Ledgers() {
    }

    public Ledgers(String accountNo, String accountName,String type,Date date,String description, Double debet, Double credit,Double saldo,String accountDescription,String periodeDescription) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.type = type;
        this.date = date;
        this.description = description;
        this.debet = debet;
        this.credit = credit;
        this.saldo = saldo;
        this.accountDescription = accountDescription;
        this.periodeDescription = periodeDescription;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
    public void setAccountDescription(String accountDescription) {
        this.accountDescription = accountDescription;
    }

    public String getAccountDescription() {
        return this.accountDescription;
    }

    public void setPeriodeDescription(String periodeDescription) {
        this.periodeDescription = periodeDescription;
    }

    public String getPeriodeDescription() {
        return this.periodeDescription;
    }

}
