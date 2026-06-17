package br.edu.ucsal.sergiolj.containers.chat.server;

public class Config {
    private static final String SERVER_NAME = "RMI_ChatServer";
    private static final Integer SERVER_PORT = 1099;

    public static String getServerName() {
        return SERVER_NAME;
    }

    public static Integer getServerPort() {
        return SERVER_PORT;
    }
}
