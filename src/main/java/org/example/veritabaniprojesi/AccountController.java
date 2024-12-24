package org.example.veritabaniprojesi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController {
    @FXML
    private Label satisYapButton;

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
    void onMyOrders(MouseEvent event) {

    }

    void changeScene(String fxml, Node node) throws IOException
    {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }




}
