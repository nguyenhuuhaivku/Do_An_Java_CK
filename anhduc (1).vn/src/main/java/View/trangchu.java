	package View;
	
	import java.awt.EventQueue;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.JTabbedPane;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	
	import java.awt.Font;
	import javax.swing.JTextField;
	import javax.swing.JButton;
	import java.awt.event.ActionListener;
	import java.math.BigDecimal;
	import java.awt.event.ActionEvent;
	import javax.swing.JTable;
	import javax.swing.border.TitledBorder;
	import javax.swing.table.DefaultTableModel;
	
	import Controller.ClientController;
	
	import javax.swing.JScrollPane;
	import java.awt.Color;
import java.awt.Scrollbar;
	
	public class trangchu {
	
	    private JFrame frame;
	    private JTextField textField;
	    private JTextField texttk;
	    private JTextField texttien;
	    private JTable table;
	    private JTextField texttiennap;
	    private JTable table_1;
	    private JTextField textField_5;
	    private JButton nutchuyentien;
	    private JButton nutnaptien,reset;
	    private JButton nutthongtincanhan,thaydoimk,btnNewButton_2_1,sodu,dangxuat;
	    private JTextField textField_1;
	    private JTextField textField_2;
	    private JTextField textField_3;
	    private JTextField textField_4;
	
	    /**
	     * Launch the application.
	     */
	    
//	    private ClientController controller;
//
//	    public trangchu(ClientController controller) {
//	        this.controller = controller;
//	        initialize();
//	    }
	    
		public trangchu() {
			initialize();
		}
		
	    public static void main(String[] args) {
	        EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    trangchu window = new trangchu();
	                    window.frame.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	
	    /**
	     * Create the application.
	     */
	   
	
	    /**
	     * Initialize the contents of the frame.
	     */
	    private void initialize() {
	        frame = new JFrame();
	        frame.setBounds(100, 100, 600, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);
	        
	        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane.setBounds(10, 10, 566, 343);
	        frame.getContentPane().add(tabbedPane);
	        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 30));
	        
	        JPanel panel = new JPanel();
	        tabbedPane.addTab("Home", null, panel, null);
	        
	        panel.setLayout(null);
	        
	        JPanel panel_8 = new JPanel();
	        panel_8.setBorder(new TitledBorder(null, "T\u00E0i kho\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_8.setBounds(10, 8, 282, 69);
	        panel.add(panel_8);
	        panel_8.setLayout(null);
	        
	        JLabel lblNewLabel = new JLabel("Xin chào");
	        lblNewLabel.setBounds(12, 20, 69, 30);
	        panel_8.add(lblNewLabel);
	        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        
	        textField = new JTextField();
	        textField.setBounds(93, 21, 156, 28);
	        panel_8.add(textField);
	        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        textField.setEditable(false);
	        textField.setColumns(10);
	        
	        JPanel panel_9 = new JPanel();
	        panel_9.setBorder(new TitledBorder(null, "S\u1ED1 D\u01B0", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_9.setBounds(302, 8, 249, 69);
	        panel.add(panel_9);
	        panel_9.setLayout(null);
	        
	        sodu = new JButton("Coi số dư");
	        sodu.setFont(new Font("Tahoma", Font.PLAIN, 19));
	        sodu.setBounds(40, 20, 169, 33);
	        panel_9.add(sodu);
	        
	        JPanel panel_10 = new JPanel();
	        panel_10.setBorder(new TitledBorder(null, "S\u1ED1 T\u00E0i kho\u1EA3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_10.setBounds(10, 87, 541, 56);
	        panel.add(panel_10);
	        panel_10.setLayout(null);
	        
	        JLabel lblTaKhon = new JLabel("Số Tài khoản");
	        lblTaKhon.setBounds(32, 21, 104, 22);
	        panel_10.add(lblTaKhon);
	        lblTaKhon.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        
	        textField_5 = new JTextField();
	        textField_5.setBounds(149, 18, 186, 28);
	        panel_10.add(textField_5);
	        textField_5.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        textField_5.setEditable(false);
	        textField_5.setColumns(10);
	        
	        nutthongtincanhan = new JButton("Thông Tin cá nhân");
	        nutthongtincanhan.setBackground(Color.WHITE);
	        nutthongtincanhan.setFont(new Font("Tahoma", Font.PLAIN, 17));
	        nutthongtincanhan.setBounds(344, 17, 181, 27);
	        panel_10.add(nutthongtincanhan);
	        
	        JPanel panel_11 = new JPanel();
	        panel_11.setBorder(new TitledBorder(null, "D\u1ECBch v\u1EE5", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_11.setBounds(10, 145, 541, 162);
	        panel.add(panel_11);
	        panel_11.setLayout(null);
	        
	        JButton btnNewButton_4 = new JButton("dv");
	        btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4.setBounds(29, 24, 85, 48);
	        panel_11.add(btnNewButton_4);
	        
	        JButton btnNewButton_4_1 = new JButton("dv");
	        btnNewButton_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_1.setBounds(157, 24, 85, 48);
	        panel_11.add(btnNewButton_4_1);
	        
	        JButton btnNewButton_4_2 = new JButton("dv");
	        btnNewButton_4_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_2.setBounds(295, 24, 85, 48);
	        panel_11.add(btnNewButton_4_2);
	        
	        JButton btnNewButton_4_3 = new JButton("dv");
	        btnNewButton_4_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_3.setBounds(425, 24, 85, 48);
	        panel_11.add(btnNewButton_4_3);
	        
	        JButton btnNewButton_4_4 = new JButton("dv");
	        btnNewButton_4_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_4.setBounds(29, 93, 85, 48);
	        panel_11.add(btnNewButton_4_4);
	        
	        JButton btnNewButton_4_4_1 = new JButton("dv");
	        btnNewButton_4_4_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_4_1.setBounds(157, 93, 85, 48);
	        panel_11.add(btnNewButton_4_4_1);
	        
	        JButton btnNewButton_4_4_2 = new JButton("dv");
	        btnNewButton_4_4_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_4_2.setBounds(295, 93, 85, 48);
	        panel_11.add(btnNewButton_4_4_2);
	        
	        JButton btnNewButton_4_4_3 = new JButton("dv");
	        btnNewButton_4_4_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        btnNewButton_4_4_3.setBounds(425, 93, 85, 48);
	        panel_11.add(btnNewButton_4_4_3);
	        
	        JPanel panel_1 = new JPanel();
	        tabbedPane.addTab("Chuyển Tiền", null, panel_1, null);
	        panel_1.setLayout(null);
	        
	        JLabel lblNewLabel_1 = new JLabel("Tài khoản");
	        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        lblNewLabel_1.setBounds(73, 23, 74, 28);
	        panel_1.add(lblNewLabel_1);
	        
	        texttk = new JTextField();
	        texttk.setBounds(157, 23, 195, 28);
	        panel_1.add(texttk);
	        texttk.setColumns(10);
	        
	        JLabel lblNewLabel_1_1 = new JLabel("Số Tiền");
	        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
	        lblNewLabel_1_1.setBounds(83, 68, 55, 28);
	        panel_1.add(lblNewLabel_1_1);
	        
	        texttien = new JTextField();
	        texttien.setColumns(10);
	        texttien.setBounds(157, 68, 195, 28);
	        panel_1.add(texttien);
	        
	        nutchuyentien = new JButton("Chuyển Tiền");
     	
	        nutchuyentien.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        nutchuyentien.setBounds(385, 38, 128, 48);
	        panel_1.add(nutchuyentien);
	        
	        JPanel panel_2 = new JPanel();
	        panel_2.setBorder(new TitledBorder(null, "Lịch sử chuyển tiền", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_2.setToolTipText("");
	        panel_2.setBounds(10, 106, 541, 191);
	        panel_1.add(panel_2);
	        panel_2.setLayout(null);
	        
	        JScrollPane scrollPane = new JScrollPane();
	        scrollPane.setBounds(10, 20, 521, 161);
	        panel_2.add(scrollPane);
	        
	        table = new JTable();	        
	        scrollPane.setViewportView(table);
	        
	        JPanel panel_12 = new JPanel();
	        panel_12.setBorder(new TitledBorder(null, "Chuy\u1EC3n Ti\u1EC1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_12.setBounds(10, 10, 541, 93);
	        panel_1.add(panel_12);
	        
	        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        
	        JPanel panel_3 = new JPanel();
	        tabbedPane.addTab("Nạp Tiền", null, panel_3, null);
	        panel_3.setLayout(null);
	        
	        JPanel panel_4 = new JPanel();
	        panel_4.setBorder(new TitledBorder(null, "L\u1ECBch s\u1EED n\u1EA1p ti\u1EC1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_4.setBounds(10, 115, 541, 182);
	        panel_3.add(panel_4);
	        panel_4.setLayout(null);
	        
	        table_1 = new JTable();
	        table_1.setBounds(10, 24, 521, 148);
	        panel_4.add(table_1);
	        
	        JPanel panel_13 = new JPanel();
	        panel_13.setBorder(new TitledBorder(null, "N\u1EA1p ti\u1EC1n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_13.setBounds(10, 22, 530, 83);
	        panel_3.add(panel_13);
	        panel_13.setLayout(null);
	        
	        texttiennap = new JTextField();
	        texttiennap.setBounds(131, 29, 166, 28);
	        panel_13.add(texttiennap);
	        texttiennap.setColumns(10);
	        
	        JLabel lblNewLabel_2 = new JLabel("Số Tiền");
	        lblNewLabel_2.setBounds(61, 28, 63, 33);
	        panel_13.add(lblNewLabel_2);
	        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        
	        nutnaptien = new JButton("Nạp Tiền");
	        nutnaptien.setBounds(318, 21, 172, 44);
	        panel_13.add(nutnaptien);
	        nutnaptien.setFont(new Font("Tahoma", Font.PLAIN, 19));
	        
	        JPanel panel_5 = new JPanel();
	        tabbedPane.addTab("Lịch sử giao dịch", null, panel_5, null);
	        panel_5.setLayout(null);
	        
	        JPanel panel_7 = new JPanel();
	        panel_7.setBorder(new TitledBorder(null, "L\u1ECBch s\u1EED giao d\u1ECBch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_7.setBounds(10, 10, 541, 287);
	        panel_5.add(panel_7);
	        panel_7.setLayout(null);
	        
	        textField_4 = new JTextField();
	        textField_4.setBounds(10, 20, 521, 223);
	        panel_7.add(textField_4);
	        textField_4.setColumns(10);
	        
	        reset = new JButton("Reset");
	        reset.setFont(new Font("Tahoma", Font.PLAIN, 17));
	        reset.setBounds(155, 253, 236, 24);
	        panel_7.add(reset);
	        
	        JPanel panel_6 = new JPanel();
	        tabbedPane.addTab("Cài đặt", null, panel_6, null);
	        panel_6.setLayout(null);
	        
	        btnNewButton_2_1 = new JButton("Liên Hệ tư vấn");
	       
	        btnNewButton_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        btnNewButton_2_1.setBounds(11, 138, 194, 43);
	        panel_6.add(btnNewButton_2_1);
	        
	        JPanel panel_14 = new JPanel();
	        panel_14.setBorder(new TitledBorder(null, "Thay \u0111\u1ED5i m\u1EADt kh\u1EA9u", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	        panel_14.setBounds(10, 10, 541, 118);
	        panel_6.add(panel_14);
	        panel_14.setLayout(null);
	        
	        thaydoimk = new JButton("Thay đổi mật khẩu");
	        thaydoimk.setBounds(348, 39, 183, 43);
	        panel_14.add(thaydoimk);
	        thaydoimk.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        
	        textField_1 = new JTextField();
	        textField_1.setBounds(130, 20, 208, 32);
	        panel_14.add(textField_1);
	        textField_1.setColumns(10);
	        
	        textField_2 = new JTextField();
	        textField_2.setColumns(10);
	        textField_2.setBounds(130, 62, 208, 32);
	        panel_14.add(textField_2);
	        
	        JLabel lblNewLabel_3 = new JLabel("Mật khẩu cũ");
	        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblNewLabel_3.setBounds(35, 31, 95, 23);
	        panel_14.add(lblNewLabel_3);
	        
	        JLabel lblNewLabel_3_1 = new JLabel("Mật khẩu mới");
	        lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
	        lblNewLabel_3_1.setBounds(28, 68, 95, 23);
	        panel_14.add(lblNewLabel_3_1);
	        
	        dangxuat = new JButton("Đăng xuất");	             
	        dangxuat.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        dangxuat.setBounds(412, 256, 143, 43);
	        panel_6.add(dangxuat);
	        
	        textField_3 = new JTextField();
	        textField_3.setBounds(11, 191, 391, 108);
	        panel_6.add(textField_3);
	        textField_3.setColumns(10);
	        loadData();
	        frame.setLocationRelativeTo(null);
	    }
	    
	    private void loadData(){
	        NonEditableTableModel model = new NonEditableTableModel(
	            new Object[]{"trade_date", "trade_Type", "amount"}, 0);
	        table.setModel(model);
	
	        // Thêm dữ liệu mẫu vào bảng (tùy chọn)
	        
	    }
	    
	    public void skchuyentien(ActionListener listener) {
	        nutchuyentien.addActionListener(listener);
	    }

	    public void sknaptien(ActionListener listener) {
	        nutnaptien.addActionListener(listener);
	    }
	    
	    public void sksd(ActionListener listener) {
	        sodu.addActionListener(listener);
	    }
	    
	    public JFrame getFrame() {
	        return frame;
	    }
	    
	    public String getstk() {
	        return texttk.getText();
	    }
	    
	    public String getsotien() {
	        return texttien.getText();
	    }
	    
	    public void setstkk(String tb) {
	    	texttk.setText(tb);
	    }
	    public void settienn(String tb) {
	    	texttien.setText(tb);
	    }
	    
	    public String gettiennap() {
	        return texttiennap.getText();
	    }
	    public void settiennap(String tb) {
	    	texttiennap.setText(tb);
	    }
	    
	    public void showMessage(String message) {
	       JOptionPane.showMessageDialog(null, message);
	    }
	    public void setten(String thongtin) {
	        textField.setText(thongtin);
	    }
	    public void setstk(String thongtin) {
	    	textField_5.setText(thongtin);
	    }
	    
	    public void skthaydoimk(ActionListener listener) {
	    	thaydoimk.addActionListener(listener);
	    }
	    
	    public void skdangxuat(ActionListener listener) {
	    	dangxuat.addActionListener(listener);
	    }
	    public String mkc() {
	    	return textField_1.getText();
	    }
	    public String mkm() {
	    	return textField_2.getText();
	    }
	    public void setmkc(String thongbao) {
	    	textField_1.setText(thongbao);
	    }
	    public void setmkm(String thongbao) {
	    	textField_2.setText(thongbao);
	    }
	    public void setlsgd(String thongbao) {
	    	textField_3.setText(thongbao);
	    }
	    public void lichsu(String thongbao) {
	    	textField_4.setText(thongbao);
	    }
	    public void skls(ActionListener listener) {
	    	reset.addActionListener(listener);
	    }
	    public void nuthotngtin(ActionListener listener) {
	    	nutthongtincanhan.addActionListener(listener);
	    }
	}