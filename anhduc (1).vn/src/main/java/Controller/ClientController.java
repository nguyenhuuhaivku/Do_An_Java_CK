	package Controller;
	
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.JOptionPane;
	
	import View.Dangky;
	import View.dangnhap;
	import View.trangchu;
	import View.Test;
	import model.ClientModel;
	
	public class ClientController {
	    private static final int port = 1000;
	    private static String url = "localhost";
	    private ClientModel model;
	    private dangnhap dangnhapView;
	    private Dangky dangkyView;
	    private trangchu trangchuView;
	    private Test test;
	   
	    public ClientController(dangnhap dangnhapView, Dangky dangkyView, trangchu trangchuView,Test test) {
	        this.dangnhapView = dangnhapView;
	        this.dangkyView = dangkyView;
	        this.trangchuView = trangchuView;
	        this.test=test;
	        this.model = new ClientModel(); // Initialize model here
	    }
	    
	    
	
	
	    public void langnghe() {
	    		
	        if (dangkyView != null) {
	            dangkyView.skdangnhap(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    dangkyView.getFrame().setVisible(false);
	                    dangnhapView.getFrame().setVisible(true);
	                }
	            });
	
	            
	            dangkyView.addRegisterListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    taotaikhoan();              	
	                }
	            });
	        }
	
	        
	        if (dangnhapView != null) {
	            dangnhapView.addRegisterListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    login();
	                }
	            });
	
	            
	            dangnhapView.addRegisterListener1(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    dangnhapView.getFrame().setVisible(false);
	                    dangkyView.getFrame().setVisible(true);
	                }
	            });
	        }
	        
	        
	        trangchuView.skchuyentien(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub				
					chuyentien();
				}
			});
	        
	        trangchuView.sknaptien(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					naptien();
				}
			});
	        trangchuView.sksd(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					laysd();			
				}
			});
	        
	        trangchuView.skthaydoimk(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					doimatkhau();					
				}
			});
	        
	        trangchuView.skdangxuat(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					trangchuView.getFrame().setVisible(false);
					dangnhapView.getFrame().setVisible(true);		
					dangnhapView.setmk("");
					dangnhapView.setsdt("");
				}
			});
	        
	        trangchuView.skls(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					lichsu();
					trangchuView.showMessage("khong duoc");
				}
			});
	        trangchuView.nuthotngtin(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					trangchuView.getFrame().setVisible(false);
					test.getframe().setVisible(true);
					thongtin();			
					
				}
			});
	        test.skql(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					test.getframe().setVisible(false);
					trangchuView.getFrame().setVisible(true);
				}
			});
	    }
	    
	    
	    private String getSdt() {
	        if (dangnhapView == null) {
	            System.err.println("dangnhapView is null, cannot get sdt");
	            return null; // or handle this case as needed
	        }
	        
	        return dangnhapView.getUsername();
	    }
	    
	    private String getmk() {
	        if (dangnhapView == null) {
	            System.err.println("dangnhapView is null, cannot get sdt");
	            return null; // or handle this case as needed
	        }
	        
	        return dangnhapView.getPassword();
	    }
	    
	    private void thongtin() {
	    	String sdt=getSdt();
	    	try {
				model.connect(url, port);
				model.sendMessage("thongtincanhan");
				model.sendMessage(sdt);
				String ten= model.receiveMessage();
				String stk=model.receiveMessage();
				String mk=model.receiveMessage();
				test.setten(ten);
				test.setstk(stk);
				test.setmk(mk);
				test.setsdt(sdt);
				model.close();
			} catch (Exception e) {
				System.err.println("Lỗi khi đăng ký tài khoản: " + e.getMessage());
	        } finally {
	            try {
	                model.close();
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    }
	    
	    private void lichsu() {
	    	String mk=getmk();
	    	try {
				model.connect(url, port);
				model.sendMessage("lichsu");
				model.sendMessage(mk);
				String thongbao=model.receiveMessage();
				trangchuView.lichsu(thongbao);
				model.close();
			} catch (Exception e) {
				 System.err.println("Lỗi khi đăng ký tài khoản: " + e.getMessage());
	        } finally {
	            try {
	                model.close();
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    }
	    
	    private void laysd() {
	    	String mk=getmk();
	    	try {
	    		 model.connect(url, port);
	    		 model.sendMessage("sodu");
	    		 model.sendMessage(mk);
	    		 String thongbao= model.receiveMessage();
	    		 trangchuView.showMessage(thongbao);
	    		 model.close();
			} catch (Exception e) {
				 System.err.println("Lỗi khi đăng ký tài khoản: " + e.getMessage());
	        } finally {
	            try {
	                model.close();
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    }	
	    public void naptien() {
	    	if(trangchuView==null) {
	    		System.out.println("trang chủ null");
	    		return ;
	    	}
	    	String phone =getSdt();
	    	String tiennap=trangchuView.gettiennap();
	    	
	    	try {
				model.connect(url, port);
				model.sendMessage("naptien");
				model.sendMessage(phone);
				model.sendMessage(tiennap);
				
				String thongbao= model.receiveMessage();
				trangchuView.showMessage(thongbao);
				if(thongbao.equals("Nạp tiền thành công")) {
					trangchuView.settiennap("");
				}
				model.close();
			} catch (Exception e) {
				System.err.println("Lỗi khi kết nối đến server: " + e.getMessage());
	        } finally {
	            try {
	                model.close(); // Đóng kết nối nếu có lỗi xảy ra
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    	
	    }
		public void chuyentien() {
	        if (trangchuView == null) {
	            System.err.println("trangchuView is null, cannot perform chuyentien operation");
	            return;
	        }
	
	        String phone =  getSdt();
	        String tienguiString = trangchuView.getsotien();
	        String tk = trangchuView.getstk();
	
	        try {
	            model.connect(url, port);
	            model.sendMessage("chuyentien");
	            model.sendMessage(phone);
	            model.sendMessage(tienguiString);
	            model.sendMessage(tk);
	            
	            String thongbao = model.receiveMessage();
	            trangchuView.showMessage(thongbao);
	            if(thongbao.equals("chuyển tiền thành công")) {
	            	trangchuView.setstkk("");
	            	trangchuView.settienn("");
	            }
	            model.close();
	        } catch (IOException e) {
	            System.err.println("Lỗi khi kết nối đến server: " + e.getMessage());
	        } finally {
	            try {
	                model.close(); // Đóng kết nối nếu có lỗi xảy ra
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    }	
	    private void login() {
	        if (dangnhapView == null) {
	            System.err.println("dangnhapView is null, cannot perform login operation");
	        }
	
	        String sdt = dangnhapView.getUsername();
	        String matkhau = dangnhapView.getPassword();
	        
	        try {
	            model.connect(url, port);
	            model.sendMessage("dangnhap");
	            model.sendMessage(sdt);
	            model.sendMessage(matkhau);
	           
	            String thongbao = model.receiveMessage();
	            dangnhapView.showMessage(thongbao);
	            
	            if (thongbao.equals("Đăng nhập thành công")) {
	                dangnhapView.getFrame().setVisible(false);
	                trangchuView.getFrame().setVisible(true);
	                model.sendMessage("dangnhapthanhcong");
	                model.sendMessage(sdt);
	                model.sendMessage(matkhau);
	                String tentaikhoan=model.receiveMessage();
	                trangchuView.setten(tentaikhoan);
	                String stk=model.receiveMessage();
	                trangchuView.setstk(stk);	              	               
	            }
	            
	            model.close();
	        } catch (IOException e) {
	            System.err.println("Lỗi khi kết nối đến server: " + e.getMessage());
	        }
        
	    }	
	    private void taotaikhoan() {
	        if (dangkyView == null) {
	            System.err.println("dangkyView is null, cannot create account");
	            return;
	        }
	        
	        String tk = dangkyView.getstk();
	        String ten = dangkyView.getname();
	        String mk = dangkyView.getmk();
	        String sdt = dangkyView.getsdt();
	
	        try {
	            model.connect(url, port);
	            model.sendMessage("dangky");
	            model.sendMessage(tk);
	            model.sendMessage(ten);
	            model.sendMessage(mk);
	            model.sendMessage(sdt);
	
	            String thongbao = model.receiveMessage();
	            dangkyView.showMessage(thongbao);
	            if(thongbao.equals("Đăng ký thành công!")) {
	            	dangkyView.getFrame().setVisible(false);
	            	dangkyView.getFrame().setVisible(true);
	            	dangkyView.setmk("");
	            	dangkyView.setname("");
	            	dangkyView.setsdt("");
	            	dangkyView.setstk("");
	            }
	        } catch (Exception e) {
	            System.err.println("Lỗi khi đăng ký tài khoản: " + e.getMessage());
	        } finally {
	            try {
	                model.close();
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    }

	    private void doimatkhau() {
	    	String mkc= trangchuView.mkc();
	    	String mkm=trangchuView.mkm();
	    	try {
				model.connect(url, port);
				model.sendMessage("doimatkhau");
				model.sendMessage(mkc);
				model.sendMessage(mkm);
				
				String thongbao= model.receiveMessage();
				trangchuView.showMessage(thongbao);
				if(thongbao.equals("Thay đổi thành công")) {
					trangchuView.setmkc("");
					trangchuView.setmkm("");
				}
				model.close();
			} catch (Exception e) {
				 System.err.println("Lỗi khi đăng ký tài khoản: " + e.getMessage());
	        } finally {
	            try {
	                model.close();
	            } catch (IOException e) {
	                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
	            }
	        }
	    }
	    
	    public static void main(String[] args) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    dangnhap dn = new dangnhap();
	                    Dangky dk = new Dangky();
	                    trangchu tcc = new trangchu();
	                    Test tc=new Test();
	
	                    ClientController controller = new ClientController(dn, dk, tcc,tc);
	                    controller.langnghe();
	                  
	                    dn.getFrame().setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	}
