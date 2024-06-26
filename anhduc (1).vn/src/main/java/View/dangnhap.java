
package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JTextField;

import Controller.ClientController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;

public class dangnhap {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JButton btnNewButton; // Khai báo trường của lớp
    private JButton btnNewButton_1; // Khai báo trường của lớp

    /**
     * Chạy ứng dụng.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    dangnhap window = new dangnhap();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Tạo ứng dụng.
     */
    public dangnhap() {
        initialize();
    }

    /**
     * Khởi tạo nội dung của khung.
     */
    private void initialize() {
        
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Khởi tạo btnNewButton
        btnNewButton = new JButton("Đăng Nhập");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.setBounds(86, 186, 138, 40);
        frame.getContentPane().add(btnNewButton);

        // Khởi tạo btnNewButton_1
        btnNewButton_1 = new JButton("Đăng Ký");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton_1.setBounds(234, 186, 138, 40);
        frame.getContentPane().add(btnNewButton_1);

        textField = new JTextField();
        textField.setBounds(168, 86, 210, 26);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Số Điện Thoại");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel.setBounds(80, 86, 78, 26);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Mật khẩu");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(86, 122, 69, 26);
        frame.getContentPane().add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(168, 124, 210, 26);
        frame.getContentPane().add(textField_1);

        JLabel lblNewLabel_2 = new JLabel("Đăng Nhập");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblNewLabel_2.setBounds(157, 17, 130, 49);
        frame.getContentPane().add(lblNewLabel_2);
        frame.setLocationRelativeTo(null);
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getUsername() {
        return textField.getText();
    }

    public String getPassword() {
        return textField_1.getText();
    }

    public void showMessage(String message) {
       JOptionPane.showMessageDialog(null, message);
    }

    public void setmk(String thongtin) {
    	textField_1.setText(thongtin);
    }
    public void setsdt(String thongtin) {
    	textField.setText(thongtin);
    }
    // Thêm ActionListener vào các trường của lớp
    public void addRegisterListener(ActionListener listener) {
        btnNewButton.addActionListener(listener);
    }

    public void addRegisterListener1(ActionListener listener) {
        btnNewButton_1.addActionListener(listener);
    }
    public void showwMessage(String message) {
        // Tạo JDialog tùy chỉnh
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setUndecorated(true);  // Loại bỏ các nút đóng mặc định
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(null); // Hiển thị giữa màn hình

        // Thêm thông báo vào JDialog
        JLabel label = new JLabel(message);
        dialog.add(label);

        // Hiển thị JDialog
        dialog.setVisible(true);

        // Thiết lập tự động đóng sau 3 giây
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dispose();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }

}