package br.edu.ucsal.sergiolj.containers.chat.gui;

import br.edu.ucsal.sergiolj.containers.chat.gui.navigation.Navigation;
import javafx.application.Application;

import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Navigation.setPrimaryStage(stage); //envia a referência do stage inicial criado pelo FX para a classe Navigation
        Navigation.loadMainView(); //carrega a primeira janela do app.
    }

    public static void main(String[] args) {
        launch();
    }
}