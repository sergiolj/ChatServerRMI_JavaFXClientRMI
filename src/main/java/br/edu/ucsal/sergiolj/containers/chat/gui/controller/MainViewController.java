package br.edu.ucsal.sergiolj.containers.chat.gui.controller;

import br.edu.ucsal.sergiolj.containers.chat.gui.navigation.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainViewController {

    @FXML
    private void configServerSpecs(ActionEvent actionEvent) {
        Navigation.loadConfigView();
    }

    @FXML
    private void exitApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void connectToServer(ActionEvent actionEvent) {
        Navigation.loadConnectView();
    }

    public void about(ActionEvent actionEvent) {
    }
}
