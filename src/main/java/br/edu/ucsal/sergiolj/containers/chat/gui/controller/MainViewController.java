package br.edu.ucsal.sergiolj.containers.chat.gui.controller;

import br.edu.ucsal.sergiolj.containers.chat.gui.navigation.Navigation;
import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.rmi.RemoteException;
import java.util.List;

public class MainViewController {
    @FXML
    private TextArea txt_area_message;
    private ClientInterface currentUser = null;

    @FXML
    private ListView<String> lst_online_users, lst_messages;

    @FXML
    public void initialize() {
        txt_area_message.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (event.isShiftDown()) {
                    txt_area_message.appendText("\n");
                    event.consume();
                } else {
                    event.consume();
                    try {
                        currentUser.sendMessageCommand(txt_area_message.getText().trim());
                        txt_area_message.clear();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }


    @FXML
    private void configServerSpecs(ActionEvent actionEvent) {
        Navigation.loadConfigView(this);
    }

    /**
     * Ao sair do aplicativo o usuário registrado deve ser desligado antes, para isso é executada a desconexão.
     * @param actionEvent
     * @throws RemoteException
     */
    @FXML
    private void exitApp(ActionEvent actionEvent) throws RemoteException {
        disconnectFromServer();
        System.exit(0);
    }

    /**
     * Exibe a lista de usuários conectados em uma ListView<String></String> na janela principal.
     * O container ListView é uma espécie de Vbox com ScrollPane automático.
     *
     * @param onlineUsers List<ClientInterface> instanciada no ChatServerRemoteObject e fornecida pelo
     *                    getOnlineClients() em formato de List<String>
     */
    public void refreshOnlineUsers(List<String> onlineUsers) {
        lst_online_users.getItems().clear();
        lst_online_users.getItems().addAll(onlineUsers);
    }

    public void publishMessage(String message) {
        lst_messages.getItems().add(message);
        int lastIndex = lst_messages.getItems().size() - 1;
        lst_messages.scrollTo(lastIndex);
    }

    public void connectToServer(ActionEvent actionEvent) {
        Navigation.loadConnectView(this);
    }

    public void about(ActionEvent actionEvent) {
        Navigation.about();
    }

    public ClientInterface getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(ClientInterface currentUser) {
        this.currentUser = currentUser;
    }

    public void disconnectFromServer() throws RemoteException {
        try{
            if(currentUser != null){
                currentUser.disconnect(this);
                currentUser = null;
                txt_area_message.clear();
                lst_messages.getItems().clear();
            }
            /* Quando o usuário se desliga do servidor ele não faz mais parte da lista de broadcast, por isso, o próprio
             controller deve apagar a sua lista de usuários online. */
            lst_online_users.getItems().clear();

        }catch (RemoteException e){
            System.err.println("Erro ao notificar o servidor sobre a desconexão: " + e.getMessage());
        }

    }
}
