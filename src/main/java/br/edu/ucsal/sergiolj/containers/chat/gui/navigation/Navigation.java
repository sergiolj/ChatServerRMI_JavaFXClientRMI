package br.edu.ucsal.sergiolj.containers.chat.gui.navigation;

import br.edu.ucsal.sergiolj.containers.chat.gui.controller.ConfigController;
import br.edu.ucsal.sergiolj.containers.chat.gui.controller.ConnectController;
import br.edu.ucsal.sergiolj.containers.chat.gui.controller.MainViewController;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;

public class Navigation {
    private static Stage primaryStage;

    /**
     * Define o stage principal criado pelo JavaFX na classe MainApp pelo método start
     *
     * @param primaryStage Stage da janela primária.
     */
    public static void setPrimaryStage(Stage primaryStage) {
        Navigation.primaryStage = primaryStage;
    }

    /**
     * Cria uma janela primária da aplicação com os parâmetros fornecidos por métodos públicos da classe e definindo
     * ainda um tema CSS para toda a aplicação usando AtlantaFX Themes definidos no pom.xml atlantafx-base.
     * O tamanho utilizado pela janela está definido para a tela toda com opção de redimensionar pelo usuário.
     *
     * @param fxml Caminho do arquivo fxml com a estrutura de containers e outros objetos visuais.
     * @param tittle Título da janela
     * @param width Largura da janela
     * @param height Altura da janela
     */
    private static void loadInPrimaryWindow(String fxml, String tittle, double width, double height) {
        Application.setUserAgentStylesheet(new atlantafx.base.theme.Dracula().getUserAgentStylesheet());

        try {
            if (primaryStage == null) {
                throw new IllegalStateException("Stage principal não foi configurado corretamente no SceneManager. Verifique" +
                        "o uso do SceneManager.setStage(Stage stage) no MainApp. ");
            }
            URL fxmlURL = Navigation.class.getResource(fxml);
            if (fxmlURL == null) {
                throw new IllegalArgumentException("Arquivo Fxml não encontrado em: " + fxml);
            }

            Parent root = FXMLLoader.load(Objects.requireNonNull(Navigation.class.getResource(fxml)));

            Scene scene = new Scene(root, width, height); //cria um cenário de tamanho definido usando um objeto fxml
            String customCss = Objects.requireNonNull(Navigation.class.getResource("/styles/styles.css")).toExternalForm();
            scene.getStylesheets().add(customCss);

            primaryStage.setScene(scene);
            primaryStage.setTitle(tittle);
            primaryStage.centerOnScreen();
            primaryStage.setResizable(true);
            primaryStage.setFullScreen(false); // usa toda a tela sem exibir barra de visualização da janela
            primaryStage.setMaximized(false); // maximiza a janela mantendo a barra de tarefas do so

            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cria uma janela do tipo modal com os parâmetros fornecidos por métodos públicos da classe com tamanho definido
     * sem a opção de redimensionamento.
     *
     *
     * @param fxml Caminho do arquivo fxml com a estrutura de containers e outros objetos visuais.
     * @param tittle Título da janela
     * @param width Largura da janela
     * @param height Altura da janela
     */
    private static <T> void loadInModalWindow(String fxml, String tittle, double width, double height, Consumer<T> setupController) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(fxml));
            Parent root = loader.load();

            T controller = loader.getController();
            if(setupController !=null && controller != null){
                setupController.accept(controller);
            }

            Scene scene = new Scene(root);
            Stage stageModal = new Stage();
            stageModal.setScene(scene);

            stageModal.setTitle(tittle);
            stageModal.initOwner(primaryStage);
            stageModal.initModality(Modality.WINDOW_MODAL);

            stageModal.setResizable(false);
            stageModal.showAndWait();

        } catch (Exception e) {
            System.out.println("Erro ao carregar janela modal: " + fxml);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Define os parâmetros para a janela principal da aplicação.
     */
    public static void loadMainView() {
        String fxml = "/view/chat/chat_main.fxml";
        String tittle = "Chat Java RMI";
        double width = 800;
        double height = 600;
        loadInPrimaryWindow(fxml, tittle, width, height);
    }

    /**
     * Define os parâmetros para a janela de configuração de servidores de chat usando uma janela do tipo modal.
     *
     */
    public static void loadConfigView(MainViewController mainViewController) {
        String fxml = "/view/chat/chat_config.fxml";
        String tittle = "Server Configuration";
        double width = 400;
        double height = 450;
        loadInModalWindow(fxml, tittle, width, height,null);
    }

    /**
     * Define os parâmetros para a janela de conexão.
     * O controller da view deve ser fornecido, caso essa tenha que atualizar dados em outra janela, como no caso, a
     * janela principal MainView.
     */
    public static void loadConnectView(MainViewController mainViewController) {
        String fxml = "/view/chat/chat_connect.fxml";
        String tittle = "Connect to Server";
        double width = 400;
        double height = 450;
        loadInModalWindow (fxml, tittle, width, height, (ConnectController controller)->
                controller.setMainController(mainViewController));
    }
    /**
     * Essa foi uma janela About criada originalmente usando a classe Alert, mas, para ter certa customização sobre o
     * resultado, foi substituída pelo método usando Stage que possibilitou a customização da centralização e
     * das margens
     *
     */
    public static void about() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("About Chat Client RMI");

        //Uma janela modal é uma janela que bloqueia a janela atrás dela.
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20));

        Label title = new Label("Chat Client Application");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label description = new Label("Este app foi desenvolvido pelo autor para" +
                "\n explorar os conceitos e aplicações de Java FX.\n" +
                "Verifique as configurações do servidor RMI disponível.");
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
}

