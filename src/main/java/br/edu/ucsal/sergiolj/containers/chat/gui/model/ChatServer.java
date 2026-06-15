package br.edu.ucsal.sergiolj.containers.chat.gui.model;

public class ChatServer {
    private String name;
    private String IP;
    private Integer port;

    public ChatServer(String name, String IP, Integer port) {
        this.name = name;
        this.IP = IP;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
