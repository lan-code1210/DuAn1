
package MODEL;

/**
 *
 * @author Admin
 */
public class CTHoaDonModel {
    private String id_CTsach;
    private int slBan,id_HD;
    private double gia,giamGia;

    public CTHoaDonModel(String id_CTsach, int slBan, int id_HD, double gia, double giamGia) {
        this.id_CTsach = id_CTsach;
        this.slBan = slBan;
        this.id_HD = id_HD;
        this.gia = gia;
        this.giamGia = giamGia;
    }

    @Override
    public String toString() {
        return "CTHoaDonModel{" + "id_CTsach=" + id_CTsach + ", slBan=" + slBan + ", id_HD=" + id_HD + ", gia=" + gia + ", giamGia=" + giamGia + '}';
    }
    
    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }
    
    public CTHoaDonModel() {
    }
    
    public String getId_CTsach() {
        return id_CTsach;
    }

    public void setId_CTsach(String id_CTsach) {
        this.id_CTsach = id_CTsach;
    }

    

    public int getSlBan() {
        return slBan;
    }

    public void setSlBan(int slBan) {
        this.slBan = slBan;
    }

    public int getId_HD() {
        return id_HD;
    }

    public void setId_HD(int id_HD) {
        this.id_HD = id_HD;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
    
}
