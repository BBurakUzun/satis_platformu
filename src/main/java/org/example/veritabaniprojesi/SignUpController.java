package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpController extends AbstractController{
    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    @FXML
    private TextArea adressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordSignField;

    @FXML
    private TextField phoneNoField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameSignField;

    @FXML
    void onSignUp(ActionEvent event) {
        String username = usernameSignField.getText();
        String password = passwordSignField.getText();
        String email = emailField.getText();
        String phone = phoneNoField.getText();

        String firstName = nameField.getText();
        String lastName = lastNameField.getText();
        String address = adressField.getText();



        String insertSQL = "INSERT INTO Kullanici (kullanici_adi, sifre, eposta, tel_no, isim, soyisim, adres) " +
                "VALUES ('" + username + "', '" + password + "', '" + email + "', '" + phone + "', '" + firstName + "', '" + lastName + "', '" + address + "')";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {

            // Sorguyu çalıştırma
            int rowsAffected = stmt.executeUpdate(insertSQL);
            if (rowsAffected > 0) {
                System.out.println("Kullanıcı başarıyla eklendi.");
                changeScene("login-view", usernameSignField);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanına bağlanırken hata oluştu.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
