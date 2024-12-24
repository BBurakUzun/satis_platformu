module org.example.veritabaniprojesi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.veritabaniprojesi to javafx.fxml;
    exports org.example.veritabaniprojesi;
}