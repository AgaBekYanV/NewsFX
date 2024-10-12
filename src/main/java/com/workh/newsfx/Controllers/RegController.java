package com.workh.newsfx.Controllers;

import com.workh.newsfx.DB;
import com.workh.newsfx.HelloApplication;
import com.workh.newsfx.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.workh.newsfx.HelloApplication.setScene;

public class RegController {

    @FXML
    private Label alertLabel;

    @FXML
    private Button authBtn;

    @FXML
    private TextField authLogin;

    @FXML
    private PasswordField authPass;

    @FXML
    private Button regBtn;

    @FXML
    private CheckBox regCheckbox;

    @FXML
    private TextField regEmail;

    @FXML
    private TextField regLogin;

    @FXML
    private PasswordField regPass;

    private DB db = new DB();

    @FXML
    void registrationUser(MouseEvent event) {
        String loginUser = String.valueOf(regLogin.getCharacters());
        String emailUser = String.valueOf(regEmail.getCharacters());
        String passUser = String.valueOf(regPass.getCharacters());

        if(loginUser.isEmpty()){
            setBorderStyleError(regLogin);
            alertLabel.setText("Поле логин не заполнено");
        }
        else if(loginUser.length() < 5){
            setBorderStyleError(regLogin);
            alertLabel.setText("Логин должен быть больше 5 символов");
        } else if (emailUser.isEmpty()){
            setBorderStyleError(regEmail);
            alertLabel.setText("Поле email не заполнено");
        } else if (emailUser.length() < 5 || !emailUser.contains("@") || !emailUser.contains(".")){
            setBorderStyleError(regEmail);
            alertLabel.setText("Email неккоректен или меньше 5 символов");
        } else if (passUser.isEmpty()){
            setBorderStyleError(regPass);
            alertLabel.setText("Поле pass не заполнено");
        } else if (passUser.length() < 6){
            setBorderStyleError(regPass);
            alertLabel.setText("Pass должен быть больше 6 символов");
        } else if(!regCheckbox.isSelected()){
            alertLabel.setText("Требуется принять пользовательское соглашение");
        } else if (db.userIsExist(loginUser)) {
            setBorderStyleError(regLogin);
            alertLabel.setText("Пользовавтель с данным логином уже существует");
        } else {
            db.insertNewUser(loginUser,emailUser,mb5String(passUser));
            alertLabel.setText("Пользовавтель успешно зарегистрирован");
            setBorderStyleDefault(regLogin);
            setBorderStyleDefault(regEmail);
            setBorderStyleDefault(regPass);
            regLogin.setText("");
            regEmail.setText("");
            regPass.setText("");
        }
    }

    @FXML
    void authenticationUser(MouseEvent event) {
        String loginUser = String.valueOf(authLogin.getCharacters());
        String passUser = String.valueOf(authPass.getCharacters());

        if(loginUser.isEmpty()){
            setBorderStyleError(authLogin);
            alertLabel.setText("Поле логин не заполнено");
        }
        else if(loginUser.length() < 5){
            setBorderStyleError(authLogin);
            alertLabel.setText("Не верный логин");
        } else if(!db.userIsExist(loginUser)){
            setBorderStyleError(authLogin);
            alertLabel.setText("Пользователя с таким логином не существует");
        }else if (passUser.isEmpty()){
            setBorderStyleError(authPass);
            alertLabel.setText("Поле pass не заполнено");
        } else if (passUser.length() < 6){
            setBorderStyleError(authPass);
            alertLabel.setText("Не верный пароль");
        }
        else {
            if(db.authUser(loginUser,mb5String(passUser))) {
                alertLabel.setText("Пользовавтель успешно авторизован");
                setBorderStyleDefault(authLogin);
                setBorderStyleDefault(authPass);
                authLogin.setText("");
                authPass.setText("");

                try {
                    FileOutputStream fos = new FileOutputStream("user.settings");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(new User(loginUser));
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    setScene("articles-panel.fxml","Новости с сайта", stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                alertLabel.setText("Авторизация не выполнена");
            }
        }


    }

    @FXML
    void initialize() {
    }


    public static String mb5String(String pass){
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(pass.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        BigInteger bigInteger = new BigInteger(1,digest);
        String m5dHex = bigInteger.toString(16);

        while (m5dHex.length() < 32)
            m5dHex = "0" + m5dHex;

        return m5dHex;

    }

    public void setBorderStyleError(TextField loginOrEmail){
        loginOrEmail.setStyle("-fx-border-color:#cc0a21");
    }

    private void setBorderStyleError(PasswordField pass){
        pass.setStyle("-fx-border-color:#cc0a21");
    }

    private void setBorderStyleDefault(TextField loginOrEmail){
        loginOrEmail.setStyle("-fx-border-color:#2C2D31");
    }

    private void setBorderStyleDefault(PasswordField pass){
        pass.setStyle("-fx-border-color:#2C2D31");
    }
}
