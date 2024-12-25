package org.example.veritabaniprojesi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Controller extends AbstractController{

    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    @FXML
    private Button mainButton;

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

    public GridPane getProductGrid() {
        return productGrid;
    }

    @FXML
    private GridPane productGrid;

    @FXML
    private Button categoryButton;

    @FXML
    private Pane categoryPane;

    @FXML
    private VBox categoryVBox;

    @FXML
    private Button sortButton;

    @FXML
    private Pane sortPane;

    @FXML
    private VBox sortVBox;

    private String selectedCategory;

    @FXML
    public void onMainButton(ActionEvent event) {
        productGrid.getChildren().clear();
        showProducts(null);
    }

    @FXML
    public void initialize() {
        selectedCategory = null;
        showProducts(null);

        categoryButton.setOnMouseEntered(event -> categoryPane.setVisible(true));
        categoryButton.setOnMouseExited(event -> {
            if (!categoryPane.isHover()) {
                categoryPane.setVisible(false);
            }
        });

        categoryPane.setOnMouseEntered(event -> categoryPane.setVisible(true));
        categoryPane.setOnMouseExited(event -> categoryPane.setVisible(false));
        String[] categories = {
                "Roman", "Bilim Kurgu", "Fantastik", "Klasik", "Tarih",
                "Kişisel Gelişim", "Felsefe", "Biyografi", "Psikoloji", "Sosyal Bilimler"
        };

        for (String category : categories) {
            Label label = new Label(category);
            label.setStyle("-fx-font-size: 18px; -fx-padding: 5px;");
            label.setOnMouseClicked(event -> handleCategoryClick(category));
            categoryVBox.getChildren().add(label);
        }
        // Add category click events if necessary
        for (int i = 0; i < categoryVBox.getChildren().size(); i++) {
            Label label = (Label) categoryVBox.getChildren().get(i);
            label.setOnMouseClicked(event -> handleCategoryClick(label.getText()));
        }

        sortButton.setOnMouseEntered(event -> sortPane.setVisible(true));
        sortButton.setOnMouseExited(event -> {
            if (!sortPane.isHover()) {
                sortPane.setVisible(false);
            }
        });


        sortPane.setOnMouseEntered(event -> sortPane.setVisible(true));
        sortPane.setOnMouseExited(event -> sortPane.setVisible(false));

    }

    private void handleCategoryClick(String category) {
        // Handle the category click
        System.out.println("Clicked on category: " + category);
        productGrid.getChildren().clear();
        selectedCategory = category;
        List<Urun> urunler = UrunService.getProductsByCategory(category, "ASC");
        showProducts(urunler);
    }

    public void showProducts(List<Urun> urunler) {
        List<Urun> products = null;
        if (urunler == null) {
            products = UrunService.getAllProducts();
        }
        else {
            products = urunler;
        }

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
            productImageView.setFitWidth(176.0);
            productImageView.setLayoutX(17.0);  // Görselin X koordinatı
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
            Text productPriceText = new Text(String.valueOf(product.getFiyat()));
            productPriceText.setLayoutX(108.0);
            productPriceText.setLayoutY(147.0);  // Y koordinatı
            productPriceText.setStrokeType(StrokeType.OUTSIDE);
            productPriceText.setStrokeWidth(0.0);
            productPriceText.setTextAlignment(TextAlignment.CENTER);
            productPriceText.setWrappingWidth(91.4700927734375);  // Yazının sarılacağı genişlik

            // AnchorPane'e görsel ve metin ekle
            anchorPane.getChildren().addAll(productImageView, productNameText, productPriceText);

            // Tıklama olayı ekle
            anchorPane.setOnMouseClicked(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("product-view.fxml"));
                    Parent productViewParent = loader.load();

                    ProductController controller = loader.getController();
                    controller.setProductDetails(product);

                    Scene productViewScene = new Scene(productViewParent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(productViewScene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // AnchorPane'i GridPane'e ekle
            productGrid.add(anchorPane, i % 3, i / 3);  // 3 sütunlu düzenleme, her 3 ürün bir satıra yerleşecek
        }
    }





    @FXML
    void onAccountClick(MouseEvent event) throws IOException {
        changeScene("account-view", mainButton);
    }

    @FXML
    void sortByAsc(ActionEvent event) {
        System.out.println("artan");
        List<Urun> urunler = UrunService.getProductsByCategory(selectedCategory, "ASC");
//        bubbleSortProductsByPrice(urunler);
        productGrid.getChildren().clear();
        showProducts(urunler);
    }

    @FXML
    void sortByDesc(ActionEvent event) {
        System.out.println("azalan");
        List<Urun> urunler = UrunService.getProductsByCategory(selectedCategory, "DESC");
//        bubbleSortProductsByPrice(urunler);
//        Collections.reverse(urunler);
        productGrid.getChildren().clear();
        showProducts(urunler);
    }

//    public static void bubbleSortProductsByPrice(List<Urun> urunler) {
//        int n = urunler.size();
//        boolean swapped;
//        for (int i = 0; i < n - 1; i++) {
//            swapped = false;
//            for (int j = 0; j < n - i - 1; j++) {
//                if (urunler.get(j).getFiyat() > urunler.get(j + 1).getFiyat()) {
//                    // Swap urunler[j] and urunler[j + 1]
//                    Urun temp = urunler.get(j);
//                    urunler.set(j, urunler.get(j + 1));
//                    urunler.set(j + 1, temp);
//                    swapped = true;
//                }
//            }
//            // If no two elements were swapped by inner loop, then break
//            if (!swapped) break;
//        }
//    }

}