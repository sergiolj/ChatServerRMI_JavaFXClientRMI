package br.edu.ucsal.sergiolj.containers.chat.gui.controller;


import br.edu.ucsal.sergiolj.containers.chat.gui.service.NetworkDataChecker;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServerSpecs;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServers;
import br.edu.ucsal.sergiolj.containers.chat.shared.Config;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;


public class ConfigController {

    @FXML
    private TextField txf_server_name, txf_server_port, txf_ip_address;

    @FXML
    private ChoiceBox<ChatServerSpecs> chbox_servers_list;

    @FXML
    private Button btn_save;

    @FXML
    public void initialize(){
        txf_server_name.setText(Config.getServerName());
        txf_server_port.setText(String.valueOf(Config.getServerPort()));
        txf_ip_address.setText(Config.getIpAddress());

        BooleanBinding dataMatchesOriginal = Bindings.createBooleanBinding(()->{
            boolean ipMatches = txf_ip_address.getText().equals(Config.getIpAddress());
            boolean nameMatches = txf_server_name.getText().equals(Config.getServerName());
            boolean portMatches = txf_server_port.getText().equals(String.valueOf(Config.getServerPort()));

            return nameMatches && ipMatches && portMatches;
        },
                //Sempre que qualquer dos campos mudar execute a verificação e retorne o valor dos booleans para o botão
                txf_server_name.textProperty(),
                txf_ip_address.textProperty(),
                txf_server_port.textProperty());

        btn_save.disableProperty().bind(dataMatchesOriginal);

        List<ChatServerSpecs> srvList = ChatServers.getInstance().getServersList();
        chbox_servers_list.getItems().addAll(srvList);
        if(!chbox_servers_list.getItems().isEmpty()) {
            chbox_servers_list.getSelectionModel().select(0);
        }
    }

    @FXML
    private void saveAndClose(ActionEvent actionEvent) {
        String name = txf_server_name.getText();
        String ip = txf_ip_address.getText();
        String port = String.valueOf(txf_server_port.getText());

        if(!NetworkDataChecker.validadeData(name, ip, port)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Data");
            alert.setHeaderText("Please verify your input data.");
            alert.setContentText("""
                    • Server name cannot contain spaces.
                    • Port number must be a valid integer.
                    • Please enter a valid IP address.""");
            alert.showAndWait();
        }

        Config.setServerName(name);
        Config.setIpAddress(ip);
        Config.setServerPort(Integer.parseInt(port));

        closeWindow(actionEvent);
    }

    /**
     * Fecha a janela corrente
     * @param actionEvent
     */
    @FXML
    private void closeWindow(ActionEvent actionEvent) {
        Node component = (Node) actionEvent.getSource();
        Stage stage = (Stage) component.getScene().getWindow();
        stage.close();
    }
}
