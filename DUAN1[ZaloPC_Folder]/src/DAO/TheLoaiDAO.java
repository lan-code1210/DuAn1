/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import MODEL.TheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TheLoaiDAO  implements QLDAO<TheLoaiModel, Integer>{

    @Override
    public int insert(TheLoaiModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(TheLoaiModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TheLoaiModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TheLoaiModel findById(Integer k) {
        String sql= "select * from theloai";
        return select(sql,k).get(0);
    }

    @Override
    public List<TheLoaiModel> select(String sql, Object... args) {
       List<TheLoaiModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    TheLoaiModel model=readFromResultSet(rs);
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
    private TheLoaiModel readFromResultSet(ResultSet rs) throws SQLException{
        TheLoaiModel model=new TheLoaiModel();
        model.setTenTL(rs.getString("tenTL"));
        model.setId(rs.getInt("id"));
        return model;
    }
    
}
