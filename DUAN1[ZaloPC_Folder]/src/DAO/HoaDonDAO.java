/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonDAO implements QLDAO<HoaDonModel, String>{

    @Override
    public int insert(HoaDonModel e) {
        String sql="insert into HoaDon (MAUSER,soTienKhachDua,THANHTIEN,tenKhach) values(?,?,?,?)";
        return JdbcHelper.executeUpdate(sql,
                "hoangnd123",
                e.getSoTienKhachDua(),
                e.getThanhTien(),
                e.getTenKH());
    }

    @Override
    public int update(HoaDonModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HoaDonModel> select() {
        String sql="Select * from hoadon";
        return select(sql);
    }

    @Override
    public HoaDonModel findById(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HoaDonModel> select(String sql, Object... args) {
        List<HoaDonModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    HoaDonModel model=readFromResultSet(rs);
                    list.add(model);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    private HoaDonModel readFromResultSet(ResultSet rs) throws SQLException{
        HoaDonModel model=new HoaDonModel();
        model.setId(rs.getInt("id"));
        model.setTenKH(rs.getString("tenKhach"));
        model.setNgayMua(rs.getDate("ngaymua")); 
        model.setThanhTien(rs.getDouble("thanhtien"));
        model.setUser(rs.getString("mauser"));
        model.setSoTienKhachDua(rs.getDouble("soTienKhachDua"));
        return model;
    }
    
}
