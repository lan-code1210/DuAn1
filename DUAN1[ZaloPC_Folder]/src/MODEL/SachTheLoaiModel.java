/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class SachTheLoaiModel {
    private int id_sach,id_TL;

    public SachTheLoaiModel() {
    }

    public SachTheLoaiModel(int id_sach, int id_TL) {
        this.id_sach = id_sach;
        this.id_TL = id_TL;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getId_TL() {
        return id_TL;
    }

    public void setId_TL(int id_TL) {
        this.id_TL = id_TL;
    }
    
}
