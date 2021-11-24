/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.GiamGiaModel;
import MODEL.SachTheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class GiamGiaDAO implements QLDAO<GiamGiaModel, Float>{

    @Override
    public int insert(GiamGiaModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(GiamGiaModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Float k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GiamGiaModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GiamGiaModel findById(Float k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public GiamGiaModel findByIdSach(Float k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public GiamGiaModel findByIdCTSSach(String id_ctSach) {
        String sql= "select max(id_gg) as id_gg from TLGIAMGIA join  THELOAI on THELOAI.Id= TLGIAMGIA.ID_TL\n" +
"	join SACHtl on sachtl.ID_TL = THELOAI.ID\n" +
"	join SACH on SACH.ID= SACHTL.ID_SACH\n" +
"	join CTSACH on SACH.ID= CTSACH.ID_SACH\n" +
"	where CTSACH.id=? ";
        return select(sql,id_ctSach).get(0);
    }
    

    @Override
    public List<GiamGiaModel> select(String sql, Object... args) {
        List<GiamGiaModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    GiamGiaModel model=readFromResultSet(rs);
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
    private GiamGiaModel readFromResultSet(ResultSet rs) throws SQLException{
        GiamGiaModel model=new GiamGiaModel();
        model.setGiamGia(rs.getFloat("id_gg"));
        return model;
    }
    
}
