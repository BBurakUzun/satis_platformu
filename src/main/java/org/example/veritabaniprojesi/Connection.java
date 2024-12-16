//package org.example.veritabaniprojesi;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class Connection {
//    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=testdb";
//    private static final String USER = "kullaniciAdi";
//    private static final String PASSWORD = "sifre";
//
//    public static Connection connect() {
//        Connection connection = null;
//
//        try {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Bağlantı başarılı!");
//        } catch (SQLException e) {
//            System.out.println("Bağlantı başarısız: " + e.getMessage());
//        }
//
//        return connection;
//    }
//}
