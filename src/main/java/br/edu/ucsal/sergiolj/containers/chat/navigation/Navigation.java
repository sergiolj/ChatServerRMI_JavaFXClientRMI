package br.edu.ucsal.sergiolj.containers.chat.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Navigation {
    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage){
        Navigation.primaryStage = primaryStage;
    }

    private static void loadInPrimaryWindow(String fxml, String tittle, double width, double height){
        try{
            if(primaryStage == null){
                throw new IllegalStateException("Stage principal não foi configurado corretamente no SceneManager. Verifique" +
                        "o uso do SceneManager.setStage(Stage stage) no MainApp. ");
            }
            URL fxmlURL = Navigation.class.getResource(fxml);
            if(fxmlURL == null){
                throw new IllegalArgumentException("Arquivo Fxml não encontrado em: " + fxml);
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(Navigation.class.getResource(fxml)));

            Scene scene = new Scene(root, width, height);
            primaryStage.setScene(scene);
            scene.getStylesheets().add(Objects.requireNonNull(
                    Navigation.class.getResource("/styles/styles.css")).toExternalForm());
            primaryStage.setTitle(tittle);
            primaryStage.centerOnScreen();
            primaryStage.sizeToScene();
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadMainView(){
        String fxml = "/view/chat/main_view.fxml";
        String tittle = "Chat Java RMI";
        double width = 420;
        double height = 450;
        loadInPrimaryWindow(fxml,tittle,width,height);
    }
    public static void loadConfigView(){
        String fxml = "/view/chat/chat_config.fxml";
        String tittle = "Server Configuration";
        double width = 400;
        double height = 450;
        loadInModalWindow(fxml,tittle,width,height);
    }

    private static void loadInModalWindow(String fxml, String tittle, double width, double height) {
        try{
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stageModal = new Stage();
            stageModal.setScene(scene);

            stageModal.setTitle(tittle);
            stageModal.initOwner(primaryStage);
            stageModal.initModality(Modality.WINDOW_MODAL);

            stageModal.setResizable(false);
            stageModal.showAndWait();


        }catch (Exception e){
            System.out.println("Erro ao carregar janela modal: " + fxml);
        }
    }
}
