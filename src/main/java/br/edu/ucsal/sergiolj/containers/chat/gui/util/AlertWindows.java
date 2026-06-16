package br.edu.ucsal.sergiolj.containers.chat.gui.util;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Objects;

/**
 *
 *
 * @author Sérgio Lopes Júnior
 *
 * @version 1.0
 */
public class AlertWindows {
    private static Window owner;

    public static void showWarning(String title, String message, ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        defineWindowOwner(event);
        if (owner != null) {
            alert.initOwner(owner);
        }
        alert.showAndWait();
    }

    private static void defineWindowOwner(ActionEvent event) {
        Node node = (Node) event.getSource();
        owner = node.getScene().getWindow();
    }

    public static void showError(String title, String message, ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        defineWindowOwner(event);
        if (owner != null) {
            alert.initOwner(owner);
        }
        alert.showAndWait();
    }

    public static void showConfirmation(String title, String message,ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        defineWindowOwner(event);
        if (owner != null) {
            alert.initOwner(owner);
        }
        alert.showAndWait();
    }

    private static void applyCustomCSS(Alert alert) {
        String css = Objects.requireNonNull(AlertWindows.class.getResource("/styles/custom_atlanta_fx.css")).toExternalForm();
        alert.getDialogPane().getStylesheets().add(css);
    }

    /**
     * Essa foi uma janela About criada originalmente usando a classe Alert, mas para ter certa customização sobre o
     * resultado, foi substituída pelo método usando Stage que possibilitou uma customização da centralização e
     * das margens
     *
     */
    public static void about() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("About Runner");

        //Uma janela modal é uma janela que bloqueia a janela atrás dela.
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));

        Label title = new Label("Runner Application");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label description = new Label("Este é um app inventado pelo autor para" +
                "\n explorar os conceitos e aplicações de Java FX\"");
        description.setTextAlignment(TextAlignment.CENTER);

        Button exit = new Button("Close");
        exit.setOnAction(event -> {
            dialogStage.close();
        });

        vbox.getChildren().addAll(title, description, exit);
        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);

        dialogStage.sizeToScene();
        dialogStage.setResizable(false);
        dialogStage.showAndWait();
    }

    @Deprecated
    public static void aboutDeprecated(){
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Runner Application");
        alert.setHeaderText("About");
        alert.setContentText("Este é um app inventado pelo autor para\n explorar os conceitos e aplicações de Java FX");

        //Insere um botão de close já que o AlertType.NONE náo possui botões.
        alert.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        alert.showAndWait();
    }
}