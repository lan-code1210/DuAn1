/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTHoaDonModel;
import MODEL.SachModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class CTHoaDonDAO implements QLDAO<CTHoaDonModel, Integer>{

    @Override
    public int insert(CTHoaDonModel e) {
        String sql="insert into CTHoaDon (id_CTsach,id_HD,SLban,Gia,Giamgia) values(?,?,?,?,?)";
        
        try {
            return JdbcHelper.executeUpdate(sql,
                    e.getId_CTsach(),
                    selectId(),
                    e.getSlBan(),
                    e.getGia(),
                    e.getGiamGia());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    @Override
    public int update(CTHoaDonModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CTHoaDonModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int selectId() throws SQLException {
        String sql = "Select max(id) from hoadon";
        ResultSet rs= JdbcHelper.executeQuery(sql);
        while (rs.next()) {            
            return rs.getInt(1);
        }
        return 0;
    }
    @Override
    public CTHoaDonModel findById(Integer k) {
        String sql= "select * from cthoadon where id= ?";
        return select(sql, k).get(0);
    }
    
    public List<CTHoaDonModel> findByIdHD(Integer k) {
        String sql= "select * from cthoadon where id_HD= ?";
        return select(sql, k);
    }

    @Override
    public List<CTHoaDonModel> select(String sql, Object... args) {
        List<CTHoaDonModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    CTHoaDonModel model=readFromResultSet(rs);
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
    private CTHoaDonModel readFromResultSet(ResultSet rs) throws SQLException{
        CTHoaDonModel model=new CTHoaDonModel();
        model.setGia(rs.getInt("gia"));
        model.setSlBan(rs.getInt("SLban"));
        model.setId_CTsach(rs.getString("id_CTsach"));
        model.setId_HD(rs.getInt("id_hd")); 
        model.setGiamGia(rs.getDouble("giamGia")); 
        return model;
    }
    
}
