package com.example.digiseedbed;

public class AddCustomerClass {
    String customer_name,customer_email,customer_password,customer_resident,customer_code,customer_date_birth,customer_phone,gender;

    public AddCustomerClass() {
    }

    public AddCustomerClass(String customer_name, String customer_email, String customer_password, String customer_resident, String customer_code, String customer_date_birth, String customer_phone, String gender) {
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_password = customer_password;
        this.customer_resident = customer_resident;
        this.customer_code = customer_code;
        this.customer_date_birth = customer_date_birth;
        this.customer_phone = customer_phone;
        this.gender = gender;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_password() {
        return customer_password;
    }

    public void setCustomer_password(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getCustomer_resident() {
        return customer_resident;
    }

    public void setCustomer_resident(String customer_resident) {
        this.customer_resident = customer_resident;
    }

    public String getCustomer_code() {
        return customer_code;
    }

    public void setCustomer_code(String customer_code) {
        this.customer_code = customer_code;
    }

    public String getCustomer_date_birth() {
        return customer_date_birth;
    }

    public void setCustomer_date_birth(String customer_date_birth) {
        this.customer_date_birth = customer_date_birth;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
