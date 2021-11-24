/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class UserModel {
    private String user,hoveten,sdt,email,cmnd,hinh,natKhau;
    private boolean gioiTinh ;
    private Date ngaySinh;
    private int id_vt;
    public UserModel() {
    }

    public UserModel(String user, String hoveten, String sdt, String email, String cmnd, String hinh, String natKhau, boolean gioiTinh, Date ngaySinh, int id_vt) {
        this.user = user;
        this.hoveten = hoveten;
        this.sdt = sdt;
        this.email = email;
        this.cmnd = cmnd;
        this.hinh = hinh;
        this.natKhau = natKhau;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.id_vt = id_vt;
    }
    
    public int getId_vt() {
        return id_vt;
    }

    public void setId_vt(int id_vt) {
        this.id_vt = id_vt;
    }

    
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getHoveten() {
        return hoveten;
    }

    public void setHoveten(String hoveten) {
        this.hoveten = hoveten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getNatKhau() {
        return natKhau;
    }

    public void setNatKhau(String natKhau) {
        this.natKhau = natKhau;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
}
