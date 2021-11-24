/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bridj.cpp.com.OLEAutomationLibrary;

/**
 *
 * @author Mr.Right
 */
public class ThongKeDAO1 {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>(); //danh sách chứa các mảng

            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);

                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getSanPham(String batdau, String ketthuc) {
        String sql = "{CALL sp_sanpham(?,?)}";
        String[] cols = {"TenSanPham", "soluongban", "doanhthu"};
        return this.getListOfArray(sql, cols, batdau, ketthuc);
    }

    public List<Object[]> getNhanVien() {
        String sql = "{CALL sp_NhanVien}";
        String[] cols = {"MaNhanVien", "TenNhanVien", "soluongban", "DoanhThu"};
        return this.getListOfArray(sql, cols);
    }

    public int SoLuongBan(String date) {
        int sl = 0;
        try {
            String sql = "SELECT SUM(SLban) SoLuongDaBan\n"
                    + "FROM HOADON join CTHOADON  on HOADON.ID = CTHOADON.ID_HD\n"
                    + "where [NGAYMUA] = ?";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

    public double doanhThu(String date) {
        double sl = 0;
        try {
            String sql = "SELECT Sum(THANHTIEN) SoLuongDaBan\n"
                    + "FROM HOADON join CTHOADON  on HOADON.ID = CTHOADON.ID_HD\n"
                    + "where [NGAYMUA] = ?";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getDouble(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

}
