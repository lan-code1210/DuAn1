/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class CTSachModel {
    private String id,vitri,trangThai;
    private double gia;
    private int id_sach,id_NXB,id_Bia,id_NN,soLuong,tap,soTrang;

    public CTSachModel() {
    }

    public CTSachModel(String id, String vitri, String trangThai, double gia, int id_sach, int id_NXB, int id_Bia, int id_NN, int soLuong, int tap, int soTrang) {
        this.id = id;
        this.vitri = vitri;
        this.trangThai = trangThai;
        this.gia = gia;
        this.id_sach = id_sach;
        this.id_NXB = id_NXB;
        this.id_Bia = id_Bia;
        this.id_NN = id_NN;
        this.soLuong = soLuong;
        this.tap = tap;
        this.soTrang = soTrang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getId_NXB() {
        return id_NXB;
    }

    public void setId_NXB(int id_NXB) {
        this.id_NXB = id_NXB;
    }

    public int getId_Bia() {
        return id_Bia;
    }

    public void setId_Bia(int id_Bia) {
        this.id_Bia = id_Bia;
    }

    public int getId_NN() {
        return id_NN;
    }

    public void setId_NN(int id_NN) {
        this.id_NN = id_NN;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTap() {
        return tap;
    }

    public void setTap(int tap) {
        this.tap = tap;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }
    
}
