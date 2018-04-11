package main.halanhalanyuk.Model;


/**
 * Created by XAgusart on 4/3/2018.
 */

public class Acara {
    private String Judul;
    private String Tanggal;
    private String Lokasi;
    private String Author;
    private String GambarUri;
    private String keterangan;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Acara(String judul, String tanggal, String lokasi, String author, String gambarUri) {
        Judul = judul;
        Tanggal = tanggal;
        Lokasi = lokasi;
        Author = author;
        GambarUri = gambarUri;
    }

    public Acara() {
    }


    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public String getGambarUri() {
        return GambarUri;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setGambarUri(String gambarUri) {
        GambarUri = gambarUri;
    }
}
