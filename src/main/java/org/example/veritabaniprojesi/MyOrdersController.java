package org.example.veritabaniprojesi;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.*;

public class MyOrdersController extends AbstractController {


    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    @FXML
    private VBox siparisVBox;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        loadSiparisler();
    }

    void loadSiparisler() {
        String query = "SELECT Siparis.id, Siparis.tarih, Urun.ad, Urun.fiyat, UrunGorselleri.gorsel " +
                "FROM Siparis " +
                "JOIN SiparisDetaylari ON Siparis.id = SiparisDetaylari.siparis_id " +
                "JOIN Urun ON SiparisDetaylari.urun_id = Urun.id " +
                "LEFT JOIN UrunGorselleri ON Urun.id = UrunGorselleri.urun_id " +
                "WHERE Siparis.musteri_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, KullaniciOturumu.getCurrentUser().getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int siparisId = rs.getInt("id");
                Date siparisTarihi = rs.getDate("tarih");
                String urunAdi = rs.getString("ad");
                double fiyat = rs.getDouble("fiyat");
                String gorselYolu = rs.getString("gorsel");

                // AnchorPane oluştur ve VBox'a ekle
                AnchorPane siparisPane = createSiparisPane(urunAdi, fiyat, siparisTarihi, gorselYolu);
                siparisVBox.getChildren().add(siparisPane);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private AnchorPane createSiparisPane(String urunAdi, double fiyat, Date siparisTarihi, String gorselYolu) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(131.0);
        pane.setPrefWidth(533.0);

        ImageView imageView = new ImageView();
        imageView.setFitHeight(106.0);
        imageView.setFitWidth(166.0);
        imageView.setLayoutX(14.0);
        imageView.setLayoutY(11.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        // Ürün resmini ayarla
        if (gorselYolu != null && !gorselYolu.isEmpty()) {
            imageView.setImage(new Image(gorselYolu));
        } else {
            imageView.setImage(new Image("@/path/to/default/image.png")); // Varsayılan görsel
        }

        Label adLabel = new Label(urunAdi);
        adLabel.setLayoutX(216.0);
        adLabel.setLayoutY(23.0);
        adLabel.setPrefHeight(92.0);
        adLabel.setPrefWidth(166.0);
        adLabel.setFont(new Font("Arial Narrow", 24.0));
        adLabel.setWrapText(true);

        Label fiyatLabel = new Label(String.valueOf(fiyat) + " TL");
        fiyatLabel.setLayoutX(405.0);
        fiyatLabel.setLayoutY(83.0);
        fiyatLabel.setPrefHeight(33.0);
        fiyatLabel.setPrefWidth(123.0);
        fiyatLabel.setFont(new Font("Arial Narrow", 22.0));

        Label tarihLabel = new Label(siparisTarihi.toString());
        tarihLabel.setLayoutX(405.0);
        tarihLabel.setLayoutY(23.0);
        tarihLabel.setPrefHeight(33.0);
        tarihLabel.setPrefWidth(123.0);
        tarihLabel.setFont(new Font("Arial Narrow", 21.0));

        pane.getChildren().addAll(imageView, adLabel, fiyatLabel, tarihLabel);

        return pane;
    }

    @FXML
    void onBack(ActionEvent event) throws IOException {
        changeScene("account-view", backButton);
    }

}
