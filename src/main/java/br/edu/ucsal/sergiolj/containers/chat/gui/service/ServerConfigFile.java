package br.edu.ucsal.sergiolj.containers.chat.gui.service;

import br.edu.ucsal.sergiolj.containers.chat.gui.util.ChatServers;
import br.edu.ucsal.sergiolj.containers.chat.gui.util.ServerSpecs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ServerConfigFile {

    public static List<ServerSpecs> readFromFile(String path) throws IOException {
        List<ServerSpecs> serversList = new ArrayList<>();
        try {
            String fileContent = Files.readString(Path.of(path));
            String[] serverData = fileContent.split(";");

            for (String currentServer : serverData) {
                if (currentServer.trim().isEmpty()) continue;
                String[] server = currentServer.split(",");
                if (server.length == 3) {
                    String name = server[0].trim();
                    String IP = server[1].trim();
                    int port = Integer.parseInt(server[2].trim());

                    serversList.add(new ServerSpecs(name, IP, port));
                }
            }
        }catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return serversList;
    }

    public static void saveToFile() throws IOException {
        List<ServerSpecs> serverslist = ChatServers.getInstance().getServersList();
        StringBuilder sb = new StringBuilder();
        for(ServerSpecs server: serverslist){
            sb.append(server.getName()).append(",")
                    .append(server.getIP()).append(",")
                    .append(server.getPort()).append(";\n");
        }
        File file = new File(ChatServers.filePath);
        try{
            Files.writeString(
                    file.toPath(),
                    sb.toString(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        }catch (IOException e){
            System.out.println("Erro crítico ao salvar o arquivo: " + e.getMessage());
        }


    }
}
