package model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
public class Account implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "AccountNumber")
    private String accountNumber;

    @Column(name = "AccountPassword")
    private String accountPassword;

    @Column(name = "AccountName")
    private String accountName;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "isActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY)
    private List<TradeHistory> tradeHistories;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<TradeHistory> getTradeHistories() {
        return tradeHistories;
    }

    public void setTradeHistories(List<TradeHistory> tradeHistories) {
        this.tradeHistories = tradeHistories;
    }
    
    
    public void themlichsugd(String stk,String tradetype,BigDecimal tien ) {
    	try {
			SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session=sessionFactory.openSession();
				Transaction tr= null;
				try {
					tr= session.beginTransaction();
					CriteriaBuilder builder= session.getCriteriaBuilder();
					CriteriaQuery<Integer> accountquery= builder.createQuery(Integer.class);
					Root<Account> accountroot=accountquery.from(Account.class);
					
					Predicate condition=builder.equal(accountroot.get("accountNumber"), stk);	
					
					//lấy ra id khi có số tk
	                accountquery.select(accountroot.get("id")).where(condition);
	               
	                //gán kết quả vào 1 biến
	                Integer accountId=session.createQuery(accountquery).uniqueResult();

	                if (accountId != null) {	                 
	                	TradeHistory trade=new TradeHistory();
	                	trade.setTradeDate(new Date());
	                	trade.setTradeType(tradetype);
	                	trade.setAmount(tien);
	                	session.save(trade);		                	
	                	Account account= session.get(Account.class, accountId);
	                	trade.setAccount(account);
	                	session.save(trade);
	               
		                tr.commit();           	
	                }else {
	                	System.out.println("lỗi");
	                }
				} catch (Exception e) {
					 if (tr != null) {
		                    tr.rollback();
		                }
		                e.printStackTrace();
		            } finally {
		                if (session != null) {
		                    session.close();
		                }
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
}
