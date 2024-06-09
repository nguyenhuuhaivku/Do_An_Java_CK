package Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import View.ServerView;
import model.ServerModel;

public class ServerController {
	private static final int port=1000;
    private ServerModel model;
    private ServerView view;

    public ServerController(ServerModel model, ServerView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            view.displayMessage("Server đang lắng nghe trên cổng " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                view.displayMessage("Client đã kết nối từ " + clientSocket.getInetAddress().getHostAddress());
                new ClientHandler(clientSocket, model, view).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                while ((inputLine = in.readLine()) != null) {
                    view.displayMessage("Nhận từ client: " + inputLine);
                    String result = model.processData(inputLine);
                    out.println(result);
                }
            } catch (Exception e) {
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
        controller.start();
    }
}
