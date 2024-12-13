module org.example.veritabaniprojesi {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.veritabaniprojesi to javafx.fxml;
    exports org.example.veritabaniprojesi;
}