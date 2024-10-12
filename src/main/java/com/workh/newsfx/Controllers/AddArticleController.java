package com.workh.newsfx.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.workh.newsfx.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static com.workh.newsfx.HelloApplication.setScene;

public class AddArticleController {

    @FXML
    private TextField introAddField;

    @FXML
    private Button addArticleTextBtn;

    @FXML
    private TextArea textAddField;

    @FXML
    private TextField titleAddField;

    @FXML
    void initialize() {

    }

    @FXML
    void addArticleText(ActionEvent event) {
        String titleAdd = String.valueOf(titleAddField.getCharacters());
        String introAdd = String.valueOf(introAddField.getCharacters());
        String textAdd = String.valueOf(textAddField.getText());

        if(titleAdd.length() <= 5){
            titleAddField.setStyle("-fx-background-color: #579192");
        } else if (introAdd.length() <= 10){
            introAddField.setStyle("-fx-background-color: #579192");
        } else if (textAdd.length() <= 15){
            textAddField.setStyle("-fx-background-color: #579192");
        } else {
            DB db = new DB();
            db.addArticle(titleAdd,introAdd,textAdd);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                setScene("articles-panel.fxml","Новости с сайта", stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
