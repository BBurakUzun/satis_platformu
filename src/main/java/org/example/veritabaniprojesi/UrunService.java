package org.example.veritabaniprojesi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UrunService {
    static String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    static String user = "yeni_kullanici";
    static String pass = "GüçlüBirŞifre";


    // Ürün adı ile id'yi almak için fonksiyon
    public static int getProductIdByName(String bookName) {

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

    public static List<Urun> getAllProducts() {
        List<Urun> products = new ArrayList<>();
        String query = "SELECT * FROM Urun WHERE satilma = 0 AND satici_id != ?";  // Ürün tablosundaki tüm satırları al

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)){
             stmt.setInt(1, KullaniciOturumu.getCurrentUser().getId());
             ResultSet rs = stmt.executeQuery();

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


    public static List<Urun> getProductsByCategory(String category, String type) {
        List<Urun> products = new ArrayList<>();

        // Dinamik olarak oluşturulan sorgu
        String query = "SELECT Urun.id, Urun.ad, Urun.satici_id, Urun.fiyat, Urun.aciklama, Urun.konum, Urun.teslim_turu, Urun.kategori, Urun.sayfa, Urun.yazar, UrunGorselleri.gorsel " +
                "FROM Urun " +
                "JOIN UrunGorselleri ON Urun.id = UrunGorselleri.urun_id " +
                "WHERE Urun.kategori = ? " +
                "ORDER BY Urun.fiyat " + (type.equalsIgnoreCase("asc") ? "ASC" : "DESC");

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);

            ResultSet rs = stmt.executeQuery();

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
                String gorsel = rs.getString("gorsel");

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
                        gorsel
                );

                products.add(urun);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }



}
