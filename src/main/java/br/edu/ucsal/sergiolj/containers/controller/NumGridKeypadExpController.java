package br.edu.ucsal.sergiolj.containers.controller;

import br.edu.ucsal.sergiolj.containers.model.KeySet;
import br.edu.ucsal.sergiolj.containers.model.KeyboardLayout;
import br.edu.ucsal.sergiolj.containers.model.NumericCard;
import br.edu.ucsal.sergiolj.containers.navigation.Navigation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NumGridKeypadExpController implements Initializable {
    @FXML
    private Button btn_switch_to_reg;
    @FXML
    private GridPane gr_numExp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupGridKeypad();
        btn_switch_to_reg.setOnAction(e -> {
            Navigation.loadNumKeypadReg();
        });
    }

    private void setupGridKeypad() {
        gr_numExp.setHgap(8);
        gr_numExp.setVgap(8);
        gr_numExp.setPadding(new Insets(10));

        List<KeySet> keys = KeyboardLayout.getNumericKeypadLabelsExtended();
        for (KeySet key : keys) {
            NumericCard card = new NumericCard(key.label());

            if (key.label().matches("[/*\\-+=C.]")) {
                card.getStyleClass().add("operation-key");
            }else{
                card.getStyleClass().add("numeric-key");
            }

            card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            GridPane.setHgrow(card, Priority.ALWAYS);
            GridPane.setVgrow(card, Priority.ALWAYS);

            gr_numExp.add(card, key.col(),key.row(),key.colSpan(),key.rowSpan());
        }

    }

}
