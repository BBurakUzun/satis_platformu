package org.example.veritabaniprojesi;

public class Urun {
    int id;
    String ad;
    int satici_id;

    public Urun(int id, String ad, int satici_id, int fiyat, String aciklama, String konum, String teslim_turu, String kategori, int sayfa, String yazar, String imagePath) {
        this.id = id;
        this.ad = ad;
        this.satici_id = satici_id;
        this.fiyat = fiyat;
        this.aciklama = aciklama;
        this.konum = konum;
        this.teslim_turu = teslim_turu;
        this.kategori = kategori;
        this.sayfa = sayfa;
        this.yazar = yazar;
        this.imagePath = imagePath;
    }
    private String imagePath;
    int fiyat;
    String aciklama;
    String konum;

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public int getSatici_id() {
        return satici_id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getFiyat() {
        return fiyat;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getKonum() {
        return konum;
    }

    public String getTeslim_turu() {
        return teslim_turu;
    }


    public String getKategori() {
        return kategori;
    }

    public int getSayfa() {
        return sayfa;
    }

    public String getYazar() {
        return yazar;
    }

    String teslim_turu;
    String kategori;
    int sayfa;
    String yazar;
}
