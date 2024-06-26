package View;

import java.util.Scanner;

public class ClientView {
    private Scanner scanner;

    public ClientView() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        System.out.print("Nhập dữ liệu bạn muốn gửi đến server: ");
        return scanner.nextLine();
    }
    
    public void login() {
    	Scanner sc = new Scanner(System.in);    	
    	System.out.println("1/ Đăng ký ");
    	System.out.println("2/ Đăng nhập");
    }
    
    public void menu() {
    	
    }

    public void displayMessage(String message) {
        System.out.println("Server trả về: " + message);
    }

    public void close() {
        scanner.close();
    }
}
