package main.halanhalanyuk.Model;

/**
 * Created by XAgusart on 4/8/2018.
 */

public class User {
    private String Nama;
    private String NoHp;
    private String Email;
    private String ImgProfile;
    private String Password;


    public User(String nama, String noHp, String email, String imgProfile, String Password) {
        Nama = nama;
        NoHp = noHp;
        Email = email;
        ImgProfile = imgProfile;
        this.Password = Password;
    }

    public User() {
    }


    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNoHp() {
        return NoHp;
    }

    public void setNoHp(String noHp) {
        NoHp = noHp;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImgProfile() {
        return ImgProfile;
    }

    public void setImgProfile(String imgProfile) {
        ImgProfile = imgProfile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }


}