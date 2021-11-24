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
public class SachModel {
    private String tenSach;
    private int id;
    private Date namXB;
    
    public SachModel() {
    }

    public SachModel(String tenSach, int id, Date namXB) {
        this.tenSach = tenSach;
        this.id = id;
        this.namXB = namXB;
    }
    
    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNamXB() {
        return namXB;
    }

    public void setNamXB(Date namXB) {
        this.namXB = namXB;
    }
    
}
