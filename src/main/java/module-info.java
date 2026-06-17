module br.edu.ucsal.sergiolj.containers {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;
    requires java.rmi;

    exports br.edu.ucsal.sergiolj.containers.keypads;
    opens br.edu.ucsal.sergiolj.containers.keypads to javafx.fxml;
    exports br.edu.ucsal.sergiolj.containers.keypads.navigation;
    opens br.edu.ucsal.sergiolj.containers.keypads.navigation to javafx.fxml;
    exports br.edu.ucsal.sergiolj.containers.keypads.model;
    opens br.edu.ucsal.sergiolj.containers.keypads.model to javafx.fxml;
    exports br.edu.ucsal.sergiolj.containers.keypads.controller;
    opens br.edu.ucsal.sergiolj.containers.keypads.controller to javafx.fxml;

    exports br.edu.ucsal.sergiolj.containers.chat.gui.controller;
    opens br.edu.ucsal.sergiolj.containers.chat.gui.controller to javafx.fxml;
    exports br.edu.ucsal.sergiolj.containers.chat.gui;
    opens br.edu.ucsal.sergiolj.containers.chat.gui to javafx.fxml;
    exports br.edu.ucsal.sergiolj.containers.chat.shared;
    opens br.edu.ucsal.sergiolj.containers.chat.shared to javafx.fxml;
    exports br.edu.ucsal.sergiolj.containers.chat.server;
    opens br.edu.ucsal.sergiolj.containers.chat.server to javafx.fxml;
}