package com.siakadakademik.mhs_app.data;

/**
 * Created by xyzz on 1/21/2018.
 */

public class ProdiData {
    private String id_prodi,nama_prodi, id_fak;


    public ProdiData(){
    }

    public ProdiData(String id_prodi, String nama_prodi, String id_fak){
        this.id_prodi = id_prodi;
        this.nama_prodi = nama_prodi;
        this.id_fak = id_fak;
    }

    public String getId_prodi(){
        return id_prodi;
    }

    public void setId_prodi(String id_prodi) {
        this.id_prodi = id_prodi;
    }



    public String getNama_prodi() {
        return nama_prodi;
    }

    public void setNama_prodi(String nama_prodi) {
        this.nama_prodi = nama_prodi;
    }

    public String getId_fak() {
        return id_fak;
    }

    public void setId_fak(String id_fak) {
        this.id_fak = id_fak;
    }
}
