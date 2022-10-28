package com.example.digiseedbed;

public class CustomerPojo {
    String name_rv,code_cv,resident_rv,email_rv,date_rv;

    public CustomerPojo() {
    }

    public CustomerPojo(String name_rv, String code_cv, String resident_rv, String email_rv, String date_rv) {
        this.name_rv = name_rv;
        this.code_cv = code_cv;
        this.resident_rv = resident_rv;
        this.email_rv = email_rv;
        this.date_rv = date_rv;
    }

    public String getName_rv() {
        return name_rv;
    }

    public void setName_rv(String name_rv) {
        this.name_rv = name_rv;
    }

    public String getCode_cv() {
        return code_cv;
    }

    public void setCode_cv(String code_cv) {
        this.code_cv = code_cv;
    }

    public String getResident_rv() {
        return resident_rv;
    }

    public void setResident_rv(String resident_rv) {
        this.resident_rv = resident_rv;
    }

    public String getEmail_rv() {
        return email_rv;
    }

    public void setEmail_rv(String email_rv) {
        this.email_rv = email_rv;
    }

    public String getDate_rv() {
        return date_rv;
    }

    public void setDate_rv(String date_rv) {
        this.date_rv = date_rv;
    }
}
