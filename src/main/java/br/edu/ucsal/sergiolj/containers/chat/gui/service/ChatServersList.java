package br.edu.ucsal.sergiolj.containers.chat.gui.service;

import br.edu.ucsal.sergiolj.containers.chat.gui.model.ChatServer;

import java.util.ArrayList;
import java.util.List;

public class ChatServersList {
    private List<ChatServer> serversList = new ArrayList<>();

    public List<ChatServer> getServersList() {
        return serversList;
    }

    public void setServersList(List<ChatServer> serversList) {
        this.serversList = serversList;
    }
}
