package com.example.digiseedbed;

public class Roles {
    String id,role;

    public Roles() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Roles(String id, String role) {
        this.id = id;
        this.role = role;
    }
}
