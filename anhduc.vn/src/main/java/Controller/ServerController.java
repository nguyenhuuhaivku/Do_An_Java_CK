package Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;

import View.ServerView;
import model.Chucnang;
import model.ServerModel;

public class ServerController {
    private static final int port = 1000;
    private ServerModel model;
    private ServerView view;

    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this.view = view;
        this.view.addStartButtonListener(e -> startServer());
    }

    private void startServer() {	
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                view.displayMessage("Server đang lắng nghe trên cổng " + port + "...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    view.displayMessage("Client đã kết nối từ " + clientSocket.getInetAddress().getHostAddress());
                    new ClientHandler(clientSocket, model, view).start();
                }
            } catch (Exception e) {
                view.displayMessage("Lỗi: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ServerModel model;
        private ServerView view;

        public ClientHandler(Socket socket, ServerModel model, ServerView view) {
            this.clientSocket = socket;
            this.model = model;
            this.view = view;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;
                Chucnang cn = new Chucnang();
                while ((inputLine = in.readLine()) != null) {
                    view.displayMessage("Nhận từ client: " + inputLine);

                    if ("lichsu".equals(inputLine)) {
                        String mk = in.readLine();
                        String thongbao = cn.hamhienlsdg(mk);
                        out.println(thongbao);
                    }

                    if ("dangky".equals(inputLine)) {
                        String tk = in.readLine();
                        String ten = in.readLine();
                        String mk = in.readLine();
                        String sdt = in.readLine();
                        String thongbao = cn.dangky(tk, mk, ten, sdt);
                        out.println(thongbao);
                    }
                    if ("dangnhap".equals(inputLine)) {
                        String sdt = in.readLine();
                        String matkhau = in.readLine();
                        String result = cn.dangnhap(sdt, matkhau);
                        out.println(result); // Gửi phản hồi cho client

                    }
                    if ("dangnhapthanhcong".equals(inputLine)) {
                        String sdt = in.readLine();
                        String tenTaiKhoan = cn.layTen(sdt);
                        out.println(tenTaiKhoan); // Gửi tên tài khoản cho client
                        String stk = cn.laytk(sdt);
                        out.println(stk);
                    }
                    if ("sodu".equals(inputLine)) {
                        String mk = in.readLine();
                        BigDecimal sd = cn.sodu(mk); // sd là đối tượng BigDecimal
                        String sdd = sd.toString(); // Chuyển đổi BigDecimal sang String
                        out.println("số dư " + sdd);
                    }

                    if ("chuyentien".equals(inputLine)) {
                        String sdt = in.readLine();
                        String tienguistring = in.readLine();
                        BigDecimal tiengui = new BigDecimal(tienguistring);
                        String tk = in.readLine();
                        String result = cn.chuyetien(sdt, tiengui, tk);
                        out.println(result);

                    }
                    if ("naptien".equals(inputLine)) {
                        String sdt = in.readLine();
                        String tiennap = in.readLine();
                        BigDecimal tn = new BigDecimal(tiennap);
                        String result = cn.naptien(sdt, tn);
                        out.println(result);
                    }
                    if ("doimatkhau".equals(inputLine)) {
                        String mkc = in.readLine();
                        String mkm = in.readLine();
                        String result = cn.thaydoimatkhau(mkc, mkm);
                        out.println(result);
                    }
                    if ("thongtincanhan".equals(inputLine)) {
                        String sdt = in.readLine();
                        String ten = cn.layTen(sdt);
                        String stk = cn.laytk(sdt);
                        String mk = cn.laymk(sdt);
                        out.println(ten);
                        out.println(stk);
                        out.println(mk);
                    }

                }
            } catch (Exception e) {
                view.displayMessage("Lỗi: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) {
        ServerModel model = new ServerModel();
        ServerView view = new ServerView();
        ServerController controller = new ServerController(model, view);
        view.setVisible(true);
    }
}