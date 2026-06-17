package br.edu.ucsal.sergiolj.containers.chat.gui.controller;

import br.edu.ucsal.sergiolj.containers.chat.gui.model.Client;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.AlertWindow;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ServerSpecs;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
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
    @FXML
    private Button btn_connect;
    @FXML
    private MainViewController mainController;
    @FXML
    private TextField txf_user_name;
    @FXML
    private ChoiceBox<ServerSpecs> chbox_servers_list;

    /**
     *
     */
    @FXML
    public void initialize() {
        /* Cria um EventFilter para capturar o ENTER do usuário e acionar o botão de connect.
         */
        txf_user_name.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btn_connect.fire();
                event.consume();
            }
        });

        /* Estas linhas devem ser removidas e essa implementação do CheckBox substituída assim que
        * o salvamento das configurações de servidor forem implementadas.*/
        List<ServerSpecs> mockList = ChatServers.getInstance().getServersList();
        chbox_servers_list.getItems().addAll(mockList);
        if(!chbox_servers_list.getItems().isEmpty()) {
            chbox_servers_list.getSelectionModel().select(0);
        }
    }

    /**
     * Determina o controlador da janela principal para possibilitar a atualização daquela através dessa stage.
     *
     * @param mainController é o controlador da MainViewController
     */
    public void setMainController(MainViewController mainController){
        this.mainController = mainController;
    }

    @FXML
    private void closeWindow(ActionEvent actionEvent) {
        Node component = (Node) actionEvent.getSource();
        Stage stage = (Stage) component.getScene().getWindow();
        stage.close();
    }

    /**
     * Cria a conexão com o servidor ao criar o usuário Client.
     * Testa se existe um usuário já conectado e emite uma mensagem de alerta
     *
     * @param actionEvent
     */
    public void connectToServer(ActionEvent actionEvent) {
        if(txf_user_name.getText().isEmpty()) {
            AlertWindow.showError("Erro", "Insira um nome de usuário", actionEvent);
            return;
        }
        if(mainController.getCurrentUser() != null ){
            AlertWindow.showWarning("Aviso", "Para se conectar com um novo usuário é necessário\n" +
                    "desconectar o usuário atual: " + mainController.getCurrentUser().toString(), actionEvent);
            return;
        }
        // Armazena a informação capturada da interface antes de executar a Thread
        final String userName = txf_user_name.getText();
        btn_connect.setDisable(true);
        btn_connect.getScene().setCursor(Cursor.WAIT);

        Thread connectionThread = new Thread(() -> {
            try{
                mainController.setCurrentUser(
                        new Client(userName,
                                this.mainController,
                                chbox_servers_list.getSelectionModel().getSelectedItem()
                        ));
                /* Só a UI Thread do JavaFX pode controlar janelas, então o comando Platform.ruLater avisa a thread
                principal que essa pode fechar a janela de conexão. */
                Platform.runLater(() -> {
                    closeWindow(actionEvent);
                });
            /* Captura o erro que ocorre no momento da instância da classe Client
            * private void connectToServer() throws RemoteException, NotBoundException */
            } catch (RemoteException | NotBoundException e) {
                Platform.runLater(()->{
                    AlertWindow.showError("Erro de conexão.", "Verifique se o servidor RMI está online e" +
                            " se os parâmetros de configuração do servidor estão corretos.", actionEvent);
                });
                btn_connect.setDisable(false);
                btn_connect.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        //Caso o programa seja fechado termine essa Thread
        connectionThread.setDaemon(true);
        //Execute a configuração acima.
        connectionThread.start();
    }
}
