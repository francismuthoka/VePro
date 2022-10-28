package com.example.digiseedbed;

public class NursuryBedClass {
    String customer_codetxt,date_plantedtxt,plant_na;

    public NursuryBedClass() {

    }

    public String getCustomer_codetxt() {
        return customer_codetxt;
    }

    public void setCustomer_codetxt(String customer_codetxt) {
        this.customer_codetxt = customer_codetxt;
    }

    public String getDate_plantedtxt() {
        return date_plantedtxt;
    }

    public void setDate_plantedtxt(String date_plantedtxt) {
        this.date_plantedtxt = date_plantedtxt;
    }

    public String getPlant_na() {
        return plant_na;
    }

    public void setPlant_na(String plant_na) {
        this.plant_na = plant_na;
    }

    public NursuryBedClass(String customer_codetxt, String date_plantedtxt, String plant_na) {
        this.customer_codetxt = customer_codetxt;
        this.date_plantedtxt = date_plantedtxt;
        this.plant_na = plant_na;
    }
}
