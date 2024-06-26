package model;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class Chucnang {
    
    private final SessionFactory sessionFactory;

    public Chucnang() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public String dangky(String accountnumber, String password, String name, String sdt) {
    	
        try (Session session = sessionFactory.openSession()) {
            if (accountnumber == null || accountnumber.isEmpty() ||
                    password == null || password.isEmpty() ||
                    name == null || name.isEmpty() ||
                    sdt == null || sdt.isEmpty()) {
                return "Vui lòng nhập đầy đủ thông tin tài khoản";            
            }

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> query = builder.createQuery(Account.class);
            Root<Account> root = query.from(Account.class);
            Predicate condition = builder.and(
                    builder.equal(root.get("phone"), sdt),
                    builder.equal(root.get("accountPassword"), password)
            );
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

                Transaction tr = session.beginTransaction(); 
                session.save(newAccount);
                tr.commit(); 
                return "Đăng ký thành công!";
            } else {
                // Tài khoản hoặc mật khẩu đã tồn tại
                return "Tài khoản hoặc mật khẩu đã tồn tại";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Đăng ký thất bại";
        }
    }

    public String dangnhap(String sdt, String matkhau) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> query = builder.createQuery(Account.class);
            Root<Account> root = query.from(Account.class);
            if(sdt==null||matkhau==null) {
            	return "hãy điền đầy đủ thông tin";
            }

            Predicate condition = builder.and(
                    builder.equal(root.get("phone"), sdt),
                    builder.equal(root.get("accountPassword"), matkhau)
            );

            query.select(root).where(condition);
            Account account = session.createQuery(query).uniqueResult();

            tr.commit();

            if (account != null) {
                return "Đăng nhập thành công";
            } else {
                return "Đăng nhập thất bại";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "lỗi";
        }
    }

    public BigDecimal sodu(String mk) {
        BigDecimal balance = BigDecimal.ZERO;
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();

            CriteriaQuery<BigDecimal> depositQuery = cb.createQuery(BigDecimal.class);
            Root<TradeHistory> depositRoot = depositQuery.from(TradeHistory.class);
            depositQuery.select(cb.sum(depositRoot.get("amount")))
                    .where(cb.and(
                            cb.equal(depositRoot.get("account").get("accountPassword"), mk),
                            cb.equal(depositRoot.get("tradeType"), "nạp tiền")));

            BigDecimal sumDeposit = session.createQuery(depositQuery).uniqueResult();
            if (sumDeposit == null) {
                sumDeposit = BigDecimal.ZERO;
            }

            CriteriaQuery<BigDecimal> withdrawQuery = cb.createQuery(BigDecimal.class);
            Root<TradeHistory> withdrawRoot = withdrawQuery.from(TradeHistory.class);
            withdrawQuery.select(cb.sum(withdrawRoot.get("amount")))
                    .where(cb.and(
                            cb.equal(withdrawRoot.get("account").get("accountPassword"), mk),
                            cb.equal(withdrawRoot.get("tradeType"), "rút tiền")));

            BigDecimal sumWithdraw = session.createQuery(withdrawQuery).uniqueResult();
            if (sumWithdraw == null) {
                sumWithdraw = BigDecimal.ZERO;
            }

            CriteriaQuery<BigDecimal> transferInQuery = cb.createQuery(BigDecimal.class);
            Root<TradeHistory> transferInRoot = transferInQuery.from(TradeHistory.class);
            transferInQuery.select(cb.sum(transferInRoot.get("amount")))
                    .where(cb.and(
                            cb.equal(transferInRoot.get("account").get("accountPassword"), mk),
                            cb.equal(transferInRoot.get("tradeType"), "nhận tiền")));

            BigDecimal sumTransferIn = session.createQuery(transferInQuery).uniqueResult();
            if (sumTransferIn == null) {
                sumTransferIn = BigDecimal.ZERO;
            }

            CriteriaQuery<BigDecimal> transferOutQuery = cb.createQuery(BigDecimal.class);
            Root<TradeHistory> transferOutRoot = transferOutQuery.from(TradeHistory.class);
            transferOutQuery.select(cb.sum(transferOutRoot.get("amount")))
                    .where(cb.and(
                            cb.equal(transferOutRoot.get("account").get("accountPassword"), mk),
                            cb.equal(transferOutRoot.get("tradeType"), "chuyển tiền")));

            BigDecimal sumTransferOut = session.createQuery(transferOutQuery).uniqueResult();
            if (sumTransferOut == null) {
                sumTransferOut = BigDecimal.ZERO;
            }

            balance = sumDeposit.add(sumTransferIn).subtract(sumWithdraw).subtract(sumTransferOut);

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance;
    }

    

    public String themlichsugd(String account, String tradetype, BigDecimal tien) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> accountquery = builder.createQuery(Integer.class);
            Root accountroot = accountquery.from(Account.class);

            Predicate condition = builder.equal(accountroot.get("accountNumber"), account);

            accountquery.select(accountroot.get("id")).where(condition);

            Integer accountId = session.createQuery(accountquery).uniqueResult();

            if (accountId != null) {
                TradeHistory trade = new TradeHistory();
                trade.setTradeDate(new Date());
                trade.setTradeType(tradetype);
                trade.setAmount(tien);
                session.save(trade);
                Account accountt = session.get(Account.class, accountId);
                trade.setAccount(accountt);
                session.save(trade);
                tr.commit();
            } else {
                return "lỗi";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Lỗi";
    }

    public String chuyetien(String phone, BigDecimal tiengui, String taikhoannhan) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            Root<Account> root = query.from(Account.class);

            Predicate condition = builder.equal(root.get("phone"), phone);

            query.select(root.get("accountNumber")).where(condition);

            String account = session.createQuery(query).uniqueResult();

            if (account != null) {
                BigDecimal sdtkgui = sodu(account);
                if (tiengui.compareTo(sdtkgui) >= 0) {
                    String chuyentien = "chuyển tiền";
                    String nhantien = "nhận tiền";

                    themlichsugd(account, chuyentien, tiengui);

                    themlichsugd(taikhoannhan, nhantien, tiengui);

                    tr.commit();
                    return "chuyển tiền thành công";
                } else {
                    return "số dư trong tài khoản không đủ";
                }
            } else {
                return "không tồn tại tài khoản này";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "lỗi";
        }
    }

    public String naptien(String phone, BigDecimal tiennap) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            Root root = query.from(Account.class);

            Predicate condition = builder.equal(root.get("phone"), phone);
            query.select(root.get("accountNumber")).where(condition);

            String account = session.createQuery(query).uniqueResult();
            String naptien = "nạp tiền";
            if (account != null) {
                themlichsugd(account, naptien, tiennap);
                return "Nạp tiền thành công";
            } else {
                return "không tồn tại tài khoản này";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "lỗi";
        }
    }

    public String ruttien(String phone, BigDecimal tienrut) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            Root root = query.from(Account.class);

            Predicate condition = builder.equal(root.get("phone"), phone);
            query.select(root.get("accountNumber")).where(condition);

            String account = session.createQuery(query).uniqueResult();
            String ruttien = "rút tiền";
            if (account != null) {
                BigDecimal sdtkr = sodu(account);
                if (tienrut.compareTo(sdtkr) <= 0) {
                    themlichsugd(account, ruttien, tienrut);
                    return "Rút tiền thành công";
                } else {
                    return "Số dư trong tài khoản của bạn không đủ";
                }
            } else {
                return "Không tồn tại tài khoản này";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "lỗi";
        }
    }

    public String hamhienlsdg(String mk) {
        StringBuilder result = new StringBuilder();
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();

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

                tradeHistories.forEach(trde ->
                        result.append(String.format("%-10d %-20s %-15s %-10.2f%n",
                                trde.getId(),
                                trde.getTradeDate().toString(),
                                trde.getTradeType(),
                                trde.getAmount()))
                );

                return result.toString();
            } else {
                result.append("Không tồn tại tài khoản với mật khẩu đã cho.\n");
            }
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            result.append("Đã xảy ra lỗi trong quá trình truy vấn.\n");
        }
        return result.toString();
    }

    public String hamthongke() {
        StringBuilder result = new StringBuilder();
        try (Session session = sessionFactory.openSession()) {
            Transaction tr = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
            Root<Account> accountRoot = query.from(Account.class);
            Root<TradeHistory> tradeRoot = query.from(TradeHistory.class);

            Predicate joinCondition = builder.equal(accountRoot.get("id"), tradeRoot.get("account"));
            query.where(joinCondition);

            query.multiselect(
                accountRoot,
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

            List<Object[]> results = session.createQuery(query).getResultList();

	            result.append("+---------------------------------------------------------------------------------------------------------+\n");
	            result.append("| ID |  Account Number |  Account Name |  Phone  | Deposit | Receive  |  Transfer  |  Withdraw  | Balance |\n");
	            result.append("+---------------------------------------------------------------------------------------------------------+\n");

            for (Object[] row : results) {
                Account account = (Account) row[0];
                BigDecimal deposit = (BigDecimal) row[1];
                BigDecimal receive = (BigDecimal) row[2];
                BigDecimal transfer = (BigDecimal) row[3];
                BigDecimal withdraw = (BigDecimal) row[4];
                BigDecimal balance = deposit.subtract(withdraw).add(receive).subtract(transfer);

                result.append(String.format("| %-2d | %-14s | %-12s | %-6s | %-8s | %-8s | %-9s | %-9s | %-8s |%n",
                                  account.getId(), account.getAccountNumber(), account.getAccountName(), account.getPhone(),
                                  deposit, receive, transfer, withdraw, balance));
            }

            result.append("+---------------------------------------------------------------------------------------------------------+\n");

            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

//    public String thaydoimatkhau(String mkc, String mkmoi) {
//        Transaction tr = null;
//        try (Session session = sessionFactory.openSession()) {
//            tr = session.beginTransaction();
//            CriteriaBuilder builder = session.getCriteriaBuilder();
//            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
//            Root<Account> root = query.from(Account.class);
//
//            Predicate condition = builder.equal(root.get("accountPassword"), mkc);
//            query.select(root.get("id")).where(condition);
//            Integer accountid = session.createQuery(query).uniqueResult();
//
//            if (accountid != null) {
//                Account tk = session.get(Account.class, accountid);
//                tk.setAccountPassword(mkmoi);
//                session.update(tk);
//                tr.commit();
//                return "Thay đổi thành công";
//            } else {
//                tr.rollback();
//                return "Mật khẩu này đã tồn tại";
//            }
//
//        } catch (Exception e) {
//            if (tr != null) {
//                tr.rollback();
//            }
//            e.printStackTrace();
//            return "Lỗi";
//        }
	//    }
    public String thaydoimatkhau(String mkc, String mkm) {
        Transaction tr = null;
        try (Session session = sessionFactory.openSession()) {
            tr = session.beginTransaction();
            
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root<Account> root = query.from(Account.class);

            Predicate condition = builder.equal(root.get("accountPassword"), mkc);
            query.select(root.get("id")).where(condition);
            List<Integer> accountIds = session.createQuery(query).getResultList();

            if (accountIds !=null) {
                Integer accountId = accountIds.get(0);
                Account tk = session.get(Account.class, accountId);
                tk.setAccountPassword(mkm);
                session.update(tk);
                tr.commit();
                return "Thay đổi thành công";
            } else {
                if (tr != null) tr.rollback();
                return "Mật khẩu này đã tồn tại hoặc có nhiều tài khoản với cùng mật khẩu";
            }
        } catch (Exception e) {
            if (tr != null) tr.rollback();
            e.printStackTrace();
            return "lỗi";
        }
    }


    public String layTen(String soDienThoai) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            
            Root<Account> root = query.from(Account.class); // Chỉ định lớp entity rõ ràng
            Predicate dieuKien = builder.equal(root.get("phone"), soDienThoai);
            
            query.select(root.get("accountName")).where(dieuKien);
            
            String tenTaiKhoan = session.createQuery(query).uniqueResult();

            transaction.commit();
            session.close();

            return tenTaiKhoan;
        } catch (Exception e) {
            // Ghi log hoặc xử lý ngoại lệ phù hợp
            e.printStackTrace(); // Cho mục đích gỡ lỗi, bạn có thể ghi log nếu muốn
            return null; // Trả về null hoặc xử lý ngoại lệ một cách thích hợp cho ứng dụng của bạn
        }
    }
    public String laytk(String soDienThoai) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            
            Root<Account> root = query.from(Account.class); // Chỉ định lớp entity rõ ràng
            Predicate dieuKien = builder.equal(root.get("phone"), soDienThoai);
            
            query.select(root.get("accountNumber")).where(dieuKien);
            
            String soTaiKhoan = session.createQuery(query).uniqueResult();

            transaction.commit();
            session.close();

            return soTaiKhoan;
        } catch (Exception e) {
            // Ghi log hoặc xử lý ngoại lệ phù hợp
            e.printStackTrace(); // Cho mục đích gỡ lỗi, bạn có thể ghi log nếu muốn
            return null; // Trả về null hoặc xử lý ngoại lệ một cách thích hợp cho ứng dụng của bạn
        }
    }
    public String laymk(String soDienThoai) {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            
            Root<Account> root = query.from(Account.class); // Chỉ định lớp entity rõ ràng
            Predicate dieuKien = builder.equal(root.get("phone"), soDienThoai);
            
            query.select(root.get("accountPassword")).where(dieuKien);
            
            String matkhau = session.createQuery(query).uniqueResult();

            transaction.commit();
            session.close();

            return matkhau;
        } catch (Exception e) {
            // Ghi log hoặc xử lý ngoại lệ phù hợp
            e.printStackTrace(); // Cho mục đích gỡ lỗi, bạn có thể ghi log nếu muốn
            return null; // Trả về null hoặc xử lý ngoại lệ một cách thích hợp cho ứng dụng của bạn
        }
    }
    
    
    
//    public static void main(String[] args) {
//		Chucnang cn= new Chucnang();
//		String anh=cn.layTen("1234567890");
//		System.out.println(anh);
//	}

}