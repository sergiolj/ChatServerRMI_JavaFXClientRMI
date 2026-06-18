package br.edu.ucsal.sergiolj.chatrmi.gui.util;

import br.edu.ucsal.sergiolj.chatrmi.gui.service.ServerConfigFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ChatServers {
    private static ChatServers instance;
    private final List<ServerSpecs> serversList = new ArrayList<>();
    public static final Path filePath = Paths.get("servers.txt");


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
            if(!Files.exists(filePath)){
                System.out.println("Arquivo de configuração não encontrado. Criando 'server.txt' padrão...");
                String serverDefaultList = "RMI_ChatServer,127.0.0.1,1099;";
                Files.writeString(filePath,
                        serverDefaultList,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE);
            }
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
}