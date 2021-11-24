/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.KhachHangModel;
import MODEL.UserModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangDAO implements QLDAO<KhachHangModel, Integer>{

    @Override
    public int insert(KhachHangModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(KhachHangModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KhachHangModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KhachHangModel findById(Integer k) {
        String sql= "select * from khachHang where id=?";
        List<KhachHangModel> list = select(sql, k);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhachHangModel> select(String sql, Object... args) {
        List<KhachHangModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    KhachHangModel model=readFromResultSet(rs);
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
    private KhachHangModel readFromResultSet(ResultSet rs) throws SQLException{
        KhachHangModel model=new KhachHangModel();
        model.setId(rs.getInt("id"));
        model.setDiaChi(rs.getString("diachi"));
        model.setSdt(rs.getString("sdt")); 
        model.setTenKH(rs.getString("tenKH"));
        
        return model;
    }
    
}
