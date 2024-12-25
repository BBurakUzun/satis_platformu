package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController extends AbstractController {
    @FXML
    private Label satisYapButton;

    @FXML
    private Button backButton;

    @FXML
    private Label myOrdersButton;

    @FXML
    private Text helloText;
    public void initialize() {
        Kullanici user = KullaniciOturumu.getCurrentUser();

        helloText.setText("Merhaba, " + user.getAd());

    }

    @FXML
    void satisYapma(MouseEvent event) throws IOException {
        changeScene("add-view", satisYapButton);
    }


    @FXML
    void onBack(ActionEvent event) throws IOException {
        changeScene("main-view", satisYapButton);
    }

    @FXML
    void onMyOrders(MouseEvent event) throws IOException {
        changeScene("myorders-view", helloText);
    }




}
