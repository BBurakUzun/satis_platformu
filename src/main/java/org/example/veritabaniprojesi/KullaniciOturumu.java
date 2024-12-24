package org.example.veritabaniprojesi;

public class KullaniciOturumu {

    private static Kullanici currentUser;

    // Kullanıcıyı ayarlamak için setter
    public static void setCurrentUser(Kullanici kullanici) {
        currentUser = kullanici;
    }

    // Mevcut kullanıcıyı almak için getter
    public static Kullanici getCurrentUser() {
        return currentUser;
    }

    // Kullanıcı oturumunu sıfırlama
    public static void logout() {
        currentUser = null;
    }
}
