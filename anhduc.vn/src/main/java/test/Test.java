package test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.Account;
import model.TradeHistory;
import util.HibernateUtil;

public class Test {
//    public static void main(String[] args) {
//        try {
//            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//            if (sessionFactory != null) {
//                Session session = sessionFactory.openSession();
//                Transaction tr = null;
//                try {
//                    tr = session.beginTransaction();       
//                    
//                    CriteriaBuilder builder= session.getCriteriaBuilder();
//                    CriteriaQuery<TradeHistory> query= builder.createQuery(TradeHistory.class);
//                    
//                    Root root= query.from(TradeHistory.class);
//                    query=query.select(root);
//                    
//                    
//                    Query q = session.createQuery(query);
//                    List<TradeHistory> trade= q.getResultList();
//                    trade.forEach(c -> System.out.printf("Trade Type: %s, Amount: %.2f, Trade Date: %s\\n",c.getTradeType(),c.getAmount(),c.getTradeDate()));
//                    
//                    
////                    CriteriaBuilder<>
//                    //hàm xóa hết dữ liệu trong bảng
////                    Query query = session.createQuery("DELETE FROM Account");
////                    int deletedCount = query.executeUpdate();
////                    System.out.println("Số lượng bản ghi đã xóa: " + deletedCount);
//
//                    
//                    //hiện danh sách theo yêu cấu
//                    //có thể thêm điều kiện cho nó
////                    Query q = session.createQuery("FROM Account WHERE id=1");
////                    Query q = session.createQuery("FROM Account");
////                    List<Account> accountList = q.getResultList();
////                    accountList.forEach(c -> System.out.printf("%d -%s\n", c.getId(), c.getAccountPassword()));
//                    
////                    TradeHistory trade=new TradeHistory();
//                    
//                    
//                    //hàm thêm
////                    Account tk=new Account();
////                    tk.setAccountNumber("123456789");
////                    tk.setAccountPassword("securepassword");
////                    tk.setAccountName("Anh Duc");
////                    tk.setPhone("0123456789");
////                    tk.setIsActive(true);
////                    session.save(tk);
////                    
//                    //hàm xó
////                    Account cn = session.get(Account.class, 2);
////                    if (cn != null) {
////                        session.delete(cn);
////                    }
//                    
//                    //thêm phan từ vào bảng trade
//                    
////                    TradeHistory trade = new TradeHistory();
////                    trade.setTradeDate(new Date()); // Đặt ngày giao dịch
////                    trade.setTradeType("nạp tiền"); // Loại giao dịch
////                    trade.setAmount(new BigDecimal(10000)); // Số lượng giao dịch
////                    
////                    // Lấy đối tượng Account từ cơ sở dữ liệu (ví dụ: đối tượng có id là 1)
////                    Account account = session.get(Account.class, 1);
////                    
////                    // Gán đối tượng Account cho thuộc tính account của TradeHistory
////                    trade.setAccount(account);
////                    
////                    // Lưu đối tượng TradeHistory vào cơ sở dữ liệu
////                    session.save(trade);
//////                    
//                    
//                    // cac sgiao dịch của tài khoản có id =1
////                    Account c = session.get(Account.class, 1); // Lấy Account có ID là 1
////
////                    if (c != null) { // Kiểm tra xem Account có tồn tại không
////                        // Lặp qua danh sách các TradeHistory liên kết với Account
////                        c.getTradeHistories().forEach(t ->
////                            System.out.printf("%d - %s - %s\n", t.getId(), t.getTradeDate(), t.getTradeType())
////                        );
////                    } else {
////                        System.out.println("Không tìm thấy tài khoản.");
////                    }
//                    
//                    
//
//
//                    
//                    
//                    tr.commit();
//               
//                } catch (Exception e) {
//                    
//                    if (tr != null) {
//                        tr.rollback();
//                    }
//                    e.printStackTrace();
//                } finally {
//                    session.close();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
