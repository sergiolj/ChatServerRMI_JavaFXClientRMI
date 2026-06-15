package br.edu.ucsal.sergiolj.containers.chat.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectController {
    @FXML
    private TextField txf_user_name;
    @FXML
    private ListView lst_server_configs;

    @FXML
    private void closeWindow(ActionEvent actionEvent) {
        Node component = (Node) actionEvent.getSource();
        Stage stage = (Stage) component.getScene().getWindow();
        stage.close();
    }

    public void connectToServer(ActionEvent actionEvent) {
    }
}
