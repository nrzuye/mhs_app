package com.siakadakademik.mhs_app.data;

/**
 * Created by xyzz on 6/3/2018.
 */

public class PeriodeData {
    private String periode,idperiode;

    public PeriodeData(){

    }

    public PeriodeData(String periode, String idperiode){
        this.periode = periode;
        this.idperiode = idperiode;
    }

    public String getPeriode() {
        return periode;
    }

    public String getIdperiode() {
        return idperiode;
    }


    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public void setIdperiode(String idperiode) {
        this.idperiode = idperiode;
    }
}
