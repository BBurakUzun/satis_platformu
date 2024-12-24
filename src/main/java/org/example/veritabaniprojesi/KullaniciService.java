package org.example.veritabaniprojesi;

import java.sql.*;

public class KullaniciService {

    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";
    String user = "yeni_kullanici";
    String pass = "GüçlüBirŞifre";

    // Kullanıcı bilgilerini al
    public Kullanici getUserByUsernameAndPassword(String kullaniciAdi, String sifre) {
        String query = "SELECT * FROM Kullanici WHERE kullanici_adi = ? AND sifre = ?";
        Kullanici kullanici = null;

        try (Connection conn = DriverManager.getConnection(url, user, pass); // Veritabanı bağlantısını sağla
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, kullaniciAdi);
            stmt.setString(2, sifre);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Kullanıcıyı oluştur ve verileri al
                kullanici = new Kullanici(
                        rs.getInt("id"),
                        rs.getString("kullanici_adi"),
                        rs.getString("sifre"),
                        rs.getString("eposta"),
                        rs.getString("tel_no"),
                        rs.getString("isim"),
                        rs.getString("soyisim"),
                        rs.getString("adres")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kullanici;
    }


//    public int getUserId(String kullaniciAdi, String sifre) {
//        String query = "SELECT id FROM Kullanici WHERE kullanici_adi = ? AND sifre = ?";
//        int userId = -1;  // Kullanıcı ID'sini tutacak değişken
//
//        try (Connection conn = DriverManager.getConnection(url, user, pass); // Veritabanı bağlantısını sağla
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, kullaniciAdi);
//            stmt.setString(2, sifre);
//
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                userId = rs.getInt("id");  // Kullanıcı ID'sini al
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return userId;
//    }

}
