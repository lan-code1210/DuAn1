/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author THE PHONG
 */
public class NXBModel {
     private int id_sach;
     private String tenNXB,DiaChi;

    public NXBModel() {
    }

    public NXBModel(int id_sach, String tenNXB, String DiaChi) {
        this.id_sach = id_sach;
        this.tenNXB = tenNXB;
        this.DiaChi = DiaChi;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }
     
     
}
