package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {


    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text signUpText;

    @FXML
    private TextField usernameField;

    @FXML
    void onSignIn(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Eğer kullanıcı adı veya şifre boşsa, kullanıcıya hata mesajı göster
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText("Gerekli alanlar boş bırakılamaz.");
            alert.setContentText("Lütfen kullanıcı adı ve şifre alanlarını doldurun.");
            alert.showAndWait();
            return;
        }

        // Kullanıcı adı ve şifreyi veritabanında sorgulama
        KullaniciService service = new KullaniciService();
        Kullanici kullanici = service.getUserByUsernameAndPassword(username, password);

        // Eğer kullanıcı veritabanında varsa, giriş başarılı
        if (kullanici != null) {
            KullaniciOturumu.setCurrentUser(kullanici);  // Kullanıcıyı oturuma kaydet

            // Başarılı girişte kullanıcıya bilgi ver
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Başarılı Giriş");
            alert.setHeaderText(null);
            alert.setContentText("Giriş başarılı!");
            alert.showAndWait();

            changeScene("main-view", usernameField);

            // Yeni sahneyi yüklemek veya başka işlemleri burada gerçekleştirebilirsiniz.
        } else {
            // Kullanıcı adı ya da şifre hatalı ise, kullanıcıya hata mesajı göster
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hatalı Giriş");
            alert.setHeaderText(null);
            alert.setContentText("Kullanıcı adı veya şifre hatalı.");
            alert.showAndWait();
        }
    }

    void changeScene(String fxml, Node node) throws IOException
    {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void wantsToSignUp(MouseEvent event) throws IOException {
        changeScene("sign-view", signUpText);
    }
}
