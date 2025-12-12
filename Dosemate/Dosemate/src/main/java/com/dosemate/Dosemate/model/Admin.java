package com.dosemate.Dosemate.model;

import jakarta.persistence.*;

@Entity
public class Admin
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AdminId;
    @Column(name = "AdminName")
    private String adminName;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdminId()
    {
        return AdminId;
    }
    public void setAdminId(int adminId)
    {
        AdminId = adminId;
    }
    public String getAdminName()
    {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

}
