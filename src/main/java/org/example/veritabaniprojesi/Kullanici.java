package org.example.veritabaniprojesi;

public class Kullanici {
    public Kullanici(int id, String kullanici_adi, String sifre, String e_posta, String tel_no, String ad, String soyad, String adres) {
        this.id = id;
        this.kullanici_adi = kullanici_adi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.e_posta = e_posta;
        this.tel_no = tel_no;
        this.adres = adres;
    }

    int id;

    public int getId() {
        return id;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public String getSifre() {
        return sifre;
    }

    public String getAd() {
        return ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public String getE_posta() {
        return e_posta;
    }

    public String getTel_no() {
        return tel_no;
    }

    public String getAdres() {
        return adres;
    }

    String kullanici_adi;
    String sifre;
    String ad;
    String soyad;
    String e_posta;
    String tel_no;
    String adres;
}
