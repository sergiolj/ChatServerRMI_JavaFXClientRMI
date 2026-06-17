package br.edu.ucsal.sergiolj.containers.chat.gui.controller;


import br.edu.ucsal.sergiolj.containers.chat.gui.service.NetworkDataChecker;
import br.edu.ucsal.sergiolj.containers.chat.gui.service.ServerConfigFile;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.AlertWindow;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ServerSpecs;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServers;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class ConfigController {

    @FXML
    private TextField txf_server_name, txf_server_port, txf_ip_address;

    @FXML
    private ChoiceBox<ServerSpecs> chbox_servers_list;

    @FXML
    private Button btn_save;

    @FXML
    public void initialize(){
        List<ServerSpecs> srvList = ChatServers.getInstance().getServersList();
        chbox_servers_list.getItems().addAll(srvList);
        if(!chbox_servers_list.getItems().isEmpty()) {
            chbox_servers_list.getSelectionModel().selectFirst();
            chbox_servers_list.getSelectionModel().selectedItemProperty()
                    .addListener((observable,
                                  oldServer, newServer) -> {
                        if(newServer != null){
                            txf_server_name.setText(newServer.getName());
                            txf_ip_address.setText(newServer.getIP());
                            txf_server_port.setText(String.valueOf(newServer.getPort()));
                        }
                    });
        }

        txf_server_name.setText(String.valueOf(chbox_servers_list.getValue().getName()));
        txf_server_port.setText(String.valueOf(chbox_servers_list.getValue().getPort()));
        txf_ip_address.setText(String.valueOf(chbox_servers_list.getValue().getIP()));

        // Cria uma referência entre dois atributos e permanece verificando a validade desta referência.
        BooleanBinding dataMatchesOriginal = Bindings.createBooleanBinding(()->{
            boolean ipMatches = txf_ip_address.getText().equals(String.valueOf(chbox_servers_list.getValue().getIP()));
            boolean nameMatches = txf_server_name.getText().equals(String.valueOf(chbox_servers_list.getValue().getName()));
            boolean portMatches = txf_server_port.getText().equals(String.valueOf(chbox_servers_list.getValue().getPort()));

            return nameMatches && ipMatches && portMatches;
        },
                //Sempre que qualquer dos campos mudar execute a verificação e retorne o valor dos booleans para o botão
                txf_server_name.textProperty(),
                txf_ip_address.textProperty(),
                txf_server_port.textProperty());

        btn_save.disableProperty().bind(dataMatchesOriginal);
    }

    /**
     * Verifica e posteriormente salva as configurações de conexão com o servidor.
     *
     * @param actionEvent
     */
    @FXML
    private void saveAndClose(ActionEvent actionEvent) {
        String name = txf_server_name.getText();
        String ip = txf_ip_address.getText();
        String port = String.valueOf(txf_server_port.getText());

        if(!NetworkDataChecker.validadeData(name, ip, port)){
            AlertWindow.showError("Dados Inválidos",
                    """
                    • Por favor, verifique os dados fornecidos.
                    • Nome do servidor não pode conter espaços.
                    • Porta de rede precisa ter um valor válido e inteiro.
                    • Insira um endereço de IP válido.
                    """
                    , actionEvent);
        }

        ServerSpecs selectedServer = chbox_servers_list.getSelectionModel().getSelectedItem();
        if(selectedServer !=null){
            selectedServer.setName(txf_server_name.getText());
            selectedServer.setIP(txf_ip_address.getText());
            selectedServer.setPort(Integer.parseInt(txf_server_port.getText()));
            try {
                ServerConfigFile.saveToFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
