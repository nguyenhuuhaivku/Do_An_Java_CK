package View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class Test {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
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
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Th\u00F4ng tin c\u00E1 nh\u00E2n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(11, 16, 416, 243);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(176, 175, 184, 27);
		panel.add(textField_3);
		
		JLabel lblNewLabel = new JLabel("Tên");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(114, 68, 39, 32);
		panel.add(lblNewLabel);
		
		JLabel lblSTiKhon = new JLabel("Số tài khoản");
		lblSTiKhon.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSTiKhon.setBounds(55, 101, 95, 32);
		panel.add(lblSTiKhon);
		
		JLabel lblMtKhu = new JLabel("Mật khẩu");
		lblMtKhu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMtKhu.setBounds(81, 140, 71, 32);
		panel.add(lblMtKhu);
		
		JLabel lblSinThoi = new JLabel("Số điện thoại");
		lblSinThoi.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSinThoi.setBounds(51, 173, 95, 32);
		panel.add(lblSinThoi);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(176, 101, 184, 27);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(176, 64, 184, 27);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(176, 139, 184, 27);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		btnNewButton = new JButton("Quay lại");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(10, 25, 112, 32);
		panel.add(btnNewButton);
		frame.setLocationRelativeTo(null);
	}
	public JFrame getframe() {
		return frame;
	}
	public void showMessage(String message) {
	       JOptionPane.showMessageDialog(null, message);
	}

	public void setten(String tb) {
		textField.setText(tb);
	}
	public void setstk(String tb) {
		textField_1.setText(tb);
	}
	public void setmk(String tb) {
		textField_2.setText(tb);
	}
	public void setsdt(String tb) {
		textField_3.setText(tb);
	}
	 public void skql(ActionListener listener) {
		 btnNewButton.addActionListener(listener);
	   }
}
