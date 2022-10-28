package com.example.digiseedbed;

public class AddWorkerClass {
    String worker_name,worker_id_no,worker_date_of_birth,worker_email,worker_passwo,gender;

    public AddWorkerClass() {
    }

    public AddWorkerClass(String worker_name, String worker_id_no, String worker_date_of_birth, String worker_email, String worker_passwo, String gender) {
        this.worker_name = worker_name;
        this.worker_id_no = worker_id_no;
        this.worker_date_of_birth = worker_date_of_birth;
        this.worker_email = worker_email;
        this.worker_passwo = worker_passwo;
        this.gender = gender;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getWorker_id_no() {
        return worker_id_no;
    }

    public void setWorker_id_no(String worker_id_no) {
        this.worker_id_no = worker_id_no;
    }

    public String getWorker_date_of_birth() {
        return worker_date_of_birth;
    }

    public void setWorker_date_of_birth(String worker_date_of_birth) {
        this.worker_date_of_birth = worker_date_of_birth;
    }

    public String getWorker_email() {
        return worker_email;
    }

    public void setWorker_email(String worker_email) {
        this.worker_email = worker_email;
    }

    public String getWorker_passwo() {
        return worker_passwo;
    }

    public void setWorker_passwo(String worker_passwo) {
        this.worker_passwo = worker_passwo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
