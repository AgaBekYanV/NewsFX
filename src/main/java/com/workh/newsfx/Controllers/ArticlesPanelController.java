package com.workh.newsfx.Controllers;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;


import com.workh.newsfx.DB;
import com.workh.newsfx.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.workh.newsfx.HelloApplication.setScene;

public class ArticlesPanelController {

    @FXML
    private Button extBtn, addArticleBtn;

    @FXML
    private VBox panelVbox;

    DB db = new DB();

    static int idArticle;

    @FXML
    void exitToAuth(MouseEvent event) {
        File file =new File("user.settings");
        file.delete();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            setScene("main-stage.fxml","Авторизация", stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() throws SQLException, IOException {
        ResultSet resultSet = db.getArticles();
        while (resultSet.next()){


            Node node = FXMLLoader.load((Objects.requireNonNull(HelloApplication.class.getResource("article.fxml"))));



            Label title = (Label) node.lookup("#titleArticle");
            title.setText(resultSet.getString("title"));

            Label intro = (Label) node.lookup("#introArticle");
            intro.setText(resultSet.getString("intro"));

            Label text = (Label) node.lookup("#textArticle");
            text.setText(resultSet.getString("text"));

            Label id = (Label) node.lookup("#artId");
            id.setText(resultSet.getString("id"));

            Button btnBack = (Button) node.lookup("#backBtn");
            btnBack.setVisible(false);

            node.setOnMouseEntered(event ->{
                node.setStyle("-fx-background-color: #578294;");
            });

            node.setOnMouseExited(event ->{
                node.setStyle("-fx-background-color: #579192;");
            });

            node.setOnMouseClicked(event ->{

                idArticle = Integer.parseInt(id.getText());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    setScene("article.fxml","Статья", stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

            panelVbox.getChildren().add(node);
        }



    }

    @FXML
    void addArticle(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setScene("add-article.fxml","Добавить статью", stage);
    }

}

