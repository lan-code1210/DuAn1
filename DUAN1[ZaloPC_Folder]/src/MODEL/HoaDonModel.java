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
public class HoaDonModel {
    private int id;
    private String user,tenKH;
    private Date ngayMua;
    private double  thanhTien,soTienKhachDua;

    public double getSoTienKhachDua() {
        return soTienKhachDua;
    }

    public void setSoTienKhachDua(double soTienKhachDua) {
        this.soTienKhachDua = soTienKhachDua;
    }

    public HoaDonModel() {
    }

    public HoaDonModel(int id, String user, String tenKH, Date ngayMua, double thanhTien, double soTienKhachDua) {
        this.id = id;
        this.user = user;
        this.tenKH = tenKH;
        this.ngayMua = ngayMua;
        this.thanhTien = thanhTien;
        this.soTienKhachDua = soTienKhachDua;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
    
}
