package model;

import java.util.Scanner;

public class ServerModel {
    public String processData(String n) {
    	Chucnang cn= new Chucnang();
    	Scanner sc = new Scanner(System.in);
    	System.out.println("nhập số tài khoản");
    	String accountnumber= sc.nextLine();
    	System.out.println("nhập mật khẩu");
    	String password= sc.nextLine();
    	System.out.println("nhập tên");
    	String name= sc.nextLine();
    	System.out.println("nhập sdt");
    	String sdt = sc.nextLine();
    	if(n.equals("1")) {
//    		cn.dangky(String accountnumber, String password, String name, String sdt, null);
    		cn.dangky(accountnumber, password, name, sdt);   	
        System.out.println();
    }
    	
    	return null;
    }
}

