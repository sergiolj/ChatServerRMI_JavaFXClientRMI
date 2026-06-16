package br.edu.ucsal.sergiolj.containers.chat.gui.controller;

import br.edu.ucsal.sergiolj.containers.chat.gui.model.Client;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.AlertWindows;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServerSpecs;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServers;

import br.edu.ucsal.sergiolj.containers.chat.shared.ClientInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;
/**
 * O controller é uma janela modal, uma das características dessas janelas é que elas não mantêm nada em memória,
 * são descartáveis, isso quer dizer que nada pode ser salvo nelas para ser usado depois nem mesmo por ela mesma.
 * Por isso, criar uma variável de usuário aqui não funciona.
 *
 * private ClientInterface currentUser = null;
 */

public class ConnectController {
    private MainViewController mainController;

    @FXML
    private TextField txf_user_name;
    @FXML
    private ChoiceBox<ChatServerSpecs> chbox_servers_list;

    @FXML
    public void initialize() {
        List<ChatServerSpecs> mockList = ChatServers.getInstance().getServersList();
        chbox_servers_list.getItems().addAll(mockList);
        if(!chbox_servers_list.getItems().isEmpty()) {
            chbox_servers_list.getSelectionModel().select(0);
        }
    }
    public void setMainController(MainViewController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent) {
        Node component = (Node) actionEvent.getSource();
        Stage stage = (Stage) component.getScene().getWindow();
        stage.close();
    }

    public void connectToServer(ActionEvent actionEvent) {
        if(txf_user_name.getText().isEmpty()) {
            AlertWindows.showError("Erro", "Insira um nome de usuário", actionEvent);
            return;
        }
        if(mainController.getCurrentUser() != null ){
            AlertWindows.showWarning("Aviso", "Para se conectar com um novo usuário é necessário\n" +
                    "desconectar o usuário atual: " + mainController.getCurrentUser().toString(), actionEvent);
            return;
        }
        Thread connectionThread = new Thread(() -> {
            try{
                String userName = txf_user_name.getText();
                mainController.setCurrentUser(new Client(userName, this.mainController));
                Platform.runLater(() -> {
                    closeWindow(actionEvent);
                });

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        connectionThread.setDaemon(true);
        connectionThread.start();
    }
}
