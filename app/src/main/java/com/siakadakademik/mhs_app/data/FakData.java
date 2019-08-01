package com.siakadakademik.mhs_app.data;

/**
 * Created by xyzz on 1/21/2018.
 */

public class FakData {
    private String id_fak,fakultas;


    public FakData(){
    }

    public FakData(String id_fak, String fakultas){
        this.id_fak = id_fak;
        this.fakultas = fakultas;
    }

    public String getId_fak(){return id_fak;}

    public void setId_fak(String id_fak) {
        this.id_fak = id_fak;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }
}
