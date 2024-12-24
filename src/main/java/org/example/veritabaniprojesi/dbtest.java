package org.example.veritabaniprojesi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbtest {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=testdb;encrypt=true;trustServerCertificate=true";

        String user = "yeni_kullanici";  // MSSQL kullanıcı adı
        String password = "GüçlüBirŞifre"; // MSSQL şifresi

        try {
            // MSSQL JDBC Sürücüsünü yükle
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Veritabanına bağlan
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Bağlantı başarılı!");

            // Bağlantıyı kapat
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver bulunamadı!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Veritabanına bağlanılamadı!");
            e.printStackTrace();
        }
    }

}
