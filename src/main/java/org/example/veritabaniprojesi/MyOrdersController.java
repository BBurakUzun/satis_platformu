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
        String siparisSQL = "SELECT id, tarih FROM Siparis WHERE musteri_id = ?";
        String siparisDetaySQL = "SELECT urun_id FROM SiparisDetaylari WHERE siparis_id = ?";
        String urunSQL = "SELECT ad, fiyat FROM Urun WHERE id = ?";
        String urunGorselSQL = "SELECT gorsel FROM UrunGorselleri WHERE urun_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement siparisStmt = conn.prepareStatement(siparisSQL)) {

            siparisStmt.setInt(1, KullaniciOturumu.getCurrentUser().getId());
            ResultSet siparisRs = siparisStmt.executeQuery();

            while (siparisRs.next()) {
                int siparisId = siparisRs.getInt("id");
                Date siparisTarihi = siparisRs.getDate("tarih");

                PreparedStatement siparisDetayStmt = conn.prepareStatement(siparisDetaySQL);
                siparisDetayStmt.setInt(1, siparisId);
                ResultSet siparisDetayRs = siparisDetayStmt.executeQuery();

                while (siparisDetayRs.next()) {
                    int urunId = siparisDetayRs.getInt("urun_id");

                    PreparedStatement urunStmt = conn.prepareStatement(urunSQL);
                    urunStmt.setInt(1, urunId);
                    ResultSet urunRs = urunStmt.executeQuery();

                    if (urunRs.next()) {
                        String urunAdi = urunRs.getString("ad");
                        double fiyat = urunRs.getDouble("fiyat");

                        // Ürün görselini çek
                        PreparedStatement urunGorselStmt = conn.prepareStatement(urunGorselSQL);
                        urunGorselStmt.setInt(1, urunId);
                        ResultSet urunGorselRs = urunGorselStmt.executeQuery();

                        String gorselYolu = null; // Varsayılan görsel yolu
                        if (urunGorselRs.next()) {
                            gorselYolu = urunGorselRs.getString("gorsel");
                        }

                        // AnchorPane oluştur ve VBox'a ekle
                        AnchorPane siparisPane = createSiparisPane(urunAdi, fiyat, siparisTarihi, gorselYolu);
                        siparisVBox.getChildren().add(siparisPane);

                        urunGorselRs.close();
                        urunGorselStmt.close();
                    }
                    urunRs.close();
                    urunStmt.close();
                }
                siparisDetayRs.close();
                siparisDetayStmt.close();
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
