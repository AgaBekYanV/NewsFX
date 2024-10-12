module com.workh.newsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.workh.newsfx to javafx.fxml;
    exports com.workh.newsfx;
    exports com.workh.newsfx.Controllers;
    opens com.workh.newsfx.Controllers to javafx.fxml;
    exports com.workh.newsfx.Models;
    opens com.workh.newsfx.Models to javafx.fxml;
}