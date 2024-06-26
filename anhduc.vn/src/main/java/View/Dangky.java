package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Controller.ClientController;

public class Dangky {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JLabel lblNewLabel_1;
    private JButton btnngNhp;
    private JButton btnNewButton;
    private JLabel lblNewLabel_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dangky window = new Dangky();
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
    public Dangky() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBounds(100, 100, 665, 444);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        btnNewButton = new JButton("Tạo tài khoản");
        btnNewButton.setBackground(Color.WHITE);
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setBounds(211, 292, 280, 35);
        frame.getContentPane().add(btnNewButton);

        textField = new JTextField();
        textField.setBounds(194, 114, 380, 29);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(194, 153, 380, 29);
        frame.getContentPane().add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(194, 192, 380, 29);
        frame.getContentPane().add(textField_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(194, 231, 380, 29);
        frame.getContentPane().add(textField_3);

        JLabel lblNewLabel = new JLabel("Số Tài Khoản");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel.setBounds(76, 114, 498, 35);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblTn = new JLabel("Tên");
        lblTn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTn.setBounds(76, 153, 498, 35);
        frame.getContentPane().add(lblTn);

        JLabel lblMtKhu = new JLabel("Mật Khẩu");
        lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblMtKhu.setBounds(76, 192, 498, 35);
        frame.getContentPane().add(lblMtKhu);

        JLabel lblSinThoi = new JLabel("Số Điện Thoại");
        lblSinThoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSinThoi.setBounds(76, 231, 498, 35);
        frame.getContentPane().add(lblSinThoi);

        lblNewLabel_1 = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblNewLabel_1.setBounds(219, 28, 261, 57);
        frame.getContentPane().add(lblNewLabel_1);

        btnngNhp = new JButton("Đăng Nhập");
        btnngNhp.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnngNhp.setBackground(Color.WHITE);
        btnngNhp.setBounds(210, 345, 284, 35);
        frame.getContentPane().add(btnngNhp);

        lblNewLabel_2 = new JLabel("Bạn đã có tài khoản?");
        lblNewLabel_2.setForeground(Color.RED);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_2.setBounds(291, 330, 129, 13);
        frame.getContentPane().add(lblNewLabel_2);
        frame.setLocationRelativeTo(null);
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getstk() {
        return textField.getText();
    }

    public String getname() {
        return textField_1.getText();
    }

    public String getmk() {
        return textField_2.getText();
    }

    public String getsdt() {
        return textField_3.getText();
    }
    
    public void setstk(String tb) {
    	textField.setText(tb);
    }
    public void setname(String tb) {
    	textField_1.setText(tb);
    }
    public void setmk(String tb) {
    	textField_2.setText(tb);
    }
    public void setsdt(String tb) {
    	textField_3.setText(tb);
    }

    public void addRegisterListener(ActionListener listener) {
        btnNewButton.addActionListener(listener);
    }

    public void skdangnhap(ActionListener listener) {
        btnngNhp.addActionListener(listener);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
