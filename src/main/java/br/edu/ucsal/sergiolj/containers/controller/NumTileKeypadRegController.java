package br.edu.ucsal.sergiolj.containers.controller;

import br.edu.ucsal.sergiolj.containers.model.KeyboardLayout;
import br.edu.ucsal.sergiolj.containers.model.NumericCard;
import br.edu.ucsal.sergiolj.containers.navigation.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NumTileKeypadRegController implements Initializable{
    @FXML
    private Button btn_switch_to_exp;
    //É um container especificamente para lidar com estruturas regulares com espaços iguais como azulejos numa parede.
    @FXML
    private TilePane tilePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupKeypad();
        btn_switch_to_exp.setOnAction(e -> {
           Navigation.loadNumKeypadExp();
        });
    }

    private void setupKeypad() {
        tilePane.setPadding(new Insets(20));
        tilePane.setPrefColumns(4);
        tilePane.setHgap(10);
        tilePane.setVgap(10);

        List<String> labels = KeyboardLayout.getNumericKeypadLabelsSmall();

        for(String label: labels){
            NumericCard card = new NumericCard(label);

            if (label.matches("[/*\\-+=C]")) {
                card.getStyleClass().add("operator-key");
            }else{
                card.getStyleClass().add("numeric-key");
            }

            tilePane.getChildren().add(card);
        }
    }
}
