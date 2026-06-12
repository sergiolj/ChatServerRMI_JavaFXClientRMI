package br.edu.ucsal.sergiolj.containers.chat.controller;

import br.edu.ucsal.sergiolj.containers.chat.navigation.Navigation;
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
}
