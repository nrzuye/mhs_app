package com.siakadakademik.mhs_app.data;

/**
 * Created by xyzz on 11/16/2017.
 */

public class MhsData {
    private String id_mhs,nama_mhs,alamat, telepon, semester;

    public MhsData(){
    }

    public MhsData( String id_mhs, String nama_mhs, String alamat, String telepon, String semester){
        this.id_mhs = id_mhs;
        this.nama_mhs = nama_mhs;
        this.alamat = alamat;
        this.telepon = telepon;
        this.semester = semester;
    }
    public String getId_mhs() {return id_mhs; }
    public void setId_mhs(String id_mhs){this.id_mhs = id_mhs; }

    public String getNama_mhs() {return nama_mhs; }
    public void  setNama_mhs(String nama_mhs) {this.nama_mhs = nama_mhs; }

    public String getAlamat() {return  alamat; }
    public  void setAlamat(String alamat) {this.alamat = alamat; }

    public String getTelepon() {return telepon; }
    public void setTelepon(String telepon) {this.telepon = telepon; }

    public String getSemester() {return semester; }
    public void setSemester(String semester) {this.semester = semester;}
}
