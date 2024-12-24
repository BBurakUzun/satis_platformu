package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.List;

public class Controller {

    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    @FXML
    private Text mainText;

    @FXML
    private Label welcomeText;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text signUpText;

    @FXML
    private TextField usernameField;


    @FXML
    private TextArea adressField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordSignField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameSignField;

    @FXML
    private TextField phoneNoField;

    @FXML
    private TextField emailField;

    @FXML
    private Text helloText;

    @FXML
    private GridPane productGrid;

    @FXML
    public void initialize() {
        showProducts();
    }

    public void showProducts() {
        List<Urun> products = new UrunService().getAllProducts();

        // GridPane'ye ürünleri ekleyelim
        for (int i = 0; i < products.size(); i++) {
            Urun product = products.get(i);

            // AnchorPane oluştur
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(173.0);
            anchorPane.setPrefWidth(203.0);

            // Görseli yükle
            Image productImage = new Image("file:" + product.getImagePath());  // 'file:' ile path'i açıyoruz
            ImageView productImageView = new ImageView(productImage);
            productImageView.setFitHeight(110.0);
            productImageView.setFitWidth(91.0);
            productImageView.setLayoutX(61.0);  // Görselin X koordinatı
            productImageView.setLayoutY(14.0);  // Görselin Y koordinatı
            productImageView.setPreserveRatio(true);  // Görselin oranını koru
            productImageView.setPickOnBounds(true);  // Tıklanabilir yap

            // Ürün adı
            Text productNameText = new Text(product.getAd());
            productNameText.setLayoutX(17.0);
            productNameText.setLayoutY(147.0);  // Y koordinatı
            productNameText.setStrokeType(StrokeType.OUTSIDE);
            productNameText.setStrokeWidth(0.0);
            productNameText.setTextAlignment(TextAlignment.CENTER);
            productNameText.setWrappingWidth(91.4700927734375);  // Yazının sarılacağı genişlik

            // Ürün fiyatı
            Text productPriceText = new Text(String.valueOf(product.getFiyat()) + " TL");
            productPriceText.setLayoutX(108.0);
            productPriceText.setLayoutY(147.0);  // Y koordinatı
            productPriceText.setStrokeType(StrokeType.OUTSIDE);
            productPriceText.setStrokeWidth(0.0);
            productPriceText.setTextAlignment(TextAlignment.CENTER);
            productPriceText.setWrappingWidth(91.4700927734375);  // Yazının sarılacağı genişlik

            // AnchorPane'e görsel ve metin ekle
            anchorPane.getChildren().addAll(productImageView, productNameText, productPriceText);

            // AnchorPane'i GridPane'e ekle
            productGrid.add(anchorPane, i % 3, i / 3);  // 3 sütunlu düzenleme, her 3 ürün bir satıra yerleşecek
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
    void onAccountClick(MouseEvent event) throws IOException {
        changeScene("account-view", mainText);
    }

}