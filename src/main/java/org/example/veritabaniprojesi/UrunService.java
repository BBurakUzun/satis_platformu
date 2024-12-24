package org.example.veritabaniprojesi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrunService {

    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    // Ürün adı ile id'yi almak için fonksiyon
    public int getProductIdByName(String bookName) {
        String query = "SELECT id FROM Urun WHERE ad = ?";
        int urunId = -1;  // Başlangıçta -1, ürün bulunmazsa geri dönülecek değer

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bookName);  // Kullanıcıdan alınan ürün adını sorguya ekle
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                urunId = rs.getInt("id");  // Ürün id'sini al
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return urunId;
    }

    public List<Urun> getAllProducts() {
        List<Urun> products = new ArrayList<>();
        String query = "SELECT * FROM Urun";  // Ürün tablosundaki tüm satırları al

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Ürün verilerini al
            while (rs.next()) {
                int p_id = rs.getInt("id");
                String ad = rs.getString("ad");
                int saticiId = rs.getInt("satici_id");
                int fiyat = rs.getInt("fiyat");
                String aciklama = rs.getString("aciklama");
                String konum = rs.getString("konum");
                String teslimTuru = rs.getString("teslim_turu");
                String kategori = rs.getString("kategori");
                int sayfa = rs.getInt("sayfa");
                String yazar = rs.getString("yazar");

                // Urun nesnesini oluştur
                Urun urun = new Urun(
                        p_id,
                        ad,
                        saticiId,
                        fiyat,
                        aciklama,
                        konum,
                        teslimTuru,
                        kategori,
                        sayfa,
                        yazar,
                        "temp"  // Görselin path'ini geçici olarak ayarlıyoruz
                );

                // Görseli almak için iç sorgu (urun_id ile)
                String imageQuery = "SELECT gorsel FROM UrunGorselleri WHERE urun_id = ?";
                try (PreparedStatement stmt2 = conn.prepareStatement(imageQuery)) {
                    stmt2.setInt(1, p_id);  // Ürün id'sini sorguya ekle
                    try (ResultSet rs2 = stmt2.executeQuery()) {
                        if (rs2.next()) {
                            String path = rs2.getString("gorsel");  // Görsel path'ini al
                            urun.setImagePath(path);  // Görsel path'ini Urun nesnesine set et
                        }
                    }
                }

                // Urun nesnesini listeye ekle
                products.add(urun);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
