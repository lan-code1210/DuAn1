/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class KhachHangModel {
    private String tenKH,sdt,DiaChi;
    private int id;

    public KhachHangModel() {
    }

    public KhachHangModel(String tenKH, String sdt, String DiaChi, int id) {
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.DiaChi = DiaChi;
        this.id = id;
    }
    
    
    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
