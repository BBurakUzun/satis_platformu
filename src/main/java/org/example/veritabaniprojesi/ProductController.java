package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ProductController extends AbstractController {


    @FXML
    private Button backButton;

    @FXML
    private Button buyButton;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Text mainText;

    @FXML
    private Label pageLabel;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productShowName;

    @FXML
    private Label showPrice;

    @FXML
    private VBox commentVBox;

    @FXML
    private Button commentButton;

    @FXML
    private TextField commentField;

    @FXML
    private Label writerLabel;


    @FXML
    public void onBack(ActionEvent event) throws IOException {
        changeScene("main-view", backButton);
    }

    public void setProductDetails(Urun urun) {
        productShowName.setText(urun.getAd());
        showPrice.setText(String.valueOf(urun.getFiyat()) + " TL");
        writerLabel.setText(urun.getYazar());
        pageLabel.setText(String.valueOf(urun.getSayfa()));
        categoryLabel.setText(urun.getKategori());
        descriptionLabel.setText(urun.getAciklama());

        Image pImage = new Image("file:" + urun.getImagePath());
        productImage.setImage(pImage);

        loadComments();
    }

    void loadComments() {

        String query = "SELECT Yorumlar.*, Kullanici.kullanici_adi " +
                "FROM Yorumlar " +
                "JOIN Kullanici ON Yorumlar.gonderen_id = Kullanici.id " +
                "WHERE urun_id = ?";;

        String desc = null;
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, UrunService.getProductIdByName(productShowName.getText()));  // Kullanıcıdan alınan ürün adını sorguya ekle
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                desc = rs.getString("yorum_metni");
                String username = rs.getString("kullanici_adi");



                AnchorPane commentPane = new AnchorPane();
                commentPane.setLayoutX(55.0);
                commentPane.setLayoutY(554.0);
                commentPane.setPrefHeight(125.0);
                commentPane.setPrefWidth(982.0);

                // Kullanıcı adı için Label oluşturuluyor
                Label usernameLabel = new Label();
                usernameLabel.setLayoutX(125.0);
                usernameLabel.setLayoutY(17.0);
                usernameLabel.setPrefHeight(11.0);
                usernameLabel.setPrefWidth(127.0);
                usernameLabel.setText(username);
                usernameLabel.setTextAlignment(TextAlignment.CENTER);
                usernameLabel.setFont(Font.font("Arial", 12.0));

                // Resim için ImageView oluşturuluyor
                ImageView userImage = new ImageView();
                userImage.setFitHeight(91.0);
                userImage.setFitWidth(91.0);
                userImage.setLayoutX(14.0);
                userImage.setLayoutY(25.0);
                userImage.setPickOnBounds(true);
                userImage.setPreserveRatio(true);
                userImage.setImage(new Image(getClass().getResource("/org/example/veritabaniprojesi/pictures/PngItem_93862.png").toExternalForm()));

                // Yorum metni için TextField oluşturuluyor
                TextField commentField = new TextField();
                commentField.setEditable(false);
                commentField.setLayoutX(125.0);
                commentField.setLayoutY(38.0);
                commentField.setPrefHeight(71.0);
                commentField.setPrefWidth(730.0);
                commentField.setStyle("-fx-background-radius: 50px;");
                commentField.setText(desc);

                // Öğeleri AnchorPane'e ekliyoruz
                commentPane.getChildren().addAll(usernameLabel, userImage, commentField);

                // Yorumları içeren bir container (örneğin bir VBox) varsa, yeni yorum ekliyoruz
                commentVBox.getChildren().add(commentPane);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void onBuying(ActionEvent event) throws IOException {
        // Sahne değiştirme işlemi
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        // Yeni Controller'ı al
        Controller controller = loader.getController();

        // Sipariş ve Sipariş Detayı için SQL sorguları
        String insertSiparisSQL = "INSERT INTO Siparis (musteri_id, tarih) VALUES (?, ?)";
        String insertSiparisDetaylariSQL = "INSERT INTO SiparisDetaylari (siparis_id, urun_id) VALUES (?, ?)";
        String updateSQL = "UPDATE Urun SET satilma = 1 WHERE id = ?";

        LocalDate currentDate = LocalDate.now();  // Anlık tarihi al

        try (Connection conn = DriverManager.getConnection(url, user, pass);  // Veritabanı bağlantısını sağla
             PreparedStatement stmtSiparis = conn.prepareStatement(insertSiparisSQL, Statement.RETURN_GENERATED_KEYS);  // Sipariş için PreparedStatement
             PreparedStatement stmtSiparisDetaylari = conn.prepareStatement(insertSiparisDetaylariSQL);
             PreparedStatement stmtUpdate = conn.prepareStatement(updateSQL)) {

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
                        int urunId = UrunService.getProductIdByName(productShowName.getText());  // Ürün ID'sini al

                        stmtSiparisDetaylari.setInt(1, siparisId);  // Yeni siparişin ID'sini ekle
                        stmtSiparisDetaylari.setInt(2, urunId);     // Ürün ID'sini ekle

                        int rowsAffectedDetaylari = stmtSiparisDetaylari.executeUpdate();
                        if (rowsAffectedDetaylari > 0) {
                            System.out.println("Sipariş Detayı başarıyla eklendi!");

                            // Urun tablosunda satilma sütununu güncelle
                            stmtUpdate.setInt(1, urunId);
                            int rowsAffectedUpdate = stmtUpdate.executeUpdate();
                            if (rowsAffectedUpdate > 0) {
                                System.out.println("Ürün başarıyla satıldı olarak işaretlendi!");

                                // GridPane'i temizle ve yeniden başlat
                                controller.getProductGrid().getChildren().clear();
                                controller.showProducts(null);
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void onComment(ActionEvent event) throws SQLException {
        String value = commentField.getText();

        String insertCommentSQL = "INSERT INTO Yorumlar (gonderen_id, yorum_metni, tarih, urun_id) VALUES (?, ?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement comment = conn.prepareStatement(insertCommentSQL, Statement.RETURN_GENERATED_KEYS)) {

            comment.setInt(1, KullaniciOturumu.getCurrentUser().getId());
            comment.setString(2, value);
            comment.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            comment.setInt(4, UrunService.getProductIdByName(productShowName.getText()));

            ResultSet rs = comment.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
