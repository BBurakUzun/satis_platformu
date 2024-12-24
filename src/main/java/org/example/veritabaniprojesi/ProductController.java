package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ProductController {

    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    @FXML
    private Button buyButton;

    @FXML
    private Text mainText;

    @FXML
    private ImageView productImage;

    @FXML
    private Text productShowName;

    @FXML
    private Text showPrice;

    @FXML
    private Button backButton;

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        changeScene("main-view", backButton);
    }

    public void setProductDetails(Urun urun) {
        productShowName.setText(urun.getAd());
        showPrice.setText(String.valueOf(urun.getFiyat()) + " TL");
//        productDescriptionLabel.setText(urun.getAciklama());

        Image pImage = new Image("file:" + urun.getImagePath());
        productImage.setImage(pImage);
    }

    @FXML
    void onBuying(ActionEvent event) throws IOException {
        // Sahne değiştirme işlemi
        changeScene("main-view", backButton);

        // Sipariş ve Sipariş Detayı için SQL sorguları
        String insertSiparisSQL = "INSERT INTO Siparis (musteri_id, tarih) VALUES (?, ?)";
        String insertSiparisDetaylariSQL = "INSERT INTO SiparisDetaylari (siparis_id, urun_id) VALUES (?, ?)";

        LocalDate currentDate = LocalDate.now();  // Anlık tarihi al

        try (Connection conn = DriverManager.getConnection(url, user, pass);  // Veritabanı bağlantısını sağla
             PreparedStatement stmtSiparis = conn.prepareStatement(insertSiparisSQL, Statement.RETURN_GENERATED_KEYS);  // Sipariş için PreparedStatement
             PreparedStatement stmtSiparisDetaylari = conn.prepareStatement(insertSiparisDetaylariSQL)) {

            // Sipariş verisini ekle
            stmtSiparis.setInt(1, KullaniciOturumu.getCurrentUser().getId());
            stmtSiparis.setDate(2, java.sql.Date.valueOf(currentDate));

            int rowsAffectedSiparis = stmtSiparis.executeUpdate();

            // Eğer Sipariş başarıyla eklendiyse, yeni siparişin ID'sini al
            if (rowsAffectedSiparis > 0) {
                try (ResultSet generatedKeys = stmtSiparis.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int siparisId = generatedKeys.getInt(1);  // Yeni siparişin ID'sini al

                        // Sipariş Detayları verisini ekle
                        // Burada örnek olarak sadece 1 ürün ekleniyor, bunu dinamik hale getirebilirsiniz
                        int urunId = UrunService.getProductIdByName(productShowName.getText());  // Burada ürün ID'si örnek olarak 1 alınıyor, dinamikleştirebilirsiniz

                        stmtSiparisDetaylari.setInt(1, siparisId);  // Yeni siparişin ID'sini ekle
                        stmtSiparisDetaylari.setInt(2, urunId);     // Ürün ID'sini ekle

                        int rowsAffectedDetaylari = stmtSiparisDetaylari.executeUpdate();
                        if (rowsAffectedDetaylari > 0) {
                            System.out.println("Sipariş Detayı başarıyla eklendi!");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
}
