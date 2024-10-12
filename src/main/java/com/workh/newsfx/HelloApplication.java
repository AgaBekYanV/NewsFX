package com.workh.newsfx;

import com.workh.newsfx.Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {

        DB db = new DB();

        File file = new File("user.settings");
        if(file.exists()) {

            FileInputStream fis = new FileInputStream("user.settings");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User user = (User) ois.readObject();
            ois.close();
            if (db.userIsExist(user.getLogin()))
                setScene("articles-panel.fxml", "Новости с сайта", stage);
             else
                setScene("main-stage.fxml", "Окно Авторизации", stage);

        } else
            setScene("main-stage.fxml", "Окно Авторизации", stage);



        //setScene("main-stage.fxml", "Окно Авторизации", stage);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setScene(String sceneName, String sceneTitle, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle(sceneTitle);
        stage.setScene(scene);
        stage.show();
    }
}