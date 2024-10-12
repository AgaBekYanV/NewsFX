package com.workh.newsfx;

import java.sql.*;

public class DB {

    private final String HOST = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB_NAME = "db_test";
    private final String LOGIN = "tester";
    private final String PASS = "tester";

    private Connection conn= null;



    private Connection getDbConnection(){
        String connStr = "jdbc:mysql://"+ HOST + ":" + PORT + "/" + DB_NAME;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connStr,LOGIN,PASS);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public boolean userIsExist(String login){
        String sql = "SELECT `id` FROM `users` WHERE `login` = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,login);
            ResultSet rs = prSt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void insertNewUser(String login, String email, String pass){
        String sql = "INSERT INTO `users` (`login`, `email`, `pass`) VALUES (?,?,?)";

        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,login);
            prSt.setString(2,email);
            prSt.setString(3,pass);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean authUser(String login, String pass){
        String sql = "SELECT `id` FROM `users` WHERE `login` = ? AND `pass` = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,login);
            prSt.setString(2,pass);
            ResultSet rs = prSt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getArticles(){
        String sql = "SELECT `id`,`title`,`intro`,`text` FROM `articles`";
        Statement st = null;
        try {
            st = getDbConnection().createStatement();
            return st.executeQuery(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getOneArticle(int id){
        String sql = "SELECT `title`,`intro`,`text` FROM `articles` WHERE `id` = ?";
        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setInt(1,id);
            return prSt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addArticle(String title, String intro, String text) {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`) VALUES (?,?,?)";

        PreparedStatement prSt = null;
        try {
            prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,title);
            prSt.setString(2,intro);
            prSt.setString(3,text);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
