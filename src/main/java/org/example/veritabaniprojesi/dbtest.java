package org.example.veritabaniprojesi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbtest {

    public static void main(String[] args) {
        String url = "jdbc:sqlserver://192.168.56.1:1433;databaseName=testdb";
        String user = "sa";  // MSSQL kullanıcı adı
        String password = "your_password"; // MSSQL şifresi

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
