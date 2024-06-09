package model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import util.HibernateUtil;

public class Chucnang {
	
	
	
	public void dangky(String accountnumber, String password, String name, String sdt) {
	    try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory()) {
	        if (sessionFactory != null) {
	            try (Session session = sessionFactory.openSession()) {
	                CriteriaBuilder builder = session.getCriteriaBuilder();
	                CriteriaQuery<Account> query = builder.createQuery(Account.class);
	                Root<Account> root = query.from(Account.class);
	                query = query.select(root); // cái này hiện tất cả

	                //cái này là điều kiện
	                Predicate condition = builder.and(
	                        builder.equal(root.get("phone"), sdt),
	                        builder.equal(root.get("accountPassword"), password)
	                );
	                //cái này thực hiện cấu lệnh kiểm tra với điều kiện trên
	                query.select(root).where(condition);

	                Account account = session.createQuery(query).uniqueResult();
	                if (account == null) {
	                    // Tài khoản không tồn tại, thêm mới
	                    Account newAccount = new Account();
	                    newAccount.setAccountNumber(accountnumber);
	                    newAccount.setAccountPassword(password);
	                    newAccount.setAccountName(name);
	                    newAccount.setPhone(sdt);
	                    newAccount.setIsActive(true);

	                    Transaction tr = session.beginTransaction(); // Bắt đầu giao dịch
	                    session.save(newAccount);
	                    tr.commit(); // Commit giao dịch
	                    System.out.println("Đăng ký thành công!");
	                } else {
	                    // Tài khoản đã tồn tại
	                    System.out.println("Tài khoản hoặc mật khẩu đã tồn tại");
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void dangnhap(String sdt,String matkhau) {
		try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session= sessionFactory.openSession();
				Transaction tr= null;
				try {
					tr =session.beginTransaction();
					
					CriteriaBuilder builder= session.getCriteriaBuilder();
					CriteriaQuery<Account> query= builder.createQuery(Account.class);
					Root root=query.from(Account.class);
					
					Predicate condition= builder.and(
						builder.equal(root.get("phone"), sdt),
						builder.equal(root.get("accountPassword"), matkhau)
							);
					
					query.select(root).where(condition);
					
					Account account =session.createQuery(query).uniqueResult();
					if(account!=null) {
						System.out.println("đăng nhập thanh công");
					}else {
						System.out.println("tài khoản hoặc mật khẩu không đúng");
					}
					
					tr.commit();
				} catch (Exception e) {
	                  if (tr != null) {
	                	  tr.rollback();
		              }
		              e.printStackTrace();
		         } finally {
		              session.close();		          
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public BigDecimal sodu(String mk) {
	    BigDecimal balance = BigDecimal.ZERO;
	    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	    if (sessionFactory != null) {
	        Session session = null;
	        Transaction tr = null;
	        try {
	            session = sessionFactory.openSession();
	            tr = session.beginTransaction();

	            CriteriaBuilder cb = session.getCriteriaBuilder();

	            // Tính tổng số tiền nạp vào tài khoản
	            CriteriaQuery<BigDecimal> depositQuery = cb.createQuery(BigDecimal.class);
	            Root<TradeHistory> depositRoot = depositQuery.from(TradeHistory.class);
	            depositQuery.select(cb.sum(depositRoot.get("amount")))
	                    .where(cb.and(
	                            cb.equal(depositRoot.get("account").get("accountPassword"),mk),
	                            cb.equal(depositRoot.get("tradeType"), "nạp tiền")));

	            BigDecimal sumDeposit = session.createQuery(depositQuery).uniqueResult();
	            if(sumDeposit==null) {
	            	sumDeposit=BigDecimal.ZERO;
	            }

	            // Tính tổng số tiền rút khỏi tài khoản
	            CriteriaQuery<BigDecimal> withdrawQuery = cb.createQuery(BigDecimal.class);
	            Root<TradeHistory> withdrawRoot = withdrawQuery.from(TradeHistory.class);
	            withdrawQuery.select(cb.sum(withdrawRoot.get("amount")))
	                    .where(cb.and(
	                            cb.equal(withdrawRoot.get("account").get("accountPassword"), mk),
	                            cb.equal(withdrawRoot.get("tradeType"), "rút tiền")));

	            BigDecimal sumWithdraw = session.createQuery(withdrawQuery).uniqueResult();
	            if(sumWithdraw==null) {
	            	sumWithdraw=BigDecimal.ZERO;
	            }

	            // Tính tổng số tiền chuyển vào tài khoản
	            CriteriaQuery<BigDecimal> transferInQuery = cb.createQuery(BigDecimal.class);
	            Root<TradeHistory> transferInRoot = transferInQuery.from(TradeHistory.class);
	            transferInQuery.select(cb.sum(transferInRoot.get("amount")))
	                    .where(cb.and(
	                            cb.equal(transferInRoot.get("account").get("accountPassword"), mk),
	                            cb.equal(transferInRoot.get("tradeType"), "nhận tiền")));

	            BigDecimal sumTransferIn = session.createQuery(transferInQuery).uniqueResult();
	            if(sumTransferIn==null) {
	            	sumTransferIn=BigDecimal.ZERO;
	            }

	            // Tính tổng số tiền chuyển ra khỏi tài khoản
	            CriteriaQuery<BigDecimal> transferOutQuery = cb.createQuery(BigDecimal.class);
	            Root<TradeHistory> transferOutRoot = transferOutQuery.from(TradeHistory.class);
	            transferOutQuery.select(cb.sum(transferOutRoot.get("amount")))
	                    .where(cb.and(
	                            cb.equal(transferOutRoot.get("account").get("accountPassword"), mk),
	                            cb.equal(transferOutRoot.get("tradeType"), "chuyển tiền")));

	            BigDecimal sumTransferOut = session.createQuery(transferOutQuery).uniqueResult();
	            if(sumTransferOut==null) {
	            	sumTransferOut=BigDecimal.ZERO;
	            }

	            // Tính toán số dư cuối cùng
	            balance = sumDeposit.add(sumTransferIn).subtract(sumWithdraw).subtract(sumTransferOut);

	            tr.commit();
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
	    return balance;
	}

    public void chuyetien(String taikhoangui,BigDecimal tiengui,String taikhoannhan) {
    	try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session= sessionFactory.openSession();
				Transaction tr=null;
				try {
					tr =session.beginTransaction();
					
					CriteriaBuilder builder= session.getCriteriaBuilder();
					CriteriaQuery<Account> query= builder.createQuery(Account.class);
					Root root =query.from(Account.class);
					query = query.select(root);
					
					//điều kiện
					Predicate condution=builder.equal(root.get("accountNumber"), taikhoannhan);

					query.select(root).where(condution);
					
					Account account= session.createQuery(query).uniqueResult();
					
					if(account!=null) {
						BigDecimal sdtkgui =sodu(taikhoangui);				
						if(tiengui.compareTo(sdtkgui)<=0) {
							String chuyentien="chuyển tiền";
							String nhantien="nhận tiền";

							themlichsugd(taikhoangui, chuyentien, tiengui);
					
							themlichsugd(taikhoannhan, nhantien, tiengui);

							tr.commit();
						}else {
							System.out.println("số dư trong tài khoản không đủ");
						}
						
						
					}else {
						System.out.println("không tồn tại tài khoản này");
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
		}
    }
    public void naptien(String tknap,BigDecimal tiennap) {
    	try {
			SessionFactory sessionFactory= HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session= sessionFactory.openSession();
				Transaction tr=null;
				try {
					tr = session.beginTransaction();
					CriteriaBuilder builder= session.getCriteriaBuilder();
					CriteriaQuery<Account> query= builder.createQuery(Account.class);
					Root root= query.from(Account.class);
					
					
					Predicate condition=builder.equal(root.get("accountNumber"), tknap);
					query.select(root).where(condition);
					
					Account account=session.createQuery(query).uniqueResult();
					String naptien="nạp tiền";
					if(account!=null) {
						themlichsugd(tknap, naptien, tiennap);
					}else {
						System.out.println("không tồn tại tài khoản này");
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
			
		}
    }
    
    public void ruttien(String tkrut,BigDecimal tienrut) {
    	try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session= sessionFactory.openSession();
				Transaction tr=null;
				try {
					tr =session.beginTransaction();
					CriteriaBuilder builder= session.getCriteriaBuilder();
					CriteriaQuery<Account> query = builder.createQuery(Account.class);
					Root root=query.from(Account.class);
					
					Predicate condition= builder.equal(root.get("accountNumber"),tkrut);
					query.select(root).where(condition);
					
					Account account=session.createQuery(query).uniqueResult();
					String ruttien="rút tiền";
					if(account!=null) {
						BigDecimal sdtkr=sodu(tkrut);
						if(tienrut.compareTo(sdtkr)<=0) {
							themlichsugd(tkrut, ruttien, tienrut);
							System.out.println("rút rành công");
						}else {
							System.out.println("số dư của bạn không đủ");							
						}
						
					}else {
						System.out.println("không tồn tại tài khoản này");
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
			
		}
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
					Root accountroot=accountquery.from(Account.class);
					
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
    
    public void hamhienlsdg(String mk) {
        try (SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session = sessionFactory.openSession()) {

            if (sessionFactory != null) {
                Transaction tr = null;
                try {
                    tr = session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
                    Root<Account> root = query.from(Account.class);

                    Predicate condition = builder.equal(root.get("accountPassword"), mk);
                    query.select(root.get("id")).where(condition);

                    Integer accountid = session.createQuery(query).uniqueResult();

                    if (accountid != null) {
                        CriteriaQuery<TradeHistory> tradeQuery = builder.createQuery(TradeHistory.class);
                        Root<TradeHistory> tradeRoot = tradeQuery.from(TradeHistory.class);
                        tradeQuery.select(tradeRoot).where(builder.equal(tradeRoot.get("account"), accountid));
                        
                        List<TradeHistory> tradeHistories = session.createQuery(tradeQuery).getResultList();

                        // In tiêu đề bảng
                        System.out.printf("%-10s %-20s %-15s %-10s%n", "ID", "Trade Date", "Trade Type", "Amount");
                        System.out.println("-----------------------------------------------------------");

                        // In thông tin lịch sử giao dịch sử dụng lambda
                        tradeHistories.forEach(trde -> 
                            System.out.printf("%-10d %-20s %-15s %-10.2f%n", 
                                trde.getId(), 
                                trde.getTradeDate().toString(), 
                                trde.getTradeType(), 
                                trde.getAmount())
                        );
                    }
                    tr.commit();
                } catch (Exception e) {
                    if (tr != null) {
                        tr.rollback();
                    }
                    e.printStackTrace();
                } finally {
					if(session!=null) {
						session.close();
					}
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hamthongke() {
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            if (sessionFactory != null) {
                Session session = sessionFactory.openSession();
                Transaction tr = null;
                try {
                    tr = session.beginTransaction();
                    CriteriaBuilder builder = session.getCriteriaBuilder();
                    
                    CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
                    Root<Account> accountRoot = query.from(Account.class);
                    Root<TradeHistory> tradeRoot = query.from(TradeHistory.class);
                    
                    // Điều kiện join giữa hai bảng
                    Predicate joinCondition = builder.equal(accountRoot.get("id"), tradeRoot.get("account"));

                    // Thêm điều kiện join vào câu truy vấn
                    query.where(joinCondition);
                    
                    // Lựa chọn các cột cần thiết từ các bảng
                    query.multiselect(
                        accountRoot, // Thực thể Account
                        builder.sum(
                            builder.<BigDecimal>selectCase()
                                   .when(builder.equal(tradeRoot.get("tradeType"), "nạp tiền"), tradeRoot.get("amount").as(BigDecimal.class))
                                   .otherwise(BigDecimal.ZERO)
                        ),
                        builder.sum(
                            builder.<BigDecimal>selectCase()
                                   .when(builder.equal(tradeRoot.get("tradeType"), "nhận tiền"), tradeRoot.get("amount").as(BigDecimal.class))
                                   .otherwise(BigDecimal.ZERO)
                        ),
                        builder.sum(
                            builder.<BigDecimal>selectCase()
                                   .when(builder.equal(tradeRoot.get("tradeType"), "chuyển tiền"), tradeRoot.get("amount").as(BigDecimal.class))
                                   .otherwise(BigDecimal.ZERO)
                        ),
                        builder.sum(
                            builder.<BigDecimal>selectCase()
                                   .when(builder.equal(tradeRoot.get("tradeType"), "rút tiền"), tradeRoot.get("amount").as(BigDecimal.class))
                                   .otherwise(BigDecimal.ZERO)
                        )
                    );

                    query.groupBy(accountRoot.get("id"));
                    
                    // Thực hiện truy vấn và nhận kết quả
                    List<Object[]> results = session.createQuery(query).getResultList();
                    
                    // Xử lý kết quả
                    System.out.println("+---------------------------------------------------------------------------------------------------------+");
                    System.out.println("| ID |  Account Number |  Account Name |  Phone  | Deposit | Receive  |  Transfer  |  Withdraw  | Balance |");
                    System.out.println("+---------------------------------------------------------------------------------------------------------+");

                    results.forEach(result -> {
                        Account account = (Account) result[0];
                        BigDecimal deposit = (BigDecimal) result[1];
                        BigDecimal receive = (BigDecimal) result[2];
                        BigDecimal transfer = (BigDecimal) result[3];
                        BigDecimal withdraw = (BigDecimal) result[4];
                        BigDecimal balance = deposit.subtract(withdraw).add(receive).subtract(transfer);

                        System.out.printf("| %-2d | %-14s | %-12s | %-6s | %-8s | %-8s | %-9s | %-9s | %-8s |%n",
                                          account.getId(), account.getAccountNumber(), account.getAccountName(), account.getPhone(),
                                          deposit, receive, transfer, withdraw, balance);
                    });

                    System.out.println("+---------------------------------------------------------------------------------------------------------+");

                    tr.commit();
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
            e.printStackTrace();
        }
    }


    public void thaydoimatkhau(String mkmoi,String mkc) {
    	try {
			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			if(sessionFactory!=null) {
				Session session=sessionFactory.openSession();
				Transaction tr=null;
				try {
					
					CriteriaBuilder builder= session.getCriteriaBuilder();
					CriteriaQuery<Integer> query= builder.createQuery(Integer.class);
					Root root= query.from(Account.class);
					
					Predicate condition=builder.equal(root.get("AccountPassword"), mkc);
					
					query.select(root.get("id")).where(condition);
					Integer accountid=session.createQuery(query).uniqueResult();
					
					if(accountid!=null) {
						Account tk= session.get(Account.class, accountid);
						tk.setAccountPassword(mkmoi);
						session.update(tk);
					}else {
						System.out.println("Mật khẩu này đã tồn tại");
					}
					
				} catch (Exception e) {
					if(tr!=null) {
						tr.rollback();
					}
					e.printStackTrace();
				} finally {
					if(session!=null) {
						session.close();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
    
//    public static void main(String[] args) {
//    	Chucnang chucnang = new Chucnang();
////        int userId = 1; // Ví dụ userId
////        BigDecimal balance = chucnang.sodu(userId);
////        System.out.println("Số dư của người dùng với ID " + userId + " là: " + balance);
//        
////        Scanner sc= new Scanner(System.in);
////        System.out.println("nhập số điện thoại");
////        String sdt= sc.nextLine();
////        System.out.println("nhập mât khẩu");
////        String matkhau= sc.nextLine();
////        
////        chucnang.dangnhap(sdt, matkhau);
////    	 Scanner scanner = new Scanner(System.in);
////
////         System.out.print("Nhập số tài khoản: ");
////         String accountNumber = scanner.nextLine();
////
////         System.out.print("Nhập mật khẩu: ");
////         String password = scanner.nextLine();
////
////         System.out.print("Nhập tên: ");
////         String name = scanner.nextLine();
////
////         System.out.print("Nhập số điện thoại: ");
////         String phoneNumber = scanner.nextLine();
////         
////         chucnang.dangky(accountNumber, password, name, phoneNumber);
//    	
//    	
////    	 BigDecimal balance = chucnang.sodu("ACC12346");
////         System.out.println("Số dư của tài khoản là: " + balance);
////         
//       
////         chucnang.chuyetien("ACC12345", new BigDecimal(1000), "ACC12346");
////         chucnang.naptien("ACC12346", new BigDecimal(1000));
////         chucnang.ruttien("ACC12346", new BigDecimal(1000));
////         chucnang.hamhienlsdg("ACC12345");
////    	chucnang.hamthongke();
//	}
}
