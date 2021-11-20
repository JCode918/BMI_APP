package org.jcode918.bmicalculator.model;

import android.graphics.Color;

import java.util.Arrays;

public class BMIModel {
    private double bmi;
    private String[] more;
    private String risk;

    public BMIModel(double bmi, String[] more, String risk) {
        this.bmi = bmi;
        this.more = more;
        this.risk = risk;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String[] getMore() {
        return more;
    }

    public void setMore(String[] more) {
        this.more = more;
    }

    public String getRisk() {
        return risk;
    }

    public void setRisk(String risk) {
        this.risk = risk;
    }

    @Override
    public String toString() {
        return "BMIModel{" +
                "bmi=" + bmi +
                ", more=" + Arrays.toString(more) +
                ", risk='" + risk + '\'' +
                '}';
    }

    public String specificLink(int a ){
        return this.more[a];
    }

    public int linkCount(){
        return this.more.length;
    }

}
