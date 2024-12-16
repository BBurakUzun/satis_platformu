package org.example.veritabaniprojesi;

public class Kullanici {
    public Kullanici(int id, String kullanic_adi, String sifre, String ad, String soyad, String e_posta, String adres) {
        this.id = id;
        this.kullanic_adi = kullanic_adi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.e_posta = e_posta;
        this.adres = adres;
    }

    int id;
    String kullanic_adi;
    String sifre;
    String ad;
    String soyad;
    String e_posta;
    String adres;
}
