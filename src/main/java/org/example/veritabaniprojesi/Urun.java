package org.example.veritabaniprojesi;

public class Urun {
    int id;
    String ad;
    int satici_id;

    public Urun(int id, String ad, int satici_id, int fiyat, String aciklama, String konum, String teslim_turu, int pazarlik_payi, int ilan_tarihi, int kategori, int sayfa, String yazar) {
        this.id = id;
        this.ad = ad;
        this.satici_id = satici_id;
        this.fiyat = fiyat;
        this.aciklama = aciklama;
        this.konum = konum;
        this.teslim_turu = teslim_turu;
        this.pazarlik_payi = pazarlik_payi;
        this.ilan_tarihi = ilan_tarihi;
        this.kategori = kategori;
        this.sayfa = sayfa;
        this.yazar = yazar;
    }

    int fiyat;
    String aciklama;
    String konum;
    String teslim_turu;
    int pazarlik_payi;
    int ilan_tarihi;
    int kategori;
    int sayfa;
    String yazar;
}
