package br.edu.ucsal.sergiolj.containers.chat.gui.controller;

import br.edu.ucsal.sergiolj.containers.chat.gui.navigation.Navigation;
import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.rmi.RemoteException;
import java.util.List;

public class MainViewController {
    private ClientInterface currentUser = null;

    @FXML
    private ListView<String> listv_online_users;

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
        listv_online_users.getItems().clear();
        listv_online_users.getItems().addAll(onlineUsers);
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
            }
            /* Quando o usuário se desliga do servidor ele não faz mais parte da lista de broadcast, por isso, o próprio
             controller deve apagar a sua lista de usuários online. */
            listv_online_users.getItems().clear();

        }catch (RemoteException e){
            System.err.println("Erro ao notificar o servidor sobre a desconexão: " + e.getMessage());
        }

    }
}
