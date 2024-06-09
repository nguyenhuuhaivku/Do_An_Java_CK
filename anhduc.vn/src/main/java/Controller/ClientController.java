package Controller;

import View.ClientView;
import model.ClientModel;

public class ClientController {
	private static final int port=1000;
	private static String url="localhost";
    private ClientModel model;
    private ClientView view;

    public ClientController(ClientModel model, ClientView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        try {
            model.connect(url, port);
            view.login();
            String userInput = view.getUserInput();           
            model.sendMessage(userInput);
            String serverResponse = model.receiveMessage();
            view.displayMessage(serverResponse);
            model.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            view.close();
        }
    }

    public static void main(String[] args) {
        ClientModel model = new ClientModel();
        ClientView view = new ClientView();
        ClientController controller = new ClientController(model, view);
        controller.start();
    }
}
