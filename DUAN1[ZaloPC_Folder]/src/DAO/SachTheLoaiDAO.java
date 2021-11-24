/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTHoaDonModel;
import MODEL.SachTheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SachTheLoaiDAO implements QLDAO<SachTheLoaiModel, Integer>{

    @Override
    public int insert(SachTheLoaiModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(SachTheLoaiModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SachTheLoaiModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SachTheLoaiModel findById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public List<SachTheLoaiModel> select(String sql, Object... args) {
        List<SachTheLoaiModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    SachTheLoaiModel model=readFromResultSet(rs);
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
    
    private SachTheLoaiModel readFromResultSet(ResultSet rs) throws SQLException{
        SachTheLoaiModel model=new SachTheLoaiModel();
        model.setId_TL(rs.getInt("id_tl"));
        model.setId_sach(rs.getInt("id_sach"));
        
        return model;
    }
    
}
