package main.halanhalanyuk.Model;

/**
 * Created by XAgusart on 4/10/2018.
 */

public class KulinerModel {
    String judul;
    String keterangan;
    String url;

    public KulinerModel() {
    }

    public KulinerModel(String judul, String keterangan, String url) {
        this.judul = judul;
        this.keterangan = keterangan;
        this.url = url;
    }


    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
