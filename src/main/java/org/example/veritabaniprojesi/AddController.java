package org.example.veritabaniprojesi;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddController extends AbstractController{

    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    private Integer urun_id = null;

    @FXML
    private TextField bookName;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<String> deliveryType;

    @FXML
    private TextField desc;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField konum;

    @FXML
    private TextField page;

    @FXML
    private TextField price;

    @FXML
    private Label submitButton;

    @FXML
    private Button uploadButton;

    @FXML
    private TextField writer;

    private String gorsel_path;


    @FXML
    private void initialize() {
        gorsel_path = null;
        categoryBox.setItems(FXCollections.observableArrayList(
                "Roman",
                "Bilim Kurgu",
                "Fantastik",
                "Klasik",
                "Tarih",
                "Kişisel Gelişim",
                "Felsefe",
                "Biyografi",
                "Psikoloji",
                "Sosyal Bilimler"));
        deliveryType.setItems(FXCollections.observableArrayList("Elden", "Kargo"));
        uploadButton.setOnAction(event -> openFileChooser());
    }

    @FXML
    void onSubmit(MouseEvent event) throws IOException {
        saveProductToDatabase(bookName.getText(),
                KullaniciOturumu.getCurrentUser().getId(),
                Integer.valueOf(price.getText()),
                writer.getText(),
                desc.getText(),
                categoryBox.getValue(),
                konum.getText(),
                Integer.valueOf(page.getText()),
                deliveryType.getValue());
        saveImagePathToDatabase(gorsel_path);  // Veritabanına kaydet (seçilen dosyanın path'i)
        changeScene("main-view", submitButton);
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Kopyalanacak hedef klasör
            String targetDirectory = "C:\\Users\\Buğra\\Downloads\\dev-spring-boot\\01-spring-boot-overview\\VeritabaniProjesi\\src\\main\\resources\\org\\example\\veritabaniprojesi\\product_pictures\\";
            File targetDir = new File(targetDirectory);

            if (!targetDir.exists()) {
                targetDir.mkdir();  // Eğer hedef klasör yoksa, oluştur
            }

            // Kopyalanacak dosyanın adı
            String targetFilePath = targetDirectory + selectedFile.getName();
            File targetFile = new File(targetFilePath);

            try {
                // Dosyayı hedef klasöre kopyala
                Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Kopyalanan dosyanın path'ini al
                String copiedFilePath = targetFile.getAbsolutePath();

                // Kopyalanan dosyanın path'ini database'e kaydetmek için
                gorsel_path = copiedFilePath;

                // Resmi ImageView üzerinde göster
                Image image = new Image(targetFile.toURI().toString());
                imageView.setImage(image);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Dosya kopyalanırken hata oluştu.");
            }
        }
    }

    private void saveImagePathToDatabase(String imagePath) {
        String insertSQL = "INSERT INTO UrunGorselleri (urun_id, gorsel) VALUES (?, ?)";
        UrunService urunService = new UrunService();
        int urun_id = urunService.getProductIdByName(bookName.getText());


        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setInt(1, urun_id);  // Dosya yolunu veritabanına ekle
            pstmt.setString(2, imagePath);  // Dosya yolunu veritabanına ekle
            pstmt.executeUpdate();  // Veriyi veritabanına ekle

            System.out.println("Görsel yolu başarıyla kaydedildi.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanına bağlanırken hata oluştu.");
        }
    }

    public void saveProductToDatabase(String ad, Integer satici_id, int fiyat, String yazar, String aciklama, String kategori, String konum, int sayfa, String teslim_turu) {
        // SQL sorgusu
        String insertSQL = "INSERT INTO urun (ad, satici_id, fiyat, yazar, aciklama, kategori, konum, sayfa, teslim_turu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Parametreleri hazırlayıp sorguya ekliyoruz
            pstmt.setString(1, ad);  // Ürün adı
            pstmt.setInt(2, satici_id);   // Açıklama
            pstmt.setInt(3, fiyat);         // Fiyat
            pstmt.setString(4, yazar);     // Görsel yolu
            pstmt.setString(5, aciklama);     // Görsel yolu
            pstmt.setString(6, kategori);     // Görsel yolu
            pstmt.setString(7, konum);     // Görsel yolu
            pstmt.setInt(8, sayfa);     // Görsel yolu
            pstmt.setString(9, teslim_turu);     // Görsel yolu

            // Sorguyu çalıştırıp veriyi veritabanına ekliyoruz
            pstmt.executeUpdate();
            System.out.println("Ürün başarıyla veritabanına kaydedildi.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanına kaydederken hata oluştu.");
        }
    }
}
