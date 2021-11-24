/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import MODEL.UserModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UserDAO implements QLDAO<UserModel, String> {

    @Override
    public int insert(UserModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(UserModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserModel> select() {
        String sql= "Select * form users";
        return select(sql);
    }

    @Override
    public UserModel findById(String k) {
        String sql= "Select * from users where mauser=?";
        List<UserModel> list = select(sql, k);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<UserModel> select(String sql, Object... args) {
        List<UserModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    UserModel model=readFromResultSet(rs);
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
    private UserModel readFromResultSet(ResultSet rs) throws SQLException{
        UserModel model=new UserModel();
        model.setCmnd(rs.getString("cmnd"));
        model.setEmail(rs.getString("email"));
        model.setGioiTinh(rs.getBoolean("GioiTinh")); 
        model.setHinh(rs.getString("Hinh"));
        model.setHoveten(rs.getString("hoVaTen"));
        model.setId_vt(rs.getInt("id_vt"));
        model.setNatKhau(rs.getString("matKhau"));
        model.setNgaySinh(rs.getDate("ngaySinh"));
        model.setSdt(rs.getString("sdt"));
        model.setUser(rs.getString("mauser"));
        return model;
    }
    
}
