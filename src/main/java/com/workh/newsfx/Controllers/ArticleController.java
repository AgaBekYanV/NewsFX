package com.workh.newsfx.Controllers;

import com.workh.newsfx.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.workh.newsfx.Controllers.ArticlesPanelController.idArticle;
import static com.workh.newsfx.HelloApplication.setScene;

public class ArticleController {

    @FXML
    private Label artId;

    @FXML
    private Label introArticle;

    @FXML
    private Label textArticle;

    @FXML
    private Label titleArticle;


    @FXML
    private Button backBtn;

    @FXML
    void backToArticlesPanel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            setScene("articles-panel.fxml","Новости с сайта", stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    DB db = new DB();

    @FXML
    void initialize() throws SQLException {

        ResultSet resultSet = db.getOneArticle(idArticle);
        while (resultSet.next()) {
            //Label title = (Label) node.lookup("#titleArticle");
            titleArticle.setText(resultSet.getString("title"));

            //Label intro = (Label) node.lookup("#introArticle");
            introArticle.setText(resultSet.getString("intro"));

            //Label text = (Label) node.lookup("#textArticle");
            textArticle.setText(resultSet.getString("text"));

            //Label id = (Label) node.lookup("#artId");
            artId.setText(String.valueOf(idArticle));
        }
    }

}