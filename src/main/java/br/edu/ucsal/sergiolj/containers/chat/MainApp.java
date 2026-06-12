package br.edu.ucsal.sergiolj.containers.chat;

import atlantafx.base.theme.*;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new atlantafx.base.theme.Dracula().getUserAgentStylesheet());

        try {
            URL fxmlURL = MainApp.class.getResource("/view/chat/chat_main.fxml");
            if (fxmlURL == null) {
                throw new IllegalArgumentException("Arquivo Fxml não encontrado");
            }
            FXMLLoader loader = new FXMLLoader(fxmlURL);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setFullScreen(false); // usa toda a tela sem exibir barra de visualização da janela
            stage.setMaximized(true); // maximiza a janela mantendo a barra de tarefas do so


            stage.setScene(scene);
            stage.setTitle("Cliente chat RMI");
            stage.centerOnScreen();
            stage.show();

        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}