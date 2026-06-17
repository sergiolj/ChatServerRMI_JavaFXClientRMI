package br.edu.ucsal.sergiolj.containers.chat.gui.util;

import br.edu.ucsal.sergiolj.containers.chat.gui.service.ServerConfigFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatServers {
    private static ChatServers instance;
    private final List<ServerSpecs> serversList = new ArrayList<>();
    public static final String filePath =
            "C:/Users/sljunior/IdeaProjects/javafxContainers/src/main/resources/config/servers.txt";

    public static ChatServers getInstance() {
        if (instance == null) {
            instance = new ChatServers();
        }
        return instance;
    }

    private ChatServers() {
        loadServersList();
    }

    public List<ServerSpecs> getServersList() {
        return serversList;
    }

    private void loadServersList() {
        try {
            List<ServerSpecs> loadedServers = ServerConfigFile.readFromFile(filePath);
            serversList.clear();
            serversList.addAll(loadedServers);
            System.out.println("Lista de servidores carregada com sucesso");
            for(ServerSpecs server: serversList){
                System.out.println(server.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public void updateServer(){
    }

    }