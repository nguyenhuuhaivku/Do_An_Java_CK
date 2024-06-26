package View;

//public class ServerView {
//    public void displayMessage(String message) {
//        System.out.println(message);
//    }
//}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ServerView extends JFrame {
    private JTextArea textArea;
    private JButton startButton;

    public ServerView() {
        // Thiết lập cửa sổ
        setTitle("Server");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo các thành phần giao diện
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        startButton = new JButton("Start Server");

        // Thiết lập layout và thêm các thành phần vào cửa sổ
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }
    public void displayMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
        });
    }
    

    // Phương thức để thêm lắng nghe sự kiện cho nút bắt đầu server
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}